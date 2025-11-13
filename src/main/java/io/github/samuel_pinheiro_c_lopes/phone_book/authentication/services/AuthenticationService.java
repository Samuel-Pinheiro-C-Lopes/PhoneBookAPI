package io.github.samuel_pinheiro_c_lopes.phone_book.authentication.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.github.samuel_pinheiro_c_lopes.phone_book.authentication.repositories.UserRepository;

@Service
public class AuthenticationService implements UserDetailsService {
	private final UserRepository userRepository;
	
	@Autowired
	public AuthenticationService(final UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return this.userRepository.findByUsername(username);
	}
}
