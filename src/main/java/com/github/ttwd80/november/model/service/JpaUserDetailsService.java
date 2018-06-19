package com.github.ttwd80.november.model.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.ttwd80.november.model.entity.Role;
import com.github.ttwd80.november.model.entity.UserRole;
import com.github.ttwd80.november.model.repository.UserRoleRepository;

@Service
public class JpaUserDetailsService implements UserDetailsService {

	private final UserRoleRepository userRoleRepository;

	@Autowired
	public JpaUserDetailsService(UserRoleRepository userRoleRepository) {
		super();
		this.userRoleRepository = userRoleRepository;
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		List<UserRole> items = userRoleRepository.findByUsernameUsername(username);
		if (items.size() == 0) {
			throw new UsernameNotFoundException(username);
		}
		String password = items.get(0).getUsername().getPassword();
		List<SimpleGrantedAuthority> roles = items.stream().map(UserRole::getRolename).map(Role::getRolename)
				.map(SimpleGrantedAuthority::new).collect(Collectors.toList());
		User user = new User(username, password, roles);
		return user;
	}

}
