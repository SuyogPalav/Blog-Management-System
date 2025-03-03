package com.website.blogapp.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.website.blogapp.service.FileService;
import com.website.blogapp.util.ImageFileUtil;

@Service
public class FileServiceImpl implements FileService {
	@Override
	public String uploadImage(String path, MultipartFile postImageName) throws IOException {
		// Validate file type
		String imagefileName = postImageName.getOriginalFilename();
		String imageFileExtension = ImageFileUtil.checkFileValidation(imagefileName);
		String randomImageFileName = ImageFileUtil.createRandomFileName(imageFileExtension);
		String imageFilePath = ImageFileUtil.getFilePath(path, randomImageFileName);
		ImageFileUtil.createDirectoryIfNotExist(path);

		// Save file
		Files.copy(postImageName.getInputStream(), Paths.get(imageFilePath));

		return randomImageFileName;
	}

	@Override
	public InputStream downloadImage(String path, String randomFileName) throws FileNotFoundException {
		String imageFilePath = ImageFileUtil.getFilePath(path, randomFileName);
		File file = new File(imageFilePath);

		if (!file.exists()) {
			throw new FileNotFoundException("Requested image not found.");
		}
		FileInputStream fileInputStream = new FileInputStream(file);

		return fileInputStream;

	}

}
