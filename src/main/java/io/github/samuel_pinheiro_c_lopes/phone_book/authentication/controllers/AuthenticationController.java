package io.github.samuel_pinheiro_c_lopes.phone_book.authentication.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.samuel_pinheiro_c_lopes.phone_book.authentication.dtos.requests.AuthenticationRequestDTO;
import io.github.samuel_pinheiro_c_lopes.phone_book.authentication.dtos.requests.UserRequestDTO;
import io.github.samuel_pinheiro_c_lopes.phone_book.authentication.dtos.responses.AuthenticationResponseDTO;
import io.github.samuel_pinheiro_c_lopes.phone_book.authentication.models.User;
import io.github.samuel_pinheiro_c_lopes.phone_book.authentication.services.JWTService;
import io.github.samuel_pinheiro_c_lopes.phone_book.authentication.services.UserService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	private final JWTService jwtService;
	private final AuthenticationManager authenticationManager;
	private final UserService userService;
	
	@Autowired
	public AuthenticationController(
			final JWTService jwtService, 
			final AuthenticationManager authenticationManager, 
			final UserService userService
	) {
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
		this.userService = userService;
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponseDTO> login(@RequestBody final AuthenticationRequestDTO authenticationRequestDTO) {
		var securityToken = new UsernamePasswordAuthenticationToken(authenticationRequestDTO.username(), authenticationRequestDTO.password());
		var authentication = this.authenticationManager.authenticate(securityToken);
		String jwt = this.jwtService.getToken((User) authentication.getPrincipal());
		return ResponseEntity.ok(new AuthenticationResponseDTO(jwt));
	}
	
	@PostMapping("/register")
	public ResponseEntity<Void> register(@RequestBody final UserRequestDTO userRequestDTO) {
		this.userService.save(userRequestDTO);
		return ResponseEntity.ok().<Void>build();
	}
}
