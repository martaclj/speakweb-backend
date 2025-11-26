package com.speakweb.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.speakweb.model.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	User findByEmail(String email); // método para el login
	boolean existsByEmail(String email); // no dos usuarios con mismo email
	
}
