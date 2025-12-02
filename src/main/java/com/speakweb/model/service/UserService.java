package com.speakweb.model.service;

import com.speakweb.model.entity.UserEntity;

public interface UserService {


	UserEntity getUserById(int id);
	UserEntity getUserByEmail(String email);
	boolean userExistsByEmail(String email);
	UserEntity save(UserEntity user);
	
}
