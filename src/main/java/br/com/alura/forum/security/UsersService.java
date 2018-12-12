package br.com.alura.forum.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.alura.forum.controller.repository.UserRepository;
import br.com.alura.forum.model.User;

@Service
public class UsersService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		Optional<User> possibleUser = userRepository.findByEmail(username);
		return possibleUser.orElseThrow(() -> 
			new UsernameNotFoundException("Não foi possível encontrar o usuário com e-mail: " + username)
			);
	}
	
	public UserDetails loadUserByID(Long id) {
		Optional<User> possibleUser = userRepository.findById(id);
		return possibleUser.orElseThrow(() -> 
			new UsernameNotFoundException("Não foi possível encontrar o usuário" )
			);
	}
}
