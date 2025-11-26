package com.speakweb.model.service;

import java.util.List;

import com.speakweb.model.entity.Language;

public interface LanguageService {

	List<Language> getAllLanguages();
	Language getLanguageById(int id);
	Language save(Language language);
}
