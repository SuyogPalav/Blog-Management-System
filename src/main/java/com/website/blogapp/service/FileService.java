package com.website.blogapp.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;

public interface FileService {
	public String uploadImage(String path, MultipartFile file) throws IOException;

	public void downloadImage(String path, String postImageFile, HttpServletResponse response) throws IOException;

}
