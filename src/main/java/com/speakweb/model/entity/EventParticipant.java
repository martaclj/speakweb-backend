package com.speakweb.model.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "event_participants")
@Data
public class EventParticipant {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; // Id único temporal para evitar la PK compuesta
	
	@ManyToOne
	@JoinColumn(name = "event_id")
	private Event event;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
}