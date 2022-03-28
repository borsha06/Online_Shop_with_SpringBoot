package com.dsam.assignment01.managers;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Set;

public class GoogleCredentialsManager {
	public static FixedCredentialsProvider getCredentialProviderForPubsub(ResourceLoader resourceLoader) throws IOException {
		String secret = "pubsub-message-publisher.json";
		GoogleCredentials googleCredentials = getGoogleCredentials(resourceLoader, secret);
		return FixedCredentialsProvider.create(googleCredentials);
	}

	public static Credentials getCredentialForCloudStorage(ResourceLoader resourceLoader) throws IOException {
		String secret = "cloud-storage-file-publisher.json";
		return getGoogleCredentials(resourceLoader, secret);
	}

	private static GoogleCredentials getGoogleCredentials(ResourceLoader resourceLoader, String secret) throws IOException {
		Resource resource = resourceLoader.getResource("classpath:secrets/" + secret);
		InputStream inputStream = resource.getInputStream();

		Set<String> scopes = Collections.singleton("https://www.googleapis.com/auth/cloud-platform");
		return GoogleCredentials.fromStream(inputStream).createScoped(scopes);
	}
}
