package io.github.samuel_pinheiro_c_lopes.phone_book.contact.dtos.responses;

import java.time.LocalDateTime;
import java.util.List;

import io.github.samuel_pinheiro_c_lopes.phone_book.contact.models.Contact;

public record ContactResponseDTO(
		Long id, 
		String name, 
		String lastName, 
		String email, 
		LocalDateTime creationDate,
		List<TelephoneResponseDTO> telephones
	) {
	public ContactResponseDTO(final Contact contact) {
		this(
			contact.getId(), 
			contact.getName(), 
			contact.getLastName(), 
			contact.getEmail(), 
			contact.getCreationDate(),
			contact.getTelephones()
				.stream()
				.map(t -> new TelephoneResponseDTO(t))
				.toList());
	}
}
