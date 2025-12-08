package com.speakweb.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.speakweb.model.dto.LoginRequest;
import com.speakweb.model.dto.UserDto;
import com.speakweb.model.dto.UserRegisterDto;
import com.speakweb.model.entity.UserEntity;
import com.speakweb.model.mapper.UserMapper;
import com.speakweb.model.service.UserService;
import com.speakweb.security.JwtUtil;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "01. Autenticación", description = "Endpoints de Registro y Login. Copiar el Bearer Token de la respuesta de Login para ponerlo en Authorize")
public class AuthController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder; // encriptada en el registro
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody UserRegisterDto dto) {
		
		if (userService.userExistsByEmail(dto.getEmail())) {
			return ResponseEntity.badRequest().body("El email ya está registrado");
		}
		
		UserEntity user = UserMapper.toEntity(dto);
		//Encriptación
		String passCifrada = passwordEncoder.encode(dto.getPassword());
		user.setPassword(passCifrada);
		
		userService.save(user);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginDto) {
		
		UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(
				loginDto.getEmail(), loginDto.getPassword());
	
		try {
			Authentication authentication = authenticationManager.authenticate(loginToken);
			
			String jwt = jwtUtil.create(authentication.getName());
			
			UserEntity userEntity = userService.getUserByEmail(loginDto.getEmail());
			
			UserDto userDto = UserMapper.toDto(userEntity);
			
			userDto.setToken(jwt);
			
			return ResponseEntity.ok(userDto);

		} catch (AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

	}
}
