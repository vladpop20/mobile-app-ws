package com.coodru.mobile.app.ws.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
Once a user logged in using "localhost/login" or "/user/login" (as the endpoint I created),
	the user received an Authorization token and a UserID.
E.g:
	Authorization -> Bearer eyJhbGciOiJIUzUxMiJ9
	.eyJzdWIiOiJnZW8udGVzdEBnbWFpbC5jb20iLCJleHAiOjE2NTY5NDYxMDh9
	.w4g7HYPwpaxJOTjbzBkfCvJ6HOOREtmFxjJbGw7coYG4v9GCahkLhf92RFQFGya6TRf0i3y0jLUlE-zfxuYAVQ

	UserID -> lmvrm75to8vyayNn7tqtjcvsq

Those 2 will be used by this class (Authorization-filter), in order to execute any operations on endpoint.
 */
public class AuthorizationFilter extends BasicAuthenticationFilter {


	public AuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	protected void doFilterInternal(HttpServletRequest request,
									HttpServletResponse response,
									FilterChain filterChain) throws IOException, ServletException {

		String header = request.getHeader(SecurityConstants.HEADER_STRING);


	}
}
