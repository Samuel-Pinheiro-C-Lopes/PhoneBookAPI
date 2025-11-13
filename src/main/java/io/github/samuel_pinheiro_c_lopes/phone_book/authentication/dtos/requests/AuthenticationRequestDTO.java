package io.github.samuel_pinheiro_c_lopes.phone_book.authentication.dtos.requests;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequestDTO(
		@NotBlank
		String username, 
		@NotBlank
		String password) {

}
