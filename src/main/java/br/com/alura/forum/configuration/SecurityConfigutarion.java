package br.com.alura.forum.configuration;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.alura.forum.security.JWTAuthenticationFilter;
import br.com.alura.forum.security.UsersService;
import br.com.alura.forum.service.TokenManagerService;

@Configuration
@EnableWebSecurity
public class SecurityConfigutarion extends WebSecurityConfigurerAdapter {
	@Autowired
	private UsersService usersService;
	@Autowired
	private TokenManagerService tokenManagerService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/api/topics").permitAll()
			.antMatchers("/api/topics/dashboard").permitAll()
			.antMatchers("/api/auth").permitAll()
			.anyRequest().authenticated()
			.and().cors()
			.and().csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and().addFilterBefore(new JWTAuthenticationFilter(usersService, tokenManagerService),
						UsernamePasswordAuthenticationFilter.class)
			.exceptionHandling().authenticationEntryPoint(new JWTErrorsHandler());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/api/auth", "/**.html", "/v2/api-docs", "/webjars/**", "/configuration/**",
				"/swagger-resources/**", "/css/**", "/**.ico", "/js/**");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(usersService).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	private static class JWTErrorsHandler implements AuthenticationEntryPoint {

		@Override
		public void commence(HttpServletRequest request, HttpServletResponse response,
				AuthenticationException authException) throws IOException, ServletException {
			response.sendError(HttpStatus.UNAUTHORIZED.value(), "Você não tem autorização para acessar");
			//log.error("Um acesso não autorizado foi verificado. Mensagem {}", authException.getMessage());
		}

	}
}
