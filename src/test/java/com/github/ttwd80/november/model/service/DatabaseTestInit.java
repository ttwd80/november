package com.github.ttwd80.november.model.service;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

public class DatabaseTestInit implements InitializingBean {

	private final JdbcTemplate jdbcTemplate;

	public DatabaseTestInit(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private void dropTables() {
		final List<String> tables = new ArrayList<>();
		tables.add("user_role");
		tables.add("role");
		tables.add("user");
		for (String table : tables) {
			jdbcTemplate.execute("DROP TABLE IF EXISTS \"" + table + "\" CASCADE");
		}
	}

	private void dropSequences() {
		//
		final List<String> sequences = new ArrayList<>();
		sequences.add("user_role_id_seq");
		for (String sequence : sequences) {
			jdbcTemplate.execute("DROP SEQUENCE IF EXISTS \"" + sequence + "\" CASCADE");
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		DataSourceTransactionManager txManager = new DataSourceTransactionManager(jdbcTemplate.getDataSource());
		TransactionTemplate txTemplate = new TransactionTemplate(txManager);
		txTemplate.setPropagationBehaviorName("PROPAGATION_REQUIRED");
		TransactionCallback<Object> action = (ts) -> {
			dropTables();
			dropSequences();
			return null;
		};
		txTemplate.execute(action);
	};
}
