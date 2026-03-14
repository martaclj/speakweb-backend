package com.speakweb.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.speakweb.model.entity.DeletedUser;
import com.speakweb.model.repository.DeletedUserRepository;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/deleted-users")
@Tag(name = "09. Usuarios eliminados", description = "Historial de usuarios borrados.")
public class DeletedUserController {
	
	@Autowired
	private DeletedUserRepository deletedUserRepository;
	
	// Listar todos los eliminados para el panel del Admin
	@GetMapping
	public ResponseEntity<List<DeletedUser>> getAllDeletedUsers() {
		return ResponseEntity.ok(deletedUserRepository.findAll());
	}

}
