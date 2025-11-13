package io.github.samuel_pinheiro_c_lopes.phone_book.contact.models;

import io.github.samuel_pinheiro_c_lopes.phone_book.contact.enums.TelephoneCategory;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Telephone {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String number;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private TelephoneCategory category;
	@Column(nullable = false)
	private Boolean principal;
	@ManyToOne
	private Contact contact;
	
	public Telephone() {
		super();
	}
	
	public Telephone(String number, TelephoneCategory category, Boolean principal) {
		this();
		this.number = number;
		this.category = category;
		this.principal = principal;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNumber() {
		return number;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}
	
	public TelephoneCategory getCategory() {
		return category;
	}
	
	public void setCategory(TelephoneCategory category) {
		this.category = category;
	}
	
	public Boolean getPrincipal() {
		return principal;
	}
	
	public void setPrincipal(Boolean principal) {
		this.principal = principal;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}
	
}
