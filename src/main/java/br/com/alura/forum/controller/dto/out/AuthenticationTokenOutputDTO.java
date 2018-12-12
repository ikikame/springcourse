package br.com.alura.forum.controller.dto.out;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthenticationTokenOutputDTO {
	private String token;
	private String tokenType;
}
