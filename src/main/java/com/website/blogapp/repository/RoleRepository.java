package com.website.blogapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.website.blogapp.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
