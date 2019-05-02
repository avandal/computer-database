package com.excilys.computer_database.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	HikariDataSource dataSource;
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.jdbcAuthentication().dataSource(dataSource)
		.usersByUsernameQuery(
			"select username, password, enabled from user where username = ?")
		.authoritiesByUsernameQuery(
			"select u.username, r.role from user u join role r on u.id = r.user_id where u.username = ?")
		.passwordEncoder(new BCryptPasswordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.authorizeRequests()
			.antMatchers("/", "/dashabord").permitAll()
			.antMatchers("/computer/edit", "/computer/delete").hasAuthority("admin")
			.antMatchers("/computer/new").hasAnyAuthority("admin", "guest")
			.antMatchers("/loginProcess").permitAll()
		.and()
			.formLogin()
			.loginPage("/login")
			.loginProcessingUrl("/loginProcess")
			.usernameParameter("username")
			.passwordParameter("password")
			.defaultSuccessUrl("/dashboard")
			.failureForwardUrl("/login?error")
		.and()
			.logout()
			.logoutSuccessUrl("/login?logout")
			.logoutUrl("/logoutProcess");
	}
}
