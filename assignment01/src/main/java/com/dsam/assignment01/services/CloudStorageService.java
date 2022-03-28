package com.dsam.assignment01.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CloudStorageService {
	String uploadImage(MultipartFile image) throws IOException;
}