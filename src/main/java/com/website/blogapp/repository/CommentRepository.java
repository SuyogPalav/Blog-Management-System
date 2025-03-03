package com.website.blogapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.website.blogapp.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

}
