package com.speakweb.model.entity;


import com.speakweb.model.entity.pk.EventParticipantId;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "event_participants")
@Data
public class EventParticipant {

	@EmbeddedId  
	private EventParticipantId id; // Como la pk es compuesta, la traemos desde una clase que se compone de los dos atributos
	
	@ManyToOne
	@JoinColumn(name = "event_id")
	private Event event;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
}