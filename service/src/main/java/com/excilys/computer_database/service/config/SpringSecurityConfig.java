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
			"select username,password, enabled from user where username = ?")
		.authoritiesByUsernameQuery(
			"select user_role.username username, role.name auhority from user_role join role on role.id = user_role.idRole where user_role.username = ?")
		.passwordEncoder(new BCryptPasswordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.authorizeRequests()
			.antMatchers("/dashboard").hasAuthority("ADMIN")
			.antMatchers("/editComputer").hasAuthority("ADMIN")
//			.antMatchers("/deleteComputer").hasAuthority("ADMIN")
			.antMatchers("/").authenticated()
//			.antMatchers("/FindComputerByName").authenticated()
			.antMatchers("/LoginProcess").permitAll()
		.and()
			.formLogin()
			.loginPage("/login")
			.loginProcessingUrl("/loginProcess")
			.usernameParameter("username")
			.passwordParameter("password")
			.defaultSuccessUrl("/")
			.failureForwardUrl("/login?error")
		.and()
			.logout()
			.logoutSuccessUrl("/login?logout")
			.logoutUrl("/logoutProcess");
	}
}
