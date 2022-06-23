package com.coodru.mobile.app.ws.security;

import com.coodru.mobile.app.ws.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@EnableWebSecurity	// this annotation also includes @Configuration
public class SecurityConfig {

	private final UserRepository userRepository;
	private final AuthenticationConfiguration authenticationConfiguration;


	public SecurityConfig(UserRepository userRepository, AuthenticationConfiguration authenticationConfiguration) {
		this.userRepository = userRepository;
		this.authenticationConfiguration = authenticationConfiguration;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.csrf().disable().authorizeRequests()
				.antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL).permitAll()
//				.anyRequest().authenticated().and().addFilter(new AuthenticationFilter());
				.anyRequest().authenticated().and().addFilter(new AuthenticationFilter(authenticationManager()));

		return http.build();
	}


	@Bean
//	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
	public AuthenticationManager authenticationManager() throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
