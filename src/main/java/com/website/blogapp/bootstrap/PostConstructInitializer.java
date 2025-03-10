package com.website.blogapp.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.website.blogapp.constants.AdminRoleConstant;
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
		User user = userRepository.findByUserEmail(AdminRoleConstant.ADMIN_EMAIL);

		if (user == null) {
			User adminUser = new User();
			adminUser.setUserName(AdminRoleConstant.ADMIN_NAME);
			adminUser.setUserEmail(AdminRoleConstant.ADMIN_EMAIL);
			String encodedPassword = passwordEncoder.encode(AdminRoleConstant.ADMIN_PASSWORD);
			adminUser.setUserPassword(encodedPassword);
			adminUser.setUserAbout(AdminRoleConstant.ADMIN_ABOUT);
			Role role = roleRepository.findById(RoleConstant.ROLE_ADMIN)
					.orElseThrow(() -> new AdminRoleNotFoundException("Admin role not found!"));
			adminUser.getRoles().add(role);
			userRepository.save(adminUser);

		}

	}

}