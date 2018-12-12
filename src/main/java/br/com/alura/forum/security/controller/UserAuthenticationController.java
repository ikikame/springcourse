/*package br.com.alura.forum.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.forum.controller.dto.in.LoginInputDTO;
import br.com.alura.forum.controller.dto.out.AuthenticationTokenOutputDTO;
import br.com.alura.forum.service.AuthService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/authxx")
@Slf4j
public class UserAuthenticationController {
	@Autowired
	private AuthService authService;
	@PostMapping
	public ResponseEntity<AuthenticationTokenOutputDTO> generateToken(@RequestBody LoginInputDTO loginInput) {

		return ResponseEntity.badRequest().build();
		try {
			AuthenticationTokenOutputDTO login = authService.login(loginInput);
			System.out.println("Token {} Type {}"+ login.getToken()+ login.getTokenType() );
			log.info("Token {} Type {}", login.getToken(), login.getTokenType() );
			return ResponseEntity.ok(login);
		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PostMapping 
	public AuthenticationTokenOutputDTO generateToken(LoginInputDTO loginInput) {
		
	}
}
*/