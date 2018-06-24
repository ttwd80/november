package com.github.ttwd80.november.model.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.github.ttwd80.november.model.entity.Role;
import com.github.ttwd80.november.model.entity.User;
import com.github.ttwd80.november.model.entity.UserRole;
import com.github.ttwd80.november.model.repository.UserRoleRepository;

class JpaUserDetailsServiceTest {

	JpaUserDetailsService sut;

	@Mock
	UserRoleRepository userRoleRepository;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		sut = new JpaUserDetailsService(userRoleRepository);
	}

	@Test
	void testLoadUserByUsernameNoUser() {
		when(userRoleRepository.findByUsernameUsername("random")).thenReturn(Collections.emptyList());
		assertThrows(UsernameNotFoundException.class, () -> sut.loadUserByUsername("random"));
	}

	@Test
	void testLoadUserByUsernameSingleRole() {
		User user = User.builder().password("w7fk2ssk").build();
		Role role = Role.builder().rolename("ROLE_ADMIN").build();
		UserRole userRole = UserRole.builder().username(user).rolename(role).build();
		List<UserRole> list = Arrays.asList(userRole);
		when(userRoleRepository.findByUsernameUsername("user32")).thenReturn(list);
		UserDetails userDetails = sut.loadUserByUsername("user32");
		assertEquals("user32", userDetails.getUsername());
		assertEquals("w7fk2ssk", userDetails.getPassword());
		assertEquals(Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")),
				new ArrayList<>(userDetails.getAuthorities()));
	}

}
