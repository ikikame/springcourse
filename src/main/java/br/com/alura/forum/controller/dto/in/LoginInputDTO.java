package br.com.alura.forum.controller.dto.in;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginInputDTO {
	private String email;
	private String password;
	
	public UsernamePasswordAuthenticationToken authenticationToken() {
		return new UsernamePasswordAuthenticationToken(email, password);
	}
}
