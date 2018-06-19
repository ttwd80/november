package com.github.ttwd80.november.model.service;

public interface DatabaseService {
	boolean isAnyUserRegistered();

	void initDatabase(String initialAdminPassword);
}
