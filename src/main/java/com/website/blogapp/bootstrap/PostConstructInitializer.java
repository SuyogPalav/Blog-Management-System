package com.website.blogapp.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.website.blogapp.constants.RoleConstant;
import com.website.blogapp.entity.Role;
import com.website.blogapp.entity.User;
import com.website.blogapp.exception.AdminRoleNotFoundException;
import com.website.blogapp.repository.RoleRepository;
import com.website.blogapp.repository.UserRepository;

import jakarta.annotation.PostConstruct;

@Component
public class PostConstructInitializer {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	public UserRepository userRepository;

	@Autowired
	public RoleRepository roleRepository;

	@PostConstruct // This method will automatically runs after Spring initializes the bean
	public void roleInitialization() {
		// Check if ROLE_ADMIN exists, if not, create it
		if (roleRepository.findById(RoleConstant.ROLE_ADMIN).isEmpty()) {
			Role adminRole = new Role();
			adminRole.setRoleId(RoleConstant.ROLE_ADMIN);
			adminRole.setRoleName("ROLE_ADMIN");
			roleRepository.save(adminRole);
		}

		// Check if ROLE_NORMAL exists, if not, create it
		if (roleRepository.findById(RoleConstant.ROLE_NORMAL).isEmpty()) {
			Role normalRole = new Role();
			normalRole.setRoleId(RoleConstant.ROLE_NORMAL);
			normalRole.setRoleName("ROLE_NORMAL");
			roleRepository.save(normalRole);
		}
	}

	@PostConstruct
	public void adminInitialization() {
		User user = userRepository.findByUserEmail("suyog@admin.com");

		if (user == null) {
			User adminUser = new User();
			adminUser.setUserName("Suyog");
			adminUser.setUserEmail("suyog@admin.com");
			String encodedPassword = passwordEncoder.encode("admin@123");
			adminUser.setUserPassword(encodedPassword);
			adminUser.setUserAbout("I am the Admin!");
			Role role = roleRepository.findById(RoleConstant.ROLE_ADMIN)
					.orElseThrow(() -> new AdminRoleNotFoundException("Admin role not found!"));
			adminUser.getRoles().add(role);
			userRepository.save(adminUser);

		}

	}

}