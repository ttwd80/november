package com.github.ttwd80.november.model.repository;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.github.ttwd80.november.model.service.DatabaseTestInit;
import com.zaxxer.hikari.HikariDataSource;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class AbstractRepositoryTest {
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
