package com.website.blogapp.service.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.website.blogapp.constants.PostConstant;
import com.website.blogapp.entity.Category;
import com.website.blogapp.entity.Post;
import com.website.blogapp.entity.User;
import com.website.blogapp.exception.CategoryNotFoundException;
import com.website.blogapp.exception.PostDatabaseIsEmptyException;
import com.website.blogapp.exception.PostNotFoundException;
import com.website.blogapp.exception.UserDatabaseIsEmptyException;
import com.website.blogapp.exception.UserNotFoundException;
import com.website.blogapp.mapper.PostMapper;
import com.website.blogapp.payload.ApiResponse;
import com.website.blogapp.payload.PostContentResponse;
import com.website.blogapp.payload.PostDto;
import com.website.blogapp.payload.PostResponseDto;
import com.website.blogapp.repository.CategoryRepository;
import com.website.blogapp.repository.PostCriteriaRepository;
import com.website.blogapp.repository.PostRepository;
import com.website.blogapp.repository.UserRepository;
import com.website.blogapp.service.FileService;
import com.website.blogapp.service.PostService;
import com.website.blogapp.util.CsvFileUtil;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private PostCriteriaRepository postCriteriaRepository;

	@Autowired
	private PostMapper postMapper;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private FileService fileService;

	@Value("${project.image}")
	private String path;

	@Override
	public PostContentResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		if (postRepository.count() == 0) {
			throw new PostDatabaseIsEmptyException("No posts found in the database.");
		}

		Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
				: sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> page = postRepository.findAll(pageable);
		List<Post> postPageContent = page.getContent();
		List<PostResponseDto> postResponseDto = postPageContent.stream().map((post) -> postMapper.postToResponseDto(post))
				.collect(Collectors.toList());

		PostContentResponse postContentResponse = PostContentResponse.builder().postResponseDtoPageContent(postResponseDto)
				.pageNumber(page.getNumber()).pageSize(page.getSize()).totalElements(page.getTotalElements())
				.totalPages(page.getTotalPages()).lastPage(page.isLast()).build();

		return postContentResponse;
	}

	@Override
	public PostResponseDto getSinglePost(Integer postId) {
		if (postRepository.count() == 0) {
			throw new PostDatabaseIsEmptyException("No posts found in the database.");
		}

		return postRepository.findById(postId).map(postMapper::postToResponseDto)
				.orElseThrow(() -> new PostNotFoundException("Post " + postId + " does not exist."));
	}

	@Override
	public PostResponseDto createPost(PostDto postDto, String userEmail, Integer categoryId, MultipartFile postImageFile)
			throws IOException {
		if (userRepository.count() == 0) {
			throw new UserDatabaseIsEmptyException("No users found in the database.");
		}

		User user = userRepository.findByUserEmail(userEmail);

		if (user == null) {
			throw new UserNotFoundException("User " + userEmail + " does not exist.");
		}

		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new CategoryNotFoundException("Category " + categoryId + " does not exist."));

		Post post = postMapper.dtoToPost(postDto);

