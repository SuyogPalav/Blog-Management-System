package com.website.blogapp.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import com.website.blogapp.service.FileService;
import com.website.blogapp.util.ImageFileUtil;

import jakarta.servlet.http.HttpServletResponse;

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
	public void downloadImage(String path, String postImageFile, HttpServletResponse response) throws IOException {
		File imageFile = new File(path + File.separator + postImageFile);

		if (!imageFile.exists()) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Image not found");
			return;
		}

		String fileExtension = ImageFileUtil.getFileExtension(postImageFile);
		String contentType = ImageFileUtil.getContentType(fileExtension);

		response.setContentType(contentType);
		response.setHeader("Content-Disposition", "inline; filename=\"" + postImageFile + "\"");

		try (InputStream inputStream = new FileInputStream(imageFile)) {
			StreamUtils.copy(inputStream, response.getOutputStream());
		}
	}

}
