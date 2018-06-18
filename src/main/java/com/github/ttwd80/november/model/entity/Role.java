package com.github.ttwd80.november.model.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "[role]")
@Setter
@Getter
@NoArgsConstructor
public class Role {

	@Id
	@Column(length = 100)
	private String rolename;

	@OneToMany
	@JoinColumn(name = "rolename")
	private Set<UserRole> userRoles = new HashSet<>();

}
