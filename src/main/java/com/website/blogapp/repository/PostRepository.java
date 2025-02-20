package com.website.blogapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.website.blogapp.entity.Category;
import com.website.blogapp.entity.Post;
import com.website.blogapp.entity.User;

public interface PostRepository extends JpaRepository<Post, Integer> {
	public List<Post> findByCategory(Category category);

	public List<Post> findByUser(User user);

	public List<Post> findByPostTitleContaining(String keywords);

}
