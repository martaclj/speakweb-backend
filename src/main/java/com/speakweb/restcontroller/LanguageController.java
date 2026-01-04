package com.speakweb.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.speakweb.model.entity.Language;
import com.speakweb.model.service.LanguageService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/languages")
@Tag(name = "07. Gestión idiomas (ADMIN)", description = "Admin crea o elimina idiomas")
public class LanguageController {
	
	@Autowired
	private LanguageService languageService;
	
	@GetMapping
	public ResponseEntity<List<Language>> getAllLanguages() {
		
		return ResponseEntity.ok(languageService.getAllLanguages());
	}
	
	@PostMapping
	public ResponseEntity<?> createLanguage(@RequestBody Language language) {
		
		try {
		
			return ResponseEntity.status(HttpStatus.CREATED).body(languageService.save(language));
		
		} catch (Exception e) {
			
			return ResponseEntity.badRequest().body("Error: Probablemente el idioma ya existe.");
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteLanguage(@PathVariable int id) {
		try {
			languageService.deleteLanguage(id);
			
			return ResponseEntity.ok("Idioma eliminado de la lista");
		
		} catch (Exception e) {
			e.printStackTrace();
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("No se puede eliminar el idioma. Hay usuarios y/o grupos asociados.");
		}
	}

}
