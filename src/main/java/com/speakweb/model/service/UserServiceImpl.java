package com.speakweb.model.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User; 
// Por este User de security cambié User a UserEntity en las entidades
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.speakweb.model.entity.UserEntity;
import com.speakweb.model.entity.enums.Role;
import com.speakweb.model.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserEntity getUserById(int id) {
		return userRepository.findById(id).orElse(null) ;
	}

	@Override
	public UserEntity getUserByEmail(String email) {

		return userRepository.findByEmail(email);
	}

	@Override
	public boolean userExistsByEmail(String email) {

		return userRepository.existsByEmail(email);
	}

	@Override
	public UserEntity save(UserEntity user) {

		if (user.getRole() == null) {
			user.setRole(Role.USER);
		}// Asignación de rol por defecto si no tiene
		return userRepository.save(user);
	}

	
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity user = userRepository.findByEmail(email);
		
		if (user == null) {
			throw new UsernameNotFoundException("User " + email + " no encontrado");
		}
		List<GrantedAuthority> authorities = this.grantedAuthorities(user.getRole());
		
		return User.builder()
				.username(user.getEmail())
				.password(user.getPassword())
				.authorities(authorities)
				.build();
	}
	
	private List<GrantedAuthority> grantedAuthorities(Role role) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));
		
		return authorities;
	}
	
}
