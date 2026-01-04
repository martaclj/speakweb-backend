package com.speakweb.restcontroller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.speakweb.model.dto.UserDto;
import com.speakweb.model.entity.UserEntity;
import com.speakweb.model.mapper.UserMapper;
import com.speakweb.model.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/users")
@Tag(name = "08. Gestión de usuarios (Admin)", description = "Listar y eliminar usuarios")
public class UserController {

	@Autowired
	private UserService userService;
	
	// Listar usuarios
	@GetMapping
	public ResponseEntity<List<UserDto>> getAllUsers() {
		
		List<UserEntity> users = userService.getAllUsers();
		
		List<UserDto> dtos = new ArrayList<>();
		
		for (UserEntity user: users) {
			UserDto dto = UserMapper.toDto(user);
			dtos.add(dto);
		}
		
		return ResponseEntity.ok(dtos);
	}
	
	// Eliminar usuarios
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable int id) {
		try {
			userService.deleteUser(id);
			
			return ResponseEntity.ok("Usuario eliminado correctamente");
		
		} catch (Exception e) {
			e.printStackTrace();
			
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body("No se ha podido eliminar el usuario. Comprueba que no tenga eventos o grupos asociados.");
		}
	}
	
}
