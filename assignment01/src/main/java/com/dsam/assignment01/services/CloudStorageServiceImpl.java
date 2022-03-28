package com.dsam.assignment01.services;

import com.dsam.assignment01.managers.GoogleCredentialsManager;
import com.google.auth.Credentials;
import com.google.cloud.storage.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

@Service
public class CloudStorageServiceImpl implements CloudStorageService {

	private static final String FOLDER_NAME = "static/images";
	private static final String BUCKET_NAME = "assignment01-339817.appspot.com";
	private final Storage storage;

	@Autowired
	public CloudStorageServiceImpl(ResourceLoader resourceLoader, Environment environment) throws IOException {

		String projectId = environment.getProperty("google.cloud.project.id");
		Credentials credentialForCloudStorage = GoogleCredentialsManager.getCredentialForCloudStorage(resourceLoader);

		storage = StorageOptions.newBuilder()
			.setProjectId(projectId)
			.setCredentials(credentialForCloudStorage)
			.build()
			.getService();
	}

	public String uploadImage(MultipartFile image) throws IOException {

		String[] fileParts = Objects.requireNonNull(image.getContentType()).split("/");
		String extension = fileParts[fileParts.length - 1];
		String filename = String.format("%s.%s", UUID.randomUUID(), extension);
		String objectName = String.format("%s/%s", FOLDER_NAME, filename);
		List<Acl> aclList = Collections.singletonList(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));

		BlobId blobId = BlobId.of(BUCKET_NAME, objectName);
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
			.setAcl(aclList)
			.setContentType(image.getContentType())
			.build();

		InputStream inputStream = new ByteArrayInputStream(image.getBytes());
		Blob blob = storage.createFrom(blobInfo, inputStream);
		return String.format("https://storage.googleapis.com/%s/%s", BUCKET_NAME, objectName);
	}
}
