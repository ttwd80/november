package com.github.ttwd80.november.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.ttwd80.november.model.entity.Role;
import com.github.ttwd80.november.model.entity.User;
import com.github.ttwd80.november.model.entity.UserRole;
import com.github.ttwd80.november.model.repository.RoleRepository;
import com.github.ttwd80.november.model.repository.UserRepository;
import com.github.ttwd80.november.model.repository.UserRoleRepository;

@Service
public class DatabaseServiceImpl implements DatabaseService {

	private final UserRepository userRepository;

	private final RoleRepository roleRepository;

	private final UserRoleRepository userRoleRepository;

	private final PasswordEncoder passwordEncoder;

	@Autowired
	public DatabaseServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
			UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
		super();
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.userRoleRepository = userRoleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isAnyUserRegistered() {
		Page<User> result = userRepository.findAll(PageRequest.of(0, 1));
		return result.getNumberOfElements() > 0;
	}

	@Override
	@Transactional
	public void initDatabase(String initialAdminPassword) {
		final String hashed = passwordEncoder.encode(initialAdminPassword);
		User user = User.builder().username("admin").password(hashed).build();
		user = userRepository.save(user);

		String[] roles = { "ROLE_ADMIN", "ROLE_USER" };
		for (String value : roles) {
			Role role = Role.builder().rolename(value).build();
			role = roleRepository.save(role);

			final UserRole userRole = UserRole.builder().username(user).rolename(role).build();
			userRoleRepository.save(userRole);
		}
	}
}
