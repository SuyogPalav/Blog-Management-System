package com.website.blogapp.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.website.blogapp.entity.Comment;
import com.website.blogapp.payload.CommentDto;
import com.website.blogapp.payload.CommentResponseDto;

@Component
public class CommentMapper {
	@Autowired
	private ModelMapper modelMapper;

	public Comment dtoToComment(CommentDto commentDto) {
		Comment comment = modelMapper.map(commentDto, Comment.class);
		return comment;
	}

	public CommentDto commentToDto(Comment comment) {
		CommentDto commentDto = modelMapper.map(comment, CommentDto.class);
		return commentDto;
	}
	
	public CommentResponseDto commentResponseDto(Comment comment) {
		CommentResponseDto commentResponseDto = modelMapper.map(comment, CommentResponseDto.class);
		return commentResponseDto;
	}

}
