package io.github.samuel_pinheiro_c_lopes.phone_book.contact.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.samuel_pinheiro_c_lopes.phone_book.contact.dtos.requests.ContactRequestDTO;
import io.github.samuel_pinheiro_c_lopes.phone_book.contact.dtos.requests.TelephoneRequestDTO;
import io.github.samuel_pinheiro_c_lopes.phone_book.contact.dtos.responses.ContactResponseDTO;
import io.github.samuel_pinheiro_c_lopes.phone_book.contact.dtos.responses.TelephoneResponseDTO;
import io.github.samuel_pinheiro_c_lopes.phone_book.contact.services.ContactService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/contact")
public class ContactController {
	private final ContactService contactService;
	
	@Autowired
	public ContactController(final ContactService contactService) {
		this.contactService = contactService;
	}
	
	@PostMapping("/contacts")
	public ResponseEntity<List<ContactResponseDTO>> saveContacts(
		@RequestBody final List<ContactRequestDTO> contactRequests
	) {
		return ResponseEntity.ok(
			this.contactService.saveForCurrentlyAuthenticatedUser(contactRequests)
		);
	}
	
	@GetMapping("/contacts")
	public ResponseEntity<List<ContactResponseDTO>> findContacts() {
		return ResponseEntity.ok(
			this.contactService.findAllForCurrentlyAuthenticatedUser()
		);
	}
	
	@GetMapping("/contacts/{contactId}")
	public ResponseEntity<ContactResponseDTO> findContact(
		@PathVariable final Long contactId
	) {
		return ResponseEntity.ok(
			this.contactService.findByIdForCurrentlyAuthenticatedUser(contactId)
		);
	}
	
	@PutMapping("/contacts/{contactId}")
	public ResponseEntity<ContactResponseDTO> updateContact(
		@PathVariable final Long contactId, 
		@RequestBody final ContactRequestDTO contactRequest
	) {
		return ResponseEntity.ok(
			this.contactService.updateContactForCurrentlyAutheticatedUser(contactId, contactRequest)
		);
	}
	
	@DeleteMapping("/contacts/{contactId}")
	public ResponseEntity<Void> deleteContact(
		@PathVariable final Long contactId
	) {
		this.contactService.deleteContactForCurrentlyAuthenticatedUser(contactId);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping("/contact/{contactId}/telephones")
	public ResponseEntity<List<TelephoneResponseDTO>> saveTelephones(
		@PathVariable final Long contactId, 
		@RequestBody final List<TelephoneRequestDTO> telephoneRequests
	) {
		return ResponseEntity.ok(
			this.contactService.addTelephonesToContactForCurrentlyAuthenticatedUser(contactId, telephoneRequests)
		);
	}
	
	@PutMapping("/contacts/{contactId}/telephones/{telephoneId}")
	public ResponseEntity<TelephoneResponseDTO> updateTelephone(
		@PathVariable final Long contactId,
		@PathVariable final Long telephoneId,
		@RequestBody final TelephoneRequestDTO telephoneRequest
	) {
		return ResponseEntity.ok(
			this.contactService.updateTelephoneOfContactForCurrentlyAuthenticatedUser(telephoneId, contactId, telephoneRequest)
		);
	}
	
	@DeleteMapping("/contacts/{contactId}/telephones/{telephoneId}")
	public ResponseEntity<Void> deleteTelephone(
		@PathVariable final Long contactId,
		@PathVariable final Long telephoneId
	) {
		this.contactService.deleteTelephoneOfContactForCurrentlyAuthenticatedUser(telephoneId, contactId);
		return ResponseEntity.noContent().build();
	}
}
