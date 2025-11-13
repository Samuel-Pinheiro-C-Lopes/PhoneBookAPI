package io.github.samuel_pinheiro_c_lopes.phone_book.contact.dtos.requests;

import io.github.samuel_pinheiro_c_lopes.phone_book.contact.enums.TelephoneCategory;
import io.github.samuel_pinheiro_c_lopes.phone_book.contact.models.Telephone;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TelephoneRequestDTO(
		@NotBlank
		String number,
		@NotNull
		TelephoneCategory category,
		@NotNull
		Boolean principal) {
	public Telephone toTelephone() {
		return new Telephone(this.number(), this.category(), this.principal());
	}
}
