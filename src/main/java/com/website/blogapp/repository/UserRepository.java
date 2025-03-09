package com.website.blogapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.website.blogapp.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	@Query("SELECT u FROM User u WHERE u.userName LIKE CONCAT(:userNamePrefix, '%')")	// JPQL
	public List<User> searchByUserName(@Param("userNamePrefix") String userNamePrefix);
	public Optional<User> findByUserEmail(String userEmail);

}
