package io.github.samuel_pinheiro_c_lopes.phone_book.contact.services;

import org.springframework.beans.factory.annotation.Autowired;

import io.github.samuel_pinheiro_c_lopes.phone_book.contact.repositories.TelephoneRepository;

public class TelephoneService {
	private final TelephoneRepository telephoneRepository;
	
	@Autowired
	public TelephoneService(final TelephoneRepository telephoneRepository) {
		this.telephoneRepository = telephoneRepository;
	}
}
