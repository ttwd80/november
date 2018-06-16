package com.github.ttwd80.november.model.service;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DatabaseServiceImpl implements DatabaseService {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public DatabaseServiceImpl(DataSource dataSource) {
		super();
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public boolean isSetuUp() {
		final String sql = "select count(*) from information_schema.tables  where table_schema = ? and table_name = ?";
		Long count = jdbcTemplate.queryForObject(sql, Long.class, "public", "user");
		return count > 0;
	}

}
