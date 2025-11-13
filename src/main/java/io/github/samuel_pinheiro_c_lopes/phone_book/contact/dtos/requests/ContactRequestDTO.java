package io.github.samuel_pinheiro_c_lopes.phone_book.contact.dtos.requests;

import java.time.LocalDateTime;
import java.util.List;

import io.github.samuel_pinheiro_c_lopes.phone_book.contact.models.Contact;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ContactRequestDTO(
		@NotBlank
		String name,
		@NotBlank
		String lastName,
		@Email
		String email,
		@NotNull
		List<TelephoneRequestDTO> telephones
	) {
	public Contact toContact(final LocalDateTime creationDate) {
		return new Contact(
				name, 
				lastName, 
				email, 
				creationDate,
				telephones
					.stream()
					.map(t -> t.toTelephone())
					.toList()
		);
	}
}
