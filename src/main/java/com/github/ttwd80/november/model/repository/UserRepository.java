package com.github.ttwd80.november.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.ttwd80.november.model.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

}
