package com.github.ttwd80.november.model.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.github.ttwd80.november.model.entity.User;
import com.github.ttwd80.november.model.service.DatabaseTestInit;
import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;
import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

@DataJpaTest
@Rollback(false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Slf4j
public class AbstractRepositoryIT {

	@PersistenceContext
	protected EntityManager entityManager;

	@Autowired
	PlatformTransactionManager transactionManager;

	@BeforeEach
	@Transactional
	@Rollback(false)
	protected void setUp() throws Exception {
		final Queue<String> queue = new LinkedList<>(listEntities());
		log.info("trying to delete " + queue);
		TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
		transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		while (!queue.isEmpty()) {
			String entity = queue.poll();
			TransactionCallback<Object> transactionCallback = (ts) -> {
				entityManager.createQuery("delete from " + entity).executeUpdate();
				return null;
			};
			try {
				transactionTemplate.execute(transactionCallback);
			} catch (PersistenceException e) {
				queue.offer(entity);
			}
		}
		log.info("Data cleared");
	}

	private List<String> listEntities() throws IOException {
		List<String> list = new ArrayList<>();
		Class<User> c = User.class;
		ClassPath x = ClassPath.from(c.getClassLoader());
		String name = c.getName();
		int index = name.lastIndexOf('.');
		if (index >= 0) {
			String packageName = name.substring(0, index);
			ImmutableSet<ClassInfo> items = x.getTopLevelClasses(packageName);
			for (ClassInfo item : items) {
				list.add(item.getSimpleName());
			}
		} else {
			log.info("Unable to process class with name : {}", name);
		}
		Collections.sort(list);
		return list;
	}

	@TestConfiguration
	static class NestedConfiguration {

		@Bean
		public DataSource dataSource() throws Exception {
			HikariDataSource dataSource = new HikariDataSource();
			Map<String, String> map = System.getenv();
			dataSource.setUsername(map.get("SPRING_DATASOURCE_USERNAME"));
			dataSource.setPassword(map.get("SPRING_DATASOURCE_PASSWORD"));
			dataSource.setJdbcUrl(map.get("SPRING_DATASOURCE_URL"));
			dataSource.setDriverClassName("org.postgresql.Driver");
			dataSource.setAutoCommit(false);
			DatabaseTestInit databaseTestInit = new DatabaseTestInit(dataSource);
			databaseTestInit.afterPropertiesSet();
			return dataSource;
		}
	}

}
