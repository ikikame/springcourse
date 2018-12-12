package br.com.alura.forum.controller;

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

@RestController
//@Slf4j
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@PostMapping
	public ResponseEntity<AuthenticationTokenOutputDTO> generateToken(@RequestBody LoginInputDTO loginInput) {
		try {
			AuthenticationTokenOutputDTO login = authService.login(loginInput);
			return ResponseEntity.ok(login);
		} catch (AuthenticationException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
	}

}
