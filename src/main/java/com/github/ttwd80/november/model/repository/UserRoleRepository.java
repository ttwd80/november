package com.github.ttwd80.november.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.ttwd80.november.model.entity.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

	List<UserRole> findByUsernameUsername(String username);
}
