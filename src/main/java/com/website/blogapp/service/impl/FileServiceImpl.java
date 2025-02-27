package com.website.blogapp.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.website.blogapp.service.FileService;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile postImageName) throws IOException {
		// Validate file type
		String fileName = postImageName.getOriginalFilename();
		if (fileName == null || fileName.isEmpty()) {
			throw new IllegalArgumentException("File name is invalid.");
		}

		// Get file extension
		String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

		// Allowed image types
		List<String> allowedExtensions = Arrays.asList("jpg", "jpeg", "png", "gif", "bmp", "webp");

		if (!allowedExtensions.contains(fileExtension)) {
			throw new IllegalArgumentException("Invalid file type. Only image files are allowed.");
		}

		// Generate a random file name
		String randomID = UUID.randomUUID().toString();
		String randomFileName = randomID.concat("." + fileExtension);
		String filePath = path + File.separator + randomFileName;

		// Create directory if it does not exist
		File directory = new File(path);
		if (!directory.exists()) {
			directory.mkdirs();
		}

		// Save file
		Files.copy(postImageName.getInputStream(), Paths.get(filePath));

		return randomFileName;
	}

	@Override
	public InputStream downloadImage(String path, String randomFileName) throws FileNotFoundException {
		String fullPath = path + File.separator + randomFileName;
		File file = new File(fullPath);

		if (!file.exists()) {
			throw new FileNotFoundException("Requested image not found.");
		}
		FileInputStream fileInputStream = new FileInputStream(file);

		return fileInputStream;

	}

}
