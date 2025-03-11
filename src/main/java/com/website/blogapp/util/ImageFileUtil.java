package com.website.blogapp.util;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ImageFileUtil {
	public static String checkFileValidation(String imagefileName) {
		if (imagefileName == null || imagefileName.isEmpty()) {
			throw new IllegalArgumentException("File name is invalid.");
		}

		// Get file extension
		String imageFileExtension = imagefileName.substring(imagefileName.lastIndexOf(".") + 1).toLowerCase();

		// Allowed image types
		List<String> allowedExtensions = Arrays.asList("jpg", "jpeg", "png", "gif", "bmp", "webp", "avif");

		if (!allowedExtensions.contains(imageFileExtension)) {
			throw new IllegalArgumentException("Invalid file type. Only image files are allowed.");
		}

		return imageFileExtension;

	}

	public String createRandomFileName(String fileExtension) {
		// Generate a random file name
		String randomID = UUID.randomUUID().toString();
		String randomFileName = randomID.concat("." + fileExtension);
		return randomFileName;

	}

	public String getFilePath(String path, String randomImageFileName) {
		// Generate a file path
		String imageFilePath = path + File.separator + randomImageFileName;
		return imageFilePath;
	}

	public static void createDirectoryIfNotExist(String path) {
		// Create directory if it does not exist
		File directory = new File(path);
		if (!directory.exists()) {
			directory.mkdirs();
		}
	}
}
