package com.speakweb.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.speakweb.model.dto.UserLanguageDto;
import com.speakweb.model.entity.Language;
import com.speakweb.model.entity.UserEntity;
import com.speakweb.model.entity.UserLanguage;
import com.speakweb.model.service.GroupMemberService;
import com.speakweb.model.service.LanguageService;
import com.speakweb.model.service.UserLanguageService;
import com.speakweb.model.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/user-languages")
@Tag(name = "02. Mis Idiomas", description = "Añadir y eliminar idiomas que el usuario habla o quiere aprender")
public class UserLanguageController {

	@Autowired
	private UserLanguageService userLanguageService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private LanguageService languageService;
	
	@Autowired
	private GroupMemberService groupMemberService;
	
	// GET: Obtengo mi idiomas
	@GetMapping
	public ResponseEntity<List<UserLanguage>> getMyLanguages(Authentication authentication) {
	
		// se obtiene el email del token jwt
		String email = authentication.getName();
		// paso intermedio encontrar su id
		UserEntity user = userService.getUserByEmail(email);
		
		return ResponseEntity.ok(userLanguageService.getUserLanguage(user.getId()));
	}
	
	// GET: Obtengo idiomas de otro usuario por su id -- ver perfiles de otros
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<UserLanguage>> getUserLanguages(@PathVariable int userId) {
		return ResponseEntity.ok(userLanguageService.getUserLanguage(userId));
	}

	// POST: Añado un idioma nuevo!
	@PostMapping
	public ResponseEntity<?> addLanguage(@RequestBody UserLanguageDto dto, Authentication authentication) {
		
		String email = authentication.getName();
		UserEntity user = userService.getUserByEmail(email);
		
		// Evitar duplicados del mismo idioma con distintos niveles -lista idiomas q ya tiene el user
		List<UserLanguage> myCurrentLanguages = userLanguageService.getUserLanguage(user.getId());
		
		for (UserLanguage ul : myCurrentLanguages) {
			if (ul.getLanguage().getId() == dto.getLanguageId()) {
				return ResponseEntity.badRequest().body("Ya tienes ese idioma añadido!");
			}
		}
		
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
		
		// ajustes para actualizar la cond de experto de los grupos - tras new idioma
		groupMemberService.refreshUserExpertStatus(email);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(savedlang);
	}
	
	// Borrado de cualquier idioma q haya guardado antes
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUserLanguage(@PathVariable int id, Authentication authentication) {
		String email = authentication.getName();
		UserEntity user = userService.getUserByEmail(email);
		
		userLanguageService.deleteUserLanguage(id, user.getId());
		
		return ResponseEntity.ok("Idioma eliminado");
		
	}
}
