package io.github.samuel_pinheiro_c_lopes.phone_book.contact.models;

import java.time.LocalDateTime;
import java.util.List;

import io.github.samuel_pinheiro_c_lopes.phone_book.authentication.models.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Contact {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String lastName;
	@Column(nullable = false, unique = true)
	private String email;
	@Column(nullable = false)
	private LocalDateTime creationDate;
	@OneToMany(mappedBy = "contact")
	private List<Telephone> telephones;
	@ManyToOne
	private User user;
	
	public Contact() {
		super();
	}
	
	public Contact(String name, String lastName, String email, LocalDateTime creationDate,  List<Telephone> telephones) {
		this();
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.creationDate = creationDate;
		this.telephones = telephones;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public LocalDateTime getCreationDate() {
		return creationDate;
	}
	
	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}
	
	public List<Telephone> getTelephones() {
		return telephones;
	}
	
	public void setTelephones(List<Telephone> telephones) {
		this.telephones = telephones;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
	
