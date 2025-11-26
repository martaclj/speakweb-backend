package com.speakweb.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.speakweb.model.entity.Language;
import com.speakweb.model.repository.LanguageRepository;

@Service
public class LanguageServiceImpl implements LanguageService {

	@Autowired
	private LanguageRepository languageRepository;
	
	@Override
	public List<Language> getAllLanguages() {

		return languageRepository.findAll();
	}

	@Override
	public Language getLanguageById(int id) {

		return languageRepository.findById(id).orElse(null);
	}

	@Override
	public Language save(Language language) {

		return languageRepository.save(language); // para añadir más idiomas en el futuro
	}

}
