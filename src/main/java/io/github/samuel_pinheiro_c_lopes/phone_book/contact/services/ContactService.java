package io.github.samuel_pinheiro_c_lopes.phone_book.contact.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import io.github.samuel_pinheiro_c_lopes.phone_book.authentication.models.User;
import io.github.samuel_pinheiro_c_lopes.phone_book.contact.dtos.requests.ContactRequestDTO;
import io.github.samuel_pinheiro_c_lopes.phone_book.contact.dtos.requests.TelephoneRequestDTO;
import io.github.samuel_pinheiro_c_lopes.phone_book.contact.dtos.responses.ContactResponseDTO;
import io.github.samuel_pinheiro_c_lopes.phone_book.contact.dtos.responses.TelephoneResponseDTO;
import io.github.samuel_pinheiro_c_lopes.phone_book.contact.models.Contact;
import io.github.samuel_pinheiro_c_lopes.phone_book.contact.models.Telephone;
import io.github.samuel_pinheiro_c_lopes.phone_book.contact.repositories.ContactRepository;
import io.github.samuel_pinheiro_c_lopes.phone_book.contact.repositories.TelephoneRepository;

@Service
public class ContactService {
	private final ContactRepository contactRepository;
	private final TelephoneRepository telephoneRepository;
	
	@Autowired
	public ContactService(
		final ContactRepository contactRepository,
		final TelephoneRepository telephoneRepository
	) {
		this.contactRepository = contactRepository;
		this.telephoneRepository = telephoneRepository;
	}
	
	public ContactResponseDTO findByIdForCurrentlyAuthenticatedUser(final Long id) {
		final Contact contact = this.contactRepository.getReferenceById(id);
		
		if (!contact.getUser().equals(this.getCurrentlyAuthenticatedUser()))
			throw new IllegalArgumentException();
		
		return new ContactResponseDTO(contact);
	}
	
	public List<ContactResponseDTO> findAllForCurrentlyAuthenticatedUser() {
		return this.findByUser(this.getCurrentlyAuthenticatedUser());
	}
	
	public ContactResponseDTO saveForCurrentlyAuthenticatedUser(final ContactRequestDTO contactDTO) {
		return this.saveForUser(this.getCurrentlyAuthenticatedUser(), contactDTO);
	}
	
	public List<ContactResponseDTO> saveForCurrentlyAuthenticatedUser(final List<ContactRequestDTO> contactRequests) {
		return this.saveForUser(this.getCurrentlyAuthenticatedUser(), contactRequests);
	}
	
	public TelephoneResponseDTO updateTelephoneOfContactForCurrentlyAuthenticatedUser(
		final Long telephoneId, 
		final Long contactId, 
		final TelephoneRequestDTO telephoneRequest
	) {
		final Contact savedContact = this.contactRepository.getReferenceById(contactId);
		
		if (!savedContact.getUser().getId().equals(this.getCurrentlyAuthenticatedUser().getId()))
			throw new IllegalArgumentException();
		
		Optional<Telephone> telephoneOpt = savedContact.getTelephones()
															.stream()
															.filter(t -> t.getId().equals(telephoneId))
															.findFirst();
		
		if (telephoneOpt.isPresent() == Boolean.FALSE)
			throw new IllegalArgumentException();
		
		return this.updateTelephone(telephoneOpt.get(), telephoneRequest);
	}
	
	public void deleteTelephoneOfContactForCurrentlyAuthenticatedUser(final Long telephoneId, final Long contactId) {
		final Contact savedContact = this.contactRepository.getReferenceById(contactId);
		
		if (!savedContact.getUser().equals(this.getCurrentlyAuthenticatedUser()))
			throw new IllegalArgumentException();
		
		Optional<Telephone> telephoneOpt = savedContact.getTelephones()
															.stream()
															.filter(t -> t.getId().equals(telephoneId))
															.findFirst();
		
		if (telephoneOpt.isPresent() == Boolean.FALSE)
			throw new IllegalArgumentException();
		
		savedContact.getTelephones().remove(telephoneOpt.get());
		this.contactRepository.save(savedContact);
		this.telephoneRepository.delete(telephoneOpt.get());
	}
	
