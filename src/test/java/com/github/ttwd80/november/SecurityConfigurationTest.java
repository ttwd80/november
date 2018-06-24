package com.github.ttwd80.november;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;

class SecurityConfigurationTest {
	SecurityConfiguration sut;

	@Mock
	ObjectPostProcessor<Object> objectPostProcessor;

	@Mock
	ApplicationContext applicationContext;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		sut = new SecurityConfiguration();
	}

	@Test
	void testConfigureWebSecurityDontCare() throws Exception {
		WebSecurity web = new WebSecurity(objectPostProcessor);
		web.setApplicationContext(applicationContext);
		sut.configure(web);
	}

	@Test
	void testConfigureHttpSecurityDontCare() throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder = new AuthenticationManagerBuilder(
				objectPostProcessor);
		Map<Class<? extends Object>, Object> sharedObjects = new HashMap<>();
		sharedObjects.put(ApplicationContext.class, applicationContext);
		HttpSecurity httpSecurity = new HttpSecurity(objectPostProcessor, authenticationManagerBuilder, sharedObjects);

		sut.configure(httpSecurity);
	}

}
