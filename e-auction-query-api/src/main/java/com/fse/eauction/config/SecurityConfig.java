package com.fse.eauction.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Profile("!test")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {

		httpSecurity
		.headers()
		.xssProtection()
		.and()
		.contentSecurityPolicy("script-src 'self'");

		httpSecurity.csrf().disable()
		.authorizeRequests().antMatchers("/**")
		.permitAll();

	}
}