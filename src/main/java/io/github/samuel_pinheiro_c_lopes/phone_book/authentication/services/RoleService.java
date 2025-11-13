package io.github.samuel_pinheiro_c_lopes.phone_book.authentication.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.samuel_pinheiro_c_lopes.phone_book.authentication.dtos.requests.RoleRequestDTO;
import io.github.samuel_pinheiro_c_lopes.phone_book.authentication.models.Role;
import io.github.samuel_pinheiro_c_lopes.phone_book.authentication.repositories.RoleRepository;


@Service
public class RoleService {
	private final RoleRepository roleRepository;
	
	@Autowired
	public RoleService(final RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}
	
	public void save(final RoleRequestDTO roleRequest) {
		this.roleRepository.save(new Role(roleRequest.authority()));
	}
}