	public ContactResponseDTO updateContactForCurrentlyAutheticatedUser(final Long id, final ContactRequestDTO contactDTO) {
		final Contact toBeUpdatedContact = this.contactRepository.getReferenceById(id);
		
		if (!toBeUpdatedContact.getUser().equals(this.getCurrentlyAuthenticatedUser()))
			throw new IllegalArgumentException();
		
		toBeUpdatedContact.setEmail(contactDTO.email());
		toBeUpdatedContact.setName(contactDTO.name());
		toBeUpdatedContact.setLastName(contactDTO.lastName());
		
		for (final Telephone telephone : toBeUpdatedContact.getTelephones())
			this.telephoneRepository.delete(telephone);
		
		toBeUpdatedContact.getTelephones().clear();
		for (final TelephoneRequestDTO telephoneRequest : contactDTO.telephones())
			toBeUpdatedContact.getTelephones().add(this.telephoneRepository.save(telephoneRequest.toTelephone()));

		return new ContactResponseDTO(this.contactRepository.save(toBeUpdatedContact));
	}
	
	public List<TelephoneResponseDTO> addTelephonesToContactForCurrentlyAuthenticatedUser(final Long id, final List<TelephoneRequestDTO> telephones) {
		final Contact toBeUpdatedContact = this.contactRepository.getReferenceById(id);

		if (!toBeUpdatedContact.getUser().equals(this.getCurrentlyAuthenticatedUser()))
			throw new IllegalArgumentException();
		
		final List<Telephone> telephonesAdded = new ArrayList<Telephone>();
		
		for (final TelephoneRequestDTO telephoneRequest : telephones)
			telephonesAdded.add(this.telephoneRepository.save(telephoneRequest.toTelephone()));
		
		toBeUpdatedContact.getTelephones().addAll(telephonesAdded);
		
		this.contactRepository.save(toBeUpdatedContact);
		
		return telephonesAdded
				.stream()
				.map(t -> new TelephoneResponseDTO(t))
				.toList();
	}
	
	public void deleteContactForCurrentlyAuthenticatedUser(final Long id) {
		final Contact toBeDeletedContact = this.contactRepository.getReferenceById(id);
		
		if (!toBeDeletedContact.getUser().equals(this.getCurrentlyAuthenticatedUser()))
			throw new IllegalArgumentException();
		
		for (final Telephone telephone : toBeDeletedContact.getTelephones())
			this.telephoneRepository.delete(telephone);
		
		this.contactRepository.delete(toBeDeletedContact);
	}
	
	private List<ContactResponseDTO> saveForUser(final User user, final List<ContactRequestDTO> contactRequests) {
		return contactRequests
				.stream()
				.map(c -> saveForUser(user, c))
				.toList();
	}
	
	private ContactResponseDTO saveForUser(final User user, final ContactRequestDTO contactDTO) {
		final Contact contact = contactDTO.toContact(LocalDateTime.now());
		
		contact.setUser(user);
		for (final Telephone telephone : contact.getTelephones()) 
			contact.getTelephones().add(this.telephoneRepository.save(telephone));
		
		return new ContactResponseDTO(this.contactRepository.save(contact));
	}
	
	private List<ContactResponseDTO> findByUser(final User user) {
		return this.contactRepository.findByUser(user)
				.stream()
				.map(c -> new ContactResponseDTO(c))
				.toList();
	}
	
	private TelephoneResponseDTO updateTelephone(final Telephone toBeUpdated, final TelephoneRequestDTO telephoneRequest) {
		toBeUpdated.setNumber(telephoneRequest.number());
		toBeUpdated.setPrincipal(telephoneRequest.principal());
		toBeUpdated.setCategory(telephoneRequest.category());
		
		return new TelephoneResponseDTO(this.telephoneRepository.save(toBeUpdated));
	}
	
	private User getCurrentlyAuthenticatedUser() {
		final Object possibleCurrentlyAuthenticatedUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if (possibleCurrentlyAuthenticatedUser == null)
			throw new IllegalArgumentException();
		
		if (!(possibleCurrentlyAuthenticatedUser instanceof User))
			throw new IllegalArgumentException();
		
		return (User) possibleCurrentlyAuthenticatedUser;
			
	}
}
