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
		String imageFileExtension = ImageFileUtil.getFileExtension(imagefileName);

		// Allowed image types
		List<String> allowedExtensions = Arrays.asList("jpg", "jpeg", "png", "gif", "bmp", "webp", "avif");

		if (!allowedExtensions.contains(imageFileExtension)) {
			throw new IllegalArgumentException("Invalid file type. Only image files are allowed.");
		}

		return imageFileExtension;

	}

	public static String createRandomFileName(String fileExtension) {
		// Generate a random file name
		String randomID = UUID.randomUUID().toString();
		String randomFileName = randomID.concat("." + fileExtension);
		return randomFileName;

	}

	public static String getFilePath(String path, String randomImageFileName) {
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

	public static String getFileExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
	}

	public static String getContentType(String fileExtension) {
		switch (fileExtension) {
		case "jpg":
		case "jpeg":
			return "image/jpeg";
		case "png":
			return "image/png";
		case "gif":
			return "image/gif";
		case "bmp":
			return "image/bmp";
		case "webp":
			return "image/webp";
		case "avif":
			return "image/avif";
		default:
			return "application/octet-stream";
		}
	}

}
