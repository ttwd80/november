package com.github.ttwd80.november.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "[user_role]", uniqueConstraints = { @UniqueConstraint(columnNames = { "username", "rolename" }) })

@Setter
@Getter
@NoArgsConstructor

public class UserRole {

	@Id
	@SequenceGenerator(name = "user_role_id_seq", sequenceName = "user_role_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_role_id_seq")
	@Column(name = "id", updatable = false)
	private long id;

	@ManyToOne
	@JoinColumn(name = "username")
	private User username;

	@ManyToOne
	@JoinColumn(name = "rolename")
	private Role rolename;
}
