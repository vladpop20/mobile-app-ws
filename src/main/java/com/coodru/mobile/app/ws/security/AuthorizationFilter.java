package com.coodru.mobile.app.ws.security;

import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/*
Once a user logged in using "localhost/login" or "/user/login" (endpoint that I created),
	the user will receive an Authorization token and a UserID.
E.g:
	Authorization -> Bearer eyJhbGciOiJIUzUxMiJ9
	.eyJzdWIiOiJnZW8udGVzdEBnbWFpbC5jb20iLCJleHAiOjE2NTY5NDYxMDh9
	.w4g7HYPwpaxJOTjbzBkfCvJ6HOOREtmFxjJbGw7coYG4v9GCahkLhf92RFQFGya6TRf0i3y0jLUlE-zfxuYAVQ

	UserID -> lmvrm75to8vyayNn7tqtjcvsq

Those 2 parameters, will be used by this class (AuthorizationFilter), in order to execute any operations on some future endpoint.
 */
public class AuthorizationFilter extends BasicAuthenticationFilter {


	public AuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}


	@Override
	protected void doFilterInternal(HttpServletRequest request,
									HttpServletResponse response,
									FilterChain filterChain) throws IOException, ServletException {

		String header = request.getHeader(SecurityConstants.HEADER_STRING);

		if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
			filterChain.doFilter(request, response);
			return;
		}

		UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		filterChain.doFilter(request, response);
	}


	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(SecurityConstants.HEADER_STRING);

		if (token != null) {
			token = token.replace(SecurityConstants.TOKEN_PREFIX, "");

			String user = Jwts.parser()
					.setSigningKey(SecurityConstants.TOKEN_SECRET)
					.parseClaimsJws(token)
					.getBody()
					.getSubject();

			if (user != null) {
				return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
			}

			return null;
		}

		return null;
	}
}
