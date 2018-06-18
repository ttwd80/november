package com.github.ttwd80.november.model.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration({ "classpath:test-database-blank.xml" })
class DatabaseSetupServiceImplIT {

	@PersistenceContext
	EntityManager entityManager;

}
