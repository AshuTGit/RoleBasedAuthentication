package com.auth.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

	private final PasswordEncoder passwordEncoder;
	@Autowired
	public  ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// the below statement is like - the request has to be authorized and that would be any kind
		// of request, the request should be authenticated with the proper username
		// and password and the basic type of authentication is there
		//************************
		// This is a basic type of authentication
		// AntMatcher and permitAll have been added in a next run to
		//allow some files... and permitAll is to permit the antMatcher contents
		
		http
		.csrf().disable()
		.authorizeRequests()
		.antMatchers("/","index","/css/*","/js/*").permitAll()
		.antMatchers("/management/**").hasRole(ApplicationUserRole.STUDENT.name())
	//	.antMatchers("/management/**").hasAnyRole(ApplicationUserRole.STUDENT.name(), ApplicationUserRole.ADMIN.name())
	//	.antMatchers(HttpMethod.DELETE,"/management/v1/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
	//	.antMatchers(HttpMethod.POST, "/management/v1/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
	//	.antMatchers(HttpMethod.PUT,"/management/v1/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
	//	.antMatchers(HttpMethod.GET, "/management/v1/**").hasAnyRole( ApplicationUserRole.SUPER_ADMIN.name())
		.anyRequest()
		.authenticated()
		.and()
		.httpBasic();
	}
	
	@Override
	@Bean
	protected UserDetailsService userDetailsService() {
		UserDetails shiviUser= User.builder()
			.username("Shivi")
			.password(passwordEncoder.encode("Password@123"))
//			.roles(ApplicationUserRole.STUDENT.name()) //ROLE_STUDENT
			.authorities(ApplicationUserRole.STUDENT.getGrantedAuthorities())
			.build();
		
		UserDetails ashuUser= User.builder()
		.username("Ashu")
		.password(passwordEncoder.encode("Password@123"))
//		.roles(ApplicationUserRole.ADMIN.name()) //ROLE_ANMIN
		.authorities(ApplicationUserRole.ADMIN.getGrantedAuthorities())
		.build();
		
		UserDetails superUser= User.builder()
				.username("Super")
				.password(passwordEncoder.encode("Password@123"))
//				.roles(ApplicationUserRole.SUPER_ADMIN.name())
				.authorities(ApplicationUserRole.SUPER_ADMIN.getGrantedAuthorities())
				.build();
		
		return new InMemoryUserDetailsManager(shiviUser, ashuUser, superUser);
	}
}
