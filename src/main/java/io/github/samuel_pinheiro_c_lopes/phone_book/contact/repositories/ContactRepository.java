package io.github.samuel_pinheiro_c_lopes.phone_book.contact.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.samuel_pinheiro_c_lopes.phone_book.authentication.models.User;
import io.github.samuel_pinheiro_c_lopes.phone_book.contact.models.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
	List<Contact> findByUser(User user);
}
