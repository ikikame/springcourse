package br.com.alura.forum.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.alura.forum.service.TokenManagerService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

	private UsersService usersService;
	private TokenManagerService tokenManager;

	private String getTokenFromHeader(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		
		if (StringUtils.hasText(bearerToken) && (bearerToken.startsWith("Bearer ")))
			return bearerToken.substring(7, bearerToken.length());
		return null;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = getTokenFromHeader(request);
		if (tokenManager.isValid(token)) {
			Long userId = tokenManager.getUserIdFromToken(token);
			UserDetails userDetails = usersService.loadUserByID(userId);
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
					null, userDetails.getAuthorities());

			SecurityContextHolder.getContext().setAuthentication(authentication);
		} 
		filterChain.doFilter(request, response);
	}
}
