package com.website.blogapp.repository;

import java.util.List;

import com.website.blogapp.entity.Post;

public interface PostCriteriaRepository {
	List<Post> findPostByTitleAndCategory(String postTitle, Integer categoryId);

}
