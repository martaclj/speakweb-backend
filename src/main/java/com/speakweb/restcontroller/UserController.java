package com.speakweb.restcontroller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	// Obtener mi perfil
	@GetMapping("/profile")
	public ResponseEntity<UserDto> getprofile() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		
		UserEntity user = userService.getUserByEmail(email);
		
		if (user == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(UserMapper.toDto(user));
	}
	
	// Actualizar mi perfil
	@PutMapping("/profile")
	public ResponseEntity<UserDto> updatedProfile(@RequestBody UserDto userDto) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		UserEntity user = userService.getUserByEmail(email);
		
		if (user == null) {
			return ResponseEntity.notFound().build();
		}
		// Actualización de campos:
		user.setName(userDto.getName());
		user.setSurname(userDto.getSurname());
		user.setBio(userDto.getBio());
		user.setAvatarUrl(userDto.getAvatarUrl());
		
		userService.save(user);
		
		return ResponseEntity.ok(UserMapper.toDto(user));
		
	}
	
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
	
	// visitar el perfil de otro usuario
	@GetMapping("/{id}")
	public ResponseEntity<UserDto> getUserById(@PathVariable int id) {
		UserEntity user = userService.getUserById(id);
		if (user == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(UserMapper.toDto(user));
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
