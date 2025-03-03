package com.website.blogapp.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.website.blogapp.entity.Comment;
import com.website.blogapp.payload.CommentDto;

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

}
