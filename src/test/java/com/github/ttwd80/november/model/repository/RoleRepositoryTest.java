package com.github.ttwd80.november.model.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.github.ttwd80.november.model.entity.Role;

@ExtendWith(SpringExtension.class)
class RoleRepositoryTest extends AbstractRepositoryTest {

	@Autowired
	RoleRepository roleRepository;

	@BeforeEach
	void setUp() throws Exception {
		roleRepository.deleteAll();
	}

	@Test
	void testNoInsertion() {
		assertEquals(0L, roleRepository.count());
	}

	@Test
	void testInsertSelect() {
		Role role = new Role();
		role.setRolename("ROLE_ADMIN");
		roleRepository.save(role);
		Optional<Role> db = roleRepository.findById("ROLE_ADMIN");
		assertEquals("ROLE_ADMIN", db.get().getRolename());
	}
}
