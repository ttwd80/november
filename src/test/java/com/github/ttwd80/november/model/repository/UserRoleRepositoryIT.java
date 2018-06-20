package com.github.ttwd80.november.model.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.github.ttwd80.november.model.entity.Role;
import com.github.ttwd80.november.model.entity.User;
import com.github.ttwd80.november.model.entity.UserRole;

@ExtendWith(SpringExtension.class)
class UserRoleRepositoryIT extends AbstractRepositoryIT {

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	UserRoleRepository userRoleRepository;

	@Test
	void testNoInsertion() {
		assertEquals(0L, userRoleRepository.count());
	}

	@Test
	void testInsertSelect() {
		Role role = new Role();
		role.setRolename("ROLE_USER");
		entityManager.persist(role);

		User user = new User();
		user.setUsername("adam");
		user.setPassword("hashed");
		entityManager.persist(user);

		UserRole userRole = new UserRole();
		userRole.setUsername(user);
		userRole.setRolename(role);
		userRoleRepository.save(userRole);

		List<UserRole> items = userRoleRepository.findByUsernameUsername("adam");
		assertEquals("ROLE_USER", items.get(0).getRolename().getRolename());
	}
}
