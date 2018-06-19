package com.github.ttwd80.november.model.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.github.ttwd80.november.model.entity.Role;
import com.github.ttwd80.november.model.entity.User;
import com.github.ttwd80.november.model.entity.UserRole;

@ExtendWith(SpringExtension.class)
class UserRoleRepositoryTest extends AbstractRepositoryTest {

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	UserRoleRepository userRoleRepository;

	@BeforeEach
	void setUp() throws Exception {
		userRoleRepository.deleteAll();
		entityManager.createQuery("delete from User").executeUpdate();
		entityManager.createQuery("delete from Role").executeUpdate();
	}

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
