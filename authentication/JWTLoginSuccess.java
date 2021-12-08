package br.com.bernhoeft.meetings.authentication;


import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JWTLoginSuccess extends BasicAuthenticationFilter{

	public JWTLoginSuccess(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			Authentication authResult) throws IOException {
		
		JWTUtil.generate(response, authResult);
	}

}
