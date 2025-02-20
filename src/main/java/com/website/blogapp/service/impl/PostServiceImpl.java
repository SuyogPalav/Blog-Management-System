package com.website.blogapp.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.website.blogapp.constants.PostConstant;
import com.website.blogapp.entity.Category;
import com.website.blogapp.entity.Post;
import com.website.blogapp.entity.User;
import com.website.blogapp.exception.CategoryNotFoundException;
import com.website.blogapp.exception.PostDatabaseIsEmptyException;
import com.website.blogapp.exception.PostNotFoundException;
import com.website.blogapp.exception.UserNotFoundException;
import com.website.blogapp.mapper.PostMapper;
import com.website.blogapp.payload.ApiResponse;
import com.website.blogapp.payload.PostContentResponse;
import com.website.blogapp.payload.PostDto;
import com.website.blogapp.repository.CategoryRepository;
import com.website.blogapp.repository.PostRepository;
import com.website.blogapp.repository.UserRepository;
import com.website.blogapp.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private PostMapper postMapper;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CategoryRepository categoryRepository;

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
		List<PostDto> postDtoPageContent = postPageContent.stream().map((post) -> postMapper.postToDto(post)).collect(Collectors.toList());


		PostContentResponse postContentResponse = new PostContentResponse();
		postContentResponse.setPostDtoPageContent(postDtoPageContent);
		postContentResponse.setPageNumber(page.getNumber());
		postContentResponse.setPageSize(page.getSize());
		postContentResponse.setTotalElements(page.getTotalElements());
		postContentResponse.setTotalPages(page.getTotalPages());
		postContentResponse.setLastPage(page.isLast());

		return postContentResponse;

	}

	@Override
	public PostDto getSinglePost(Integer postId) {
		if (postRepository.count() == 0) {
			throw new PostDatabaseIsEmptyException("No posts found in the database.");
		}

		return postRepository.findById(postId).map(postMapper::postToDto)
				.orElseThrow(() -> new PostNotFoundException("Post " + postId + " does not exist."));
	}

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User " + userId + " does not exist."));

		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new CategoryNotFoundException("Category " + categoryId + " does not exist."));

		Post post = postMapper.dtoToPost(postDto);

//		post.setPostTitle(postDto.getPostTitle());
//		post.setPostContent(postDto.getPostContent());
		post.setPostImageName(postDto.getPostImageName());
		post.setPostCreatedDate(new Date());
		post.setUser(user);
		post.setCategory(category);

		postRepository.save(post);
		PostDto postDtoCreated = postMapper.postToDto(post);
		return postDtoCreated;
	}

	@Override
	public PostDto updatePost(Integer postId, PostDto postDto) {
		Post existingPost = postRepository.findById(postId)
				.orElseThrow(() -> new PostNotFoundException("Post " + postId + " does not exist."));
		existingPost.setPostTitle(postDto.getPostTitle());
		existingPost.setPostContent(postDto.getPostContent());
		existingPost.setPostImageName(postDto.getPostImageName());
//		existingPost.setPostCreatedDate(new Date());
		postRepository.save(existingPost);

		PostDto postDtoUpdated = postMapper.postToDto(existingPost);
		return postDtoUpdated;

	}

	@Override
	public ApiResponse deletePost(Integer postId) {
		if (postRepository.count() == 0) {
			throw new PostDatabaseIsEmptyException("No posts found in the database.");
		}

		postRepository.findById(postId)
				.orElseThrow(() -> new PostNotFoundException("Post " + postId + " does not exist."));

		postRepository.deleteById(postId);
		ApiResponse apiResponse = new ApiResponse("Post " + postId + " has been successfully deleted", PostConstant.SUCCESS);
		return apiResponse;

	}

	@Override
	public ApiResponse deleteAllPost() {
		if (postRepository.count() == 0) {
			throw new PostDatabaseIsEmptyException("No posts found in the database.");
		}

		postRepository.deleteAll();
		ApiResponse apiResponse = new ApiResponse("All posts has been successfully deleted", PostConstant.SUCCESS);
		return apiResponse;
	}

	@Override
	public List<PostDto> getPostByCateory(Integer cateoryId) {
		Category category = categoryRepository.findById(cateoryId)
				.orElseThrow(() -> new CategoryNotFoundException("Category " + cateoryId + " does not exist."));

		List<Post> posts = postRepository.findByCategory(category);

		if (posts.isEmpty()) {
			throw new PostNotFoundException("Category " + cateoryId + " does not have any post.");
		}

		List<PostDto> postDto = posts.stream().map((post) -> postMapper.postToDto(post)).collect(Collectors.toList());
		return postDto;
	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User " + userId + " does not exist."));

		List<Post> posts = postRepository.findByUser(user);

		if (posts.isEmpty()) {
			throw new PostNotFoundException("User " + userId + " does not have any post.");
		}

		List<PostDto> postDto = posts.stream().map((post) -> postMapper.postToDto(post)).collect(Collectors.toList());
		return postDto;
	}

	@Override
	public List<PostDto> searchPostByTitle(String keywords) {
		List<Post> posts = postRepository.findByPostTitleContaining(keywords);
		if (posts.isEmpty()) {
			throw new PostNotFoundException("Kindly search with another keywords");
		}

		List<PostDto> postDto = posts.stream().map((post) -> postMapper.postToDto(post)).collect(Collectors.toList());
		return postDto;
	}

}
