package com.speakweb.model.service;

import java.util.List;

import com.speakweb.model.entity.UserEntity;

public interface UserService {


	UserEntity getUserById(int id);
	UserEntity getUserByEmail(String email);
	boolean userExistsByEmail(String email);
	UserEntity save(UserEntity user);
	
	// métodos para el admin
	List<UserEntity> getAllUsers();
	void deleteUser(int id);
	
}
