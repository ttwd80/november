package com.github.ttwd80.november.model.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "[user]")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

	@Id
	@Column(length = 100)
	private String username;

	@Column(length = 60)
	private String password;

	@OneToMany
	@JoinColumn(name = "username")
	@Builder.Default
	private Set<UserRole> userRoles = new HashSet<>();

}
