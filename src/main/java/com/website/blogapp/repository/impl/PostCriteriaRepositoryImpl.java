package com.website.blogapp.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.website.blogapp.entity.Post;
import com.website.blogapp.repository.PostCriteriaRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
public class PostCriteriaRepositoryImpl implements PostCriteriaRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Post> findPostByTitleAndCategory(String postTitle, Integer categoryId) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Post> criteriaQuery = criteriaBuilder.createQuery(Post.class);

		// SELECT * FROM Post
		Root<Post> rootPost = criteriaQuery.from(Post.class);

		// WHERE clause
		Predicate titlePredicate = criteriaBuilder.like(rootPost.get("postTitle"), "%" + postTitle + "%");
		Predicate categoryPredicate = criteriaBuilder.equal(rootPost.get("category").get("categoryId"), categoryId);
		
		// Final Query: SELECT * FROM Post p WHERE p.postTitle LIKE '%{postTitle}%' AND p.category_id = {categoryId};
		criteriaQuery.where(criteriaBuilder.and(titlePredicate, categoryPredicate));
		
		TypedQuery<Post> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();

	}

}
