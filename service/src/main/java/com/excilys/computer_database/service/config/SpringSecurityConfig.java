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
			"select u.username, r.role from user u join role r on u.username = r.username where u.username = ?")
		.passwordEncoder(new BCryptPasswordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.authorizeRequests()
			.antMatchers("/computer/new", "/computer/edit", "/computer/delete").hasAuthority("admin")
			.antMatchers("/", "/dashboard").hasAnyAuthority("admin", "guest")
			.antMatchers("/login", "/login/signup", "login/create", "/loginProcess").permitAll()
		.and()
			.formLogin()
			.loginPage("/login")
			.loginProcessingUrl("/loginProcess")
			.usernameParameter("username")
			.passwordParameter("password")
			.defaultSuccessUrl("/dashboard")
			.failureUrl("/login?error=true")
		.and()
			.logout()
			.logoutSuccessUrl("/login?logout")
			.logoutUrl("/logoutProcess")
		.and()
			.exceptionHandling().accessDeniedPage("/403");
	}
}
