package com.github.ttwd80.november;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Override
	public void configure(final WebSecurity web) throws Exception {
		final String[] patterns = { "/", "/setup" };
		web.ignoring().antMatchers(patterns);
	}

}
