package io.github.samuel_pinheiro_c_lopes.phone_book.authentication.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.github.samuel_pinheiro_c_lopes.phone_book.authentication.dtos.requests.UserRequestDTO;
import io.github.samuel_pinheiro_c_lopes.phone_book.authentication.models.User;
import io.github.samuel_pinheiro_c_lopes.phone_book.authentication.repositories.UserRepository;


@Service
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public UserService(final UserRepository userRepository, final PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	public void save(final UserRequestDTO userRequest) {
		final User user = userRequest.toUser();
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		this.userRepository.save(user);
	}
}
