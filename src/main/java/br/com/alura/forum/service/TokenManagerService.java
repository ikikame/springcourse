package br.com.alura.forum.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.alura.forum.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Service
public class TokenManagerService {

	@Value(value = "${mp.jwt.key}")
	public String key;
	@Value(value = "${mp.jwt.expiration}")
	private String expiration;

	public String generateToken(Authentication auth) {
		User user = (User)auth.getPrincipal(); 
		return Jwts.builder()
			.setIssuer("Forum Alura")
			.setIssuedAt(new Date())
			.setSubject(user.getId().toString())
			.setExpiration(new Date((new Date()).getTime() + Long.parseLong(this.expiration)))
			.signWith(SignatureAlgorithm.HS256, this.key)
			.compact();
	}

	public boolean isValid(String token) {
		try {
			Jwts.parser().setSigningKey(key).parseClaimsJws(token);
			return true;
		} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException
				| IllegalArgumentException e) {
			return false;
		}
	}
	
	public Long getUserIdFromToken(String jwt) {
		Claims claims = Jwts.parser().setSigningKey(this.key).parseClaimsJws(jwt).getBody();
		return Long.parseLong(claims.getSubject());
	}
}
