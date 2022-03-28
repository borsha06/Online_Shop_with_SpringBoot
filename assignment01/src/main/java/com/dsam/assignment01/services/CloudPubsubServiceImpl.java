package com.dsam.assignment01.services;

import com.dsam.assignment01.managers.GoogleCredentialsManager;
import com.dsam.assignment01.models.Order;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.ProjectTopicName;
import com.google.pubsub.v1.PubsubMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.*;

@Slf4j
@Service
public class CloudPubsubServiceImpl implements CloudPubsubService {
	private final ProjectTopicName billPdfTopicName;
	private final FixedCredentialsProvider credentialProviderForPubsub;

	@Autowired
	public CloudPubsubServiceImpl(ResourceLoader resourceLoader, Environment environment) throws IOException {

		String projectId = environment.getProperty("google.cloud.project.id");
		String billPdfTopicId = environment.getProperty("google.cloud.pubsub.topic.billPdf");
		billPdfTopicName = ProjectTopicName.of(projectId, billPdfTopicId);
		credentialProviderForPubsub = GoogleCredentialsManager.getCredentialProviderForPubsub(resourceLoader);
	}

	public void publishMessage(Order order) throws InterruptedException {
		Publisher publisher = null;
		try {
			PubsubMessage pubsubMessage = PubsubMessage.newBuilder()
				.setData(ByteString.copyFromUtf8("An order has been placed."))
				.putAttributes("orderId", order.getOrderId().toString())
				.build();

			publisher = Publisher.newBuilder(billPdfTopicName)
				.setCredentialsProvider(credentialProviderForPubsub)
				.build();

			log.info("Starting message publishing");
			publisher.publish(pubsubMessage).get();
		} catch (InterruptedException | ExecutionException | IOException e) {
			log.error(e.getMessage());
		} finally {
			if (publisher != null) {
				publisher.shutdown();
				publisher.awaitTermination(1, TimeUnit.MINUTES);
				log.info("Terminated");
			}
		}
	}
}
