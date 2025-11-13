package io.github.samuel_pinheiro_c_lopes.phone_book.authentication.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import io.github.samuel_pinheiro_c_lopes.phone_book.authentication.models.User;

@Service
public class JWTService {
	private final Algorithm algorithm;
	private final String secret;
	private final String issuer;
	
	public JWTService(
	        @Value("${app.authentication.token.secret}") final String secret,
	        @Value("${app.authentication.token.issuer}") final String issuer
        ) {
	        this.secret = secret;
	        this.issuer = issuer;
	        this.algorithm = Algorithm.HMAC256(this.secret); 
	    }
	
	public String getSubject(final String jwt) {
		try {
			return JWT.require(algorithm)
					.withIssuer(this.issuer)
					.build()
					.verify(jwt)
					.getSubject();
		} catch (JWTVerificationException ex) {
			throw new RuntimeException("Error when trying to verify JWT token: " + ex.getMessage());
		}
	}
	
	public String getToken(final User user) {
		try {
			return JWT.create()
						.withIssuer(this.issuer)
						.withSubject(user.getUsername())
						.withExpiresAt(this.getExpirationDate())
						.sign(algorithm);
		} catch (JWTCreationException ex) {
			throw new RuntimeException("Error when trying to generate JWT token for " + user.getUsername() + ":" + ex.getMessage());
		}
	}
	
	private Instant getExpirationDate() {
		return LocalDateTime
				.now()
				.plusHours(1l)
				.toInstant(ZoneOffset.of("-03:00"));
	}
}
