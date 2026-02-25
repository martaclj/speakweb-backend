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
@Table(name = "user_reports")
@Data
public class UserReport implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "report_id")
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "reporter_id")
	private UserEntity reporter;
	
	@ManyToOne
	@JoinColumn(name = "reported_user_id")
	private UserEntity reportedUser;
	
	private String reason;
	
	@Column(name = "created_at", insertable = false, updatable = false)
	private LocalDateTime createdAt;

}
