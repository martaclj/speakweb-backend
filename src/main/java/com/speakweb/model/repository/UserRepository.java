package com.speakweb.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.speakweb.model.entity.UserEntity;
import org.springframework.stereotype.Repository;

import com.speakweb.model.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

	UserEntity findByEmail(String email); // método para el login
	boolean existsByEmail(String email); // no dos usuarios con mismo email
	
}
