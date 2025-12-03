package com.speakweb.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.speakweb.model.dto.UserLanguageDto;
import com.speakweb.model.entity.Language;
import com.speakweb.model.entity.UserEntity;
import com.speakweb.model.entity.UserLanguage;
import com.speakweb.model.service.LanguageService;
import com.speakweb.model.service.UserLanguageService;
import com.speakweb.model.service.UserService;

@RestController
@RequestMapping("/api/user-languages")
public class UserLanguageController {

	@Autowired
	private UserLanguageService userLanguageService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private LanguageService languageService;
	
	// GET: Obtengo mi idiomas
	@GetMapping
	public ResponseEntity<List<UserLanguage>> getMyLanguages(Authentication authentication) {
	
		// se obtiene el email del token jwt
		String email = authentication.getName();
		// paso intermedio encontrar su id
		UserEntity user = userService.getUserByEmail(email);
		
		return ResponseEntity.ok(userLanguageService.getUserLanguage(user.getId()));
	}

	// POST: Añado un idioma nuevo!
	@PostMapping
	public ResponseEntity<?> addLanguage(@RequestBody UserLanguageDto dto, Authentication authentication) {
		
		String email = authentication.getName();
		UserEntity user = userService.getUserByEmail(email);
		
		Language language = languageService.getLanguageById(dto.getLanguageId());
		
		if (language== null) {
			return ResponseEntity.badRequest().body("Idioma no encontrado");
		}
		
		// aquí transfiero datos recibidos (dto) a Entity
		UserLanguage newRegister = new UserLanguage();
		newRegister.setUser(user);
		newRegister.setLanguage(language);
		newRegister.setLevel(dto.getLevel());
		newRegister.setType(dto.getType());
		
		// Guardo idioma
		UserLanguage savedlang = userLanguageService.save(newRegister);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(savedlang);
	}
}
