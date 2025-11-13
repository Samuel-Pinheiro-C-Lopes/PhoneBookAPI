package io.github.samuel_pinheiro_c_lopes.phone_book.authentication.configuration;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.github.samuel_pinheiro_c_lopes.phone_book.authentication.models.User;
import io.github.samuel_pinheiro_c_lopes.phone_book.authentication.repositories.UserRepository;
import io.github.samuel_pinheiro_c_lopes.phone_book.authentication.services.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {
	private final String AUTHORIZATION_HEADER =  "Authorization";
	private final String AUTHORIZATION_PREFIX = "Bearer";
	
	private final JWTService jwtokenService;
	private final UserRepository userRepository;
	
	@Autowired
	public SecurityFilter(final JWTService jwtokenService, final UserRepository userRepository) {
		this.jwtokenService = jwtokenService;
		this.userRepository = userRepository;
	}
	
	@Override
	protected void doFilterInternal(
			final HttpServletRequest request, 
			final HttpServletResponse response, 
			final FilterChain filterChain
	) throws ServletException, IOException {
		final String token = this.retrieveToken(request);
		
		if (token != null)
			this.setContextAuthentication(token);
		
		filterChain.doFilter(request, response);
	}
	
	private void setContextAuthentication(final String token) {
		final String username = jwtokenService.getSubject(token);
		final User user = this.userRepository.findByUsername(username);
		final var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
	
	private String retrieveToken(final HttpServletRequest request) {
		final String token = request.getHeader(AUTHORIZATION_HEADER);
		
		if (token == null || token.isEmpty() || !token.startsWith(AUTHORIZATION_PREFIX))
			return null;
		
		return token;
	}
}