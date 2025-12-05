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

		// Intentamos que los idiomas sean como los del estándar internacional
		// ocupan dos letras
		String code = language.getCode().toUpperCase();
		language.setCode(code);
		
		if(code.length() != 2) {
			throw new RuntimeException("El código del idioma debe tener 2 letras (Ej: ES, EN)");
		}
		
		// Evitamos crear varias versiones de un mismo idioma (
		// español --> es o sp, o inglés --> en in)
		if (languageRepository.existsByCode(code)) {
			throw new RuntimeException("El idioma con código " + code + " ya existe.");
		}
		
		return languageRepository.save(language); // para añadir más idiomas en el futuro
	}

}
