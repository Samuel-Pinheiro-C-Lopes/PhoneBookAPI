package io.github.samuel_pinheiro_c_lopes.phone_book.contact.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.samuel_pinheiro_c_lopes.phone_book.contact.models.Telephone;

@Repository
public interface TelephoneRepository extends JpaRepository<Telephone, Long> {

}
