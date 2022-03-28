package com.dsam.assignment02.services;

import com.dsam.assignment02.constants.Constants;
import com.google.cloud.storage.*;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;

public class CloudStorageService {

	private final Logger logger;
	private final Storage storage;

	public CloudStorageService(Logger logger) {
		this.logger = logger;
		storage = StorageOptions.newBuilder().setProjectId(Constants.PROJECT_ID).build().getService();
	}

	public Blob uploadPdf(ByteArrayInputStream inputStream) throws IOException {
		try{
			String fileName = String.format("%s.pdf", UUID.randomUUID());
			String objectName = String.format("%s/%s", Constants.FOLDER_NAME, fileName);
			List<Acl> aclList = Collections.singletonList(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));

			BlobId blobId = BlobId.of(Constants.BUCKET_NAME, objectName);
			BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
				.setAcl(aclList)
				.setContentType(Constants.APPLICATION_PDF)
				.build();

			Blob blob = storage.createFrom(blobInfo, inputStream);

			logger.info(String.format("Pdf of the bill has been uploaded to the cloud storage with name = %s", blob.getName()));
			return blob;
		} catch (IOException e) {
			logger.severe(e.getMessage());
			throw e;
		}
	}

	public ByteArrayInputStream downloadPdf(String objectName) {

		BlobId blobId = BlobId.of(Constants.BUCKET_NAME, objectName);
		Blob blob = storage.get(blobId);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		blob.downloadTo(outputStream);
		return new ByteArrayInputStream(outputStream.toByteArray());
	}
}
