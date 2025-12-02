package com.speakweb.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.speakweb.model.entity.UserEntity;
import com.speakweb.model.entity.UserLanguage;
import com.speakweb.model.repository.UserLanguageRepository;

@Service
public class UserLanguageServiceImpl implements UserLanguageService {

	@Autowired
	private UserLanguageRepository userLanguageRepository;
	
	@Autowired
	private UserService userService;	
	
	@Override
	public List<UserLanguage> getUserLanguage(int userId) {
		UserEntity user = userService.getUserById(userId);
		return userLanguageRepository.findByUser(user);
	}

	@Override
	public UserLanguage save(UserLanguage userLanguage) {
		return userLanguageRepository.save(userLanguage);
	}

}
