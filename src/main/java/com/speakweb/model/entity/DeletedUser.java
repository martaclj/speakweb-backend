package com.speakweb.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "deleted_users")
@Data
public class DeletedUser implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "log_id")
	private int logId;
	
	@Column(name = "original_user_id")
	private Integer originalUserId;
	
	private String name;
	private String surname;
	private String email;
	private String password;
	private String role;
	
	@Column(name = "avatar_url")
	private String avatarUrl;
	private String bio;
	
	@Column(name = "deleted_at", insertable = false, updatable = false)
	private LocalDateTime deletedAt;
	
}
