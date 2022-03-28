package com.dsam.assignment02.functions;

import com.dsam.assignment02.data.OrderRepository;
import com.dsam.assignment02.models.Order;
import com.dsam.assignment02.models.PubSubMessage;
import com.dsam.assignment02.services.*;
import com.google.cloud.functions.BackgroundFunction;
import com.google.cloud.functions.Context;
import com.google.cloud.storage.Blob;

import java.io.ByteArrayInputStream;
import java.util.logging.Logger;

public class OrderBillPdfGeneratorFunction implements BackgroundFunction<PubSubMessage> {
	private static final Logger logger = Logger.getLogger(OrderBillPdfGeneratorFunction.class.getName());

	@Override
	public void accept(PubSubMessage payload, Context context) throws Exception {
		if (payload.attributes == null) {
			logger.severe("Payload attribute is null.");
			return;
		}

		Long orderId = Long.valueOf(payload.attributes.getOrDefault("orderId", "-1"));
		Order order = new OrderRepository().getById(orderId);

		if (order == null) {
			logger.severe(String.format("No order has been found for order id = %s", orderId));
			return;
		}

		PdfGeneratorService pdfGeneratorService = new PdfGeneratorService(logger);
		ByteArrayInputStream inputStream = pdfGeneratorService.generate(order);

		CloudStorageService cloudStorageService = new CloudStorageService(logger);
		Blob blob = cloudStorageService.uploadPdf(inputStream);

		EventPublisherService eventPublisherService = new EventPublisherService(logger);
		eventPublisherService.publishMessage(orderId, blob.getName());
		logger.info("An email sending event has been published");
	}
}