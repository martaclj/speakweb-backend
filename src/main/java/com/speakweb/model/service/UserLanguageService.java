package com.speakweb.model.service;

import java.util.List;

import com.speakweb.model.entity.UserLanguage;

public interface UserLanguageService {

	List<UserLanguage> getUserLanguage(int userId);
	UserLanguage save(UserLanguage userLanguage);
}
