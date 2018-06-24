package com.github.ttwd80.november.model.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.github.ttwd80.november.model.entity.Role;
import com.github.ttwd80.november.model.entity.User;
import com.github.ttwd80.november.model.entity.UserRole;
import com.github.ttwd80.november.model.repository.RoleRepository;
import com.github.ttwd80.november.model.repository.UserRepository;
import com.github.ttwd80.november.model.repository.UserRoleRepository;

class DatabaseServiceImplTest {

	DatabaseServiceImpl sut;

	@Mock
	UserRepository userRepository;

	@Mock
	RoleRepository roleRepository;

	@Mock
	UserRoleRepository userRoleRepository;

	@Mock
	PasswordEncoder passwordEncoder;

	@Mock
	Page<User> result;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		sut = new DatabaseServiceImpl(userRepository, roleRepository, userRoleRepository, passwordEncoder);
	}

	@Test
	void testIsAnyUserRegisteredTrue() {
		when(result.getNumberOfElements()).thenReturn(1);
		when(userRepository.findAll(any(Pageable.class))).thenReturn(result);
		assertTrue(sut.isAnyUserRegistered());
	}

	@Test
	void testIsAnyUserRegisteredFalse() {
		when(result.getNumberOfElements()).thenReturn(0);
		when(userRepository.findAll(any(Pageable.class))).thenReturn(result);
		assertFalse(sut.isAnyUserRegistered());
	}

	@Test
	void testInitDatabase() {
		sut.initDatabase("secret");
		verify(passwordEncoder).encode("secret");
		verify(userRepository).save(any(User.class));
		verify(roleRepository).save(any(Role.class));
		verify(userRoleRepository).save(any(UserRole.class));
	}

}
