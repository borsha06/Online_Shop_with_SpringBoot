package com.dsam.assignment02.services;

import com.dsam.assignment02.constants.Constants;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.ProjectTopicName;
import com.google.pubsub.v1.PubsubMessage;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class EventPublisherService {
	private final ProjectTopicName emailTopicName;
	private final Logger logger;

	public EventPublisherService(Logger logger) {
		this.logger = logger;
		emailTopicName = ProjectTopicName.of(Constants.PROJECT_ID, Constants.EMAIL_TOPIC_ID);
	}

	public void publishMessage(Long orderId, String objectName) throws InterruptedException {
		Publisher publisher = null;
		try {
			PubsubMessage pubsubMessage = PubsubMessage.newBuilder()
				.setData(ByteString.copyFromUtf8("A bill has been uploaded."))
				.putAttributes("orderId", orderId.toString())
				.putAttributes("objectName", objectName)
				.build();

			publisher = Publisher.newBuilder(emailTopicName).build();

			logger.info("Starting message publishing");
			publisher.publish(pubsubMessage).get();
		} catch (InterruptedException | ExecutionException | IOException e) {
			logger.severe(e.getMessage());
		} finally {
			if (publisher != null) {
				publisher.shutdown();
				publisher.awaitTermination(1, TimeUnit.MINUTES);
				logger.info("Terminated");
			}
		}
	}
}
