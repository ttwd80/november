package com.github.ttwd80.november.model.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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

	@ParameterizedTest
	@CsvSource({ "user32, w7fk2ssk, 'ROLE_ADMIN'", "user33, bi68aycu, 'ROLE_ADMIN, ROLE_USER'" })
	void testLoadUserByUsernameSingleRole(String username, String userPassword, String concatenatedUserRoles) {
		List<UserRole> list = new ArrayList<>();
		String[] userRoles = StringUtils.split(concatenatedUserRoles, ',');
		User user = User.builder().username(username).password(userPassword).build();
		for (String userRoleValue : userRoles) {
			Role role = Role.builder().rolename(userRoleValue).build();
			UserRole userRole = UserRole.builder().username(user).rolename(role).build();
			list.add(userRole);
		}

		when(userRoleRepository.findByUsernameUsername(username)).thenReturn(list);
		UserDetails userDetails = sut.loadUserByUsername(username);
		assertEquals(username, userDetails.getUsername());
		assertEquals(userPassword, userDetails.getPassword());
		List<String> expectedRoles = Stream.of(userRoles).sorted().collect(Collectors.toList());
		List<String> actualRoles = userDetails.getAuthorities().stream().map(Object::toString).sorted()
				.collect(Collectors.toList());
		assertEquals(expectedRoles, actualRoles);
	}

}
