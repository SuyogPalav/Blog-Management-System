package com.website.blogapp.bootstrap;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.website.blogapp.constants.RoleConstant;
import com.website.blogapp.entity.Role;
import com.website.blogapp.repository.RoleRepository;

import jakarta.annotation.PostConstruct;

@Component
public class RoleInitializer implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	public RoleRepository roleRepository;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		this.roleInitialization();
	}

	@PostConstruct // This method will automatically runs after Spring initializes the bean
	public void roleInitialization() {
		Role adminRole = new Role();
		adminRole.setRoleId(RoleConstant.ROLE_ADMIN);
		adminRole.setRoleName("ROLE_ADMIN");

		Role normalRole = new Role();
		normalRole.setRoleId(RoleConstant.ROLE_NORMAL);
		normalRole.setRoleName("ROLE_NORMAL");

		List<Role> roles = List.of(adminRole, normalRole);
		roleRepository.saveAll(roles);

	}

}