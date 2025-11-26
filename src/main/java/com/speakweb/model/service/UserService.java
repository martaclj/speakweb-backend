package com.speakweb.model.service;

import com.speakweb.model.entity.User;

public interface UserService {


	User getUserById(int id);
	User getUserByEmail(String email);
	boolean userExistsByEmail(String email);
	User save(User user);
	
}
