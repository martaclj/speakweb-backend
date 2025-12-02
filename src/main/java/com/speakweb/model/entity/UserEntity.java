package com.speakweb.model.entity;

import java.io.Serializable;
import com.speakweb.model.entity.enums.Role;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class UserEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private int id;
	
	private String name;
	private String surname;
	private String email;
	private String password;
	
	@Enumerated(EnumType.STRING)
	private Role role = Role.USER;
}