package com.speakweb.model.mapper;

import com.speakweb.model.dto.UserDto;
import com.speakweb.model.dto.UserRegisterDto;
import com.speakweb.model.entity.UserEntity;

public class UserMapper {
	
	public static UserEntity toEntity(UserRegisterDto dto) {
		if (dto == null) {
			return null;
		}
		
		UserEntity user = new UserEntity();
		user.setName(dto.getName());
		user.setSurname(dto.getSurname());
		user.setEmail(dto.getEmail());
		user.setPassword(dto.getPassword());
		
		return user;
	}
	
	public static UserDto toDto(UserEntity user) {
		if (user == null) return null;
		
		return new UserDto(
				user.getId(),
				user.getName(),
				user.getSurname(),
				user.getEmail(),
				user.getRole().name(),
				null // para el token que va vacío en la entidad
		);
	}

}
