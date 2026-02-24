package com.speakweb.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "user_ratings")
@Data
public class UserRating implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rating_id")
	private int id;
	
	// usuario que escribe la valoración
	@ManyToOne
	@JoinColumn(name = "reviewer_id")
	private UserEntity reviewer;
	
	// usuario valorado
	@ManyToOne
	@JoinColumn(name = "reviewer_user_id")
	private UserEntity reviewedUser;
	
	// evento relacionado
	@ManyToOne
	@JoinColumn(name = "event_id")
	private Event event;
	
	private int score;
	
	private String comments;
	
	@Column(name = "created_at")
	private LocalDateTime createdAt;

}
