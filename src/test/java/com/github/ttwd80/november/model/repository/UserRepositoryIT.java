package com.github.ttwd80.november.model.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.github.ttwd80.november.model.entity.User;

@ExtendWith(SpringExtension.class)
class UserRepositoryIT extends AbstractRepositoryIT {

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@BeforeEach
	void setUp() throws Exception {
		userRepository.deleteAll();
	}

	@Test
	void testNoInsertion() {
		assertEquals(0L, userRepository.count());
	}

	@Test
	void testInsertSelect() {
		User user = new User();
		user.setUsername("adam");
		user.setPassword("adam123");
		userRepository.save(user);
		Optional<User> db = userRepository.findById("adam");
		assertEquals("adam123", db.get().getPassword());
	}

	@Test
	void testPasswordEncoder() {
		String hash = passwordEncoder.encode("password");
		User user = new User();
		user.setUsername("adam");
		user.setPassword(hash);
		userRepository.save(user);
		Optional<User> db = userRepository.findById("adam");
		assertTrue(passwordEncoder.matches("password", db.get().getPassword()));

	}

}
