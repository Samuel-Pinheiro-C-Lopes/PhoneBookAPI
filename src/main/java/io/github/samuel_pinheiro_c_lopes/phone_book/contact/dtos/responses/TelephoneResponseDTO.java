package io.github.samuel_pinheiro_c_lopes.phone_book.contact.dtos.responses;

import io.github.samuel_pinheiro_c_lopes.phone_book.contact.enums.TelephoneCategory;
import io.github.samuel_pinheiro_c_lopes.phone_book.contact.models.Telephone;

public record TelephoneResponseDTO(Long id, String number, TelephoneCategory category, Boolean principal) {
	public TelephoneResponseDTO(final Telephone telephone) {
		this(telephone.getId(), telephone.getNumber(), telephone.getCategory(), telephone.getPrincipal());
	}
}
