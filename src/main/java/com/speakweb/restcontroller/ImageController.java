package com.speakweb.restcontroller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.speakweb.model.service.LocalImageStorageService;

// https://github.com/philipplackner/SpringBootFileUploadDownload

@RestController
@RequestMapping("/api/images")
@CrossOrigin(origins = "*")
public class ImageController {
	private final LocalImageStorageService storageService;
	// Inyección de dependencias por constructor
	public ImageController(LocalImageStorageService storageService) {
		this.storageService = storageService;
	}
	
	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
		if (file.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Archivo está vacío");
		}
		try {
			String storedPath = storageService.storeFile(file);
			
			Map<String, String> response = new HashMap<>();
			response.put("imageUrl", storedPath);
			
			return ResponseEntity.ok(response);
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno al subir archivo");
		}
	}
	
}
