package com.dsam.assignment02.functions;

import com.dsam.assignment02.data.OrderRepository;
import com.dsam.assignment02.models.Order;
import com.dsam.assignment02.models.PubSubMessage;
import com.dsam.assignment02.services.EmailSenderService;
import com.google.cloud.functions.BackgroundFunction;
import com.google.cloud.functions.Context;

import java.sql.SQLException;
import java.util.logging.Logger;

public class OrderEmailSenderFunction implements BackgroundFunction<PubSubMessage> {
	private static final Logger logger = Logger.getLogger(OrderBillPdfGeneratorFunction.class.getName());

	@Override
	public void accept(PubSubMessage payload, Context context) throws SQLException {
		if (payload.attributes == null) {
			logger.severe("Payload attribute is null.");
			return;
		}

		Long orderId = Long.valueOf(payload.attributes.getOrDefault("orderId", "-1"));
		String objectName = payload.attributes.getOrDefault("objectName", "");
		logger.info(String.format("Message received with order id = %s and blob name = %s", orderId, objectName));
		if (orderId == -1 || objectName.isBlank()) {
			logger.info("invalid parameters");
			return;
		}

		Order order = new OrderRepository().getById(orderId);
		if (order == null) {
			logger.severe(String.format("No order has been found for order id = %s", orderId));
			return;
		}

		EmailSenderService emailSenderService = new EmailSenderService(logger);
		emailSenderService.sendEmail(order, objectName);
	}
}
