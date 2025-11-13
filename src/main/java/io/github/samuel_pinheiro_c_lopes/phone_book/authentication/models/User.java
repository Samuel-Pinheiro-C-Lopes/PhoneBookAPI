package io.github.samuel_pinheiro_c_lopes.phone_book.authentication.models;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import io.github.samuel_pinheiro_c_lopes.phone_book.contact.models.Contact;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class User implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, unique = true)
	private String username;
	@Column(nullable = false)
	private String password;
	@ManyToMany
	private List<Role> roles;
	@OneToMany(mappedBy = "user")
	private List<Contact> contacts;

	public User() {
		super();
	}
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof User)
			return this.getId().equals(((User) obj).getId());
		
		return super.equals(obj);
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Override
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Override
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public List<Role> getRoles() {
		return roles;
	}
	
	@Override
	public List<Role> getAuthorities() {
		return getRoles();
	}
	
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}
}
