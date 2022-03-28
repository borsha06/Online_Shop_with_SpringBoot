package com.dsam.assignment02.services;

import com.dsam.assignment02.constants.Constants;
import com.dsam.assignment02.models.Order;
import com.dsam.assignment02.models.User;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;

import java.io.ByteArrayInputStream;
import java.util.logging.Logger;

public class EmailSenderService {
	private static final String SMTP_HOST = "smtp.gmail.com";
	private static final int SMTP_PORT = 587;
	private static final String SMTP_USER = "assignment02.noreply@gmail.com";
	private static final String SMTP_PASS = "longpassword";
	private static final String SMTP_NAME = "no reply";

	private final Logger logger;
	private final CloudStorageService cloudStorageService;

	public EmailSenderService(Logger logger) {
		this.logger = logger;
		this.cloudStorageService = new CloudStorageService(logger);
	}

	public void sendEmail(Order order, String objectName) {
		try {
			ByteArrayInputStream pdf = cloudStorageService.downloadPdf(objectName);

			Email email = EmailBuilder.startingBlank()
				.to(order.getUser().getFullName(), order.getUser().getEmail())
				.from(SMTP_NAME, SMTP_USER)
				.withSubject("Your Order Has Been Placed")
				.withPlainText(getMailBody(order.getUser()))
				.withAttachment("Invoice.pdf", pdf.readAllBytes(), Constants.APPLICATION_PDF)
				.buildEmail();

			Mailer mailer = MailerBuilder.withSMTPServer(SMTP_HOST, SMTP_PORT, SMTP_USER, SMTP_PASS)
				.withTransportStrategy(TransportStrategy.SMTP_TLS)
				.buildMailer();

			mailer.sendMail(email, false);
			logger.info("An email containing order details has been sent to the user");
		} catch (Exception exception) {
			logger.severe(exception.getMessage());
		}
	}

	private String getMailBody(User user) {
		return new StringBuilder()
			.append("Hello ")
			.append(user.getFullName())
			.append(",\n\n")
			.append("Thank you for your order. ")
			.append("We will let you know once your item(s) have dispatched. ")
			.append("You can view your order details in the attached pdf.")
			.toString();
	}
}
