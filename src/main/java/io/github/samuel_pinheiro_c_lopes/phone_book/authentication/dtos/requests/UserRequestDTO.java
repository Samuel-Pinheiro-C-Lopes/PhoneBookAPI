package io.github.samuel_pinheiro_c_lopes.phone_book.authentication.dtos.requests;

import io.github.samuel_pinheiro_c_lopes.phone_book.authentication.models.User;
import jakarta.validation.constraints.NotBlank;

public record UserRequestDTO(
		@NotBlank
		String username,
		@NotBlank
		String password
	) {
	public User toUser() {
		return new User(username, password);
	}
}
