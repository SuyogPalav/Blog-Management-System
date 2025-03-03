package com.website.blogapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.website.blogapp.constants.PostConstant;
import com.website.blogapp.entity.Comment;
import com.website.blogapp.entity.Post;
import com.website.blogapp.entity.User;
import com.website.blogapp.exception.CommentDatabaseIsEmptyException;
import com.website.blogapp.exception.PostDatabaseIsEmptyException;
import com.website.blogapp.exception.PostNotFoundException;
import com.website.blogapp.exception.UserDatabaseIsEmptyException;
import com.website.blogapp.exception.UserNotFoundException;
import com.website.blogapp.mapper.CommentMapper;
import com.website.blogapp.payload.ApiResponse;
import com.website.blogapp.payload.CommentDto;
import com.website.blogapp.repository.CommentRepository;
import com.website.blogapp.repository.PostRepository;
import com.website.blogapp.repository.UserRepository;
import com.website.blogapp.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PostServiceImpl postServiceImpl;

	@Autowired
	private CommentMapper commentMapper;

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer userId, Integer postId) {
		if (userRepository.count() == 0) {
			throw new UserDatabaseIsEmptyException("No users found in the database.");
		}

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User " + userId + " does not exist."));

		if (postRepository.count() == 0) {
			throw new PostDatabaseIsEmptyException("No posts found in the database.");
		}

		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new PostNotFoundException("Post " + postId + " does not exist."));

		Comment comment = commentMapper.dtoToComment(commentDto);
		comment.setPost(post);
		comment.setUser(user);

		commentRepository.save(comment);
		CommentDto commentDtoCreated = commentMapper.commentToDto(comment);
		return commentDtoCreated;

	}

	@Override
	public ApiResponse deleteComment(Integer commentId) {
		if (commentRepository.count() == 0) {
			throw new CommentDatabaseIsEmptyException("No comments found in the database.");
		}

		commentRepository.findById(commentId)
				.orElseThrow(() -> new PostNotFoundException("Comment " + commentId + " does not exist."));

		commentRepository.deleteById(commentId);

		ApiResponse apiResponse = ApiResponse.builder().message("Comment " + commentId + " has been successfully deleted")
				.success(PostConstant.SUCCESS).build();

		return apiResponse;

	}
	
	

}
