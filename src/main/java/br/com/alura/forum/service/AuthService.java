package br.com.alura.forum.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.alura.forum.controller.dto.in.LoginInputDTO;
import br.com.alura.forum.controller.dto.out.AuthenticationTokenOutputDTO;

@Service
public class AuthService {
	@Autowired
	private AuthenticationManager authManager;
	@Autowired
	private TokenManagerService tokenManager;

	@PostMapping
	public AuthenticationTokenOutputDTO login(LoginInputDTO loginInput) throws AuthenticationException {
		UsernamePasswordAuthenticationToken authToken = loginInput.authenticationToken();
		Authentication auth = authManager.authenticate(authToken);
		String token = tokenManager.generateToken(auth);
		return new AuthenticationTokenOutputDTO(token, "Bearer");
	}
}