//		post.setPostTitle(postDto.getPostTitle());
//		post.setPostContent(postDto.getPostContent());
		post.setPostCreatedDate(new Date());
		post.setUser(user);
		post.setCategory(category);

		if (postImageFile == null) {
			post.setPostImageName(null);
		} else {
			String fileName = fileService.uploadImage(path, postImageFile);
			post.setPostImageName(fileName);

		}

		postRepository.save(post);
		PostResponseDto postResponseDto = postMapper.postToResponseDto(post);
		return postResponseDto;
	}

	@Override
	public PostResponseDto updatePost(Integer postId, PostDto postDto, MultipartFile postImageFile) throws IOException {
		Post existingPost = postRepository.findById(postId)
				.orElseThrow(() -> new PostNotFoundException("Post " + postId + " does not exist."));

		existingPost.setPostTitle(postDto.getPostTitle());
		existingPost.setPostContent(postDto.getPostContent());

		if (postImageFile == null) {
			existingPost.setPostImageName(null);
		} else {
			String fileName = fileService.uploadImage(path, postImageFile);
			existingPost.setPostImageName(fileName);
		}

		postRepository.save(existingPost);
		PostResponseDto postResponseDto = postMapper.postToResponseDto(existingPost);
		return postResponseDto;

	}

	@Override
	public ApiResponse deletePost(Integer postId) {
		if (postRepository.count() == 0) {
			throw new PostDatabaseIsEmptyException("No posts found in the database.");
		}

		postRepository.findById(postId)
				.orElseThrow(() -> new PostNotFoundException("Post " + postId + " does not exist."));

		postRepository.deleteById(postId);

		ApiResponse apiResponse = ApiResponse.builder().message("Post " + postId + " has been successfully deleted")
				.success(PostConstant.SUCCESS).build();

		return apiResponse;

	}

	@Override
	public ApiResponse deleteAllPost() {
		if (postRepository.count() == 0) {
			throw new PostDatabaseIsEmptyException("No posts found in the database.");
		}

		postRepository.deleteAll();
		ApiResponse apiResponse = ApiResponse.builder().message("All posts has been successfully deleted")
				.success(PostConstant.SUCCESS).build();
		return apiResponse;
	}

	@Override
	public List<PostResponseDto> getPostByCategory(Integer categoryId) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new CategoryNotFoundException("Category " + categoryId + " does not exist."));

		List<Post> posts = postRepository.findByCategory(category);

		if (posts.isEmpty()) {
			throw new PostNotFoundException("Category " + categoryId + " does not have any post.");
		}

		List<PostResponseDto> postResponseDto = posts.stream().map((post) -> postMapper.postToResponseDto(post)).collect(Collectors.toList());
		return postResponseDto;
	}

	@Override
	public List<PostResponseDto> getPostByUser(Integer userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User " + userId + " does not exist."));

		List<Post> posts = postRepository.findByUser(user);

		if (posts.isEmpty()) {
			throw new PostNotFoundException("User " + userId + " does not have any post.");
		}

		List<PostResponseDto> postResponseDto = posts.stream().map((post) -> postMapper.postToResponseDto(post)).collect(Collectors.toList());
		return postResponseDto;
	}

	@Override
	public List<PostResponseDto> searchPostByTitle(String keywords) {
		List<Post> posts = postRepository.findByPostTitleContaining(keywords);
		if (posts.isEmpty()) {
			throw new PostNotFoundException("Kindly search with another keywords");
		}

		List<PostResponseDto> postResponseDto = posts.stream().map((post) -> postMapper.postToResponseDto(post)).collect(Collectors.toList());
		return postResponseDto;
	}

	@Override
	public List<PostResponseDto> findPostByTitleAndCategory(Integer categoryId, String postTitle) {
		categoryRepository.findById(categoryId)
				.orElseThrow(() -> new CategoryNotFoundException("Category " + categoryId + " does not exist."));

		List<Post> posts = postCriteriaRepository.findPostByTitleAndCategory(postTitle, categoryId);

		if (posts.isEmpty()) {
			throw new PostNotFoundException("Post title is not present in given category: " + categoryId);
		}
		List<PostResponseDto> postResponseDto = posts.stream().map((post) -> postMapper.postToResponseDto(post)).collect(Collectors.toList());
		return postResponseDto;
	}

	@Override
	public void exportPostInCsv(Integer userId, HttpServletResponse response) throws IOException {
		List<PostResponseDto> postResponseDto = this.getPostByUser(userId);
		String fileName = "User-" + userId + " Posts.csv";
		response.setContentType("text/csv");
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "");

		PrintWriter writer = response.getWriter();
		CsvFileUtil.writePostToCsv(writer, postResponseDto);
	}

}
