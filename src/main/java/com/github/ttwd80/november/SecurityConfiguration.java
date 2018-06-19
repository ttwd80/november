package com.github.ttwd80.november;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Override
	public void configure(final HttpSecurity http) throws Exception {
		final String[] patterns = { "/", "/setup/**", "/webjars/**", "/login" };
		http.authorizeRequests().antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
				.antMatchers("/user/**", "/content").access("hasRole('ROLE_USER')").antMatchers(patterns).anonymous()
				.and().csrf().and().formLogin();
	}

}
