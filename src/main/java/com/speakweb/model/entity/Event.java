package com.speakweb.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.speakweb.model.entity.enums.EventType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "events")
@Data
public class Event implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "event_id")
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "group_id")
	private BGroup group;
	
	@ManyToOne
	@JoinColumn(name = "creator_id")
	private UserEntity creator; // el que creó el evento (experto o admin)
	
	@Enumerated(EnumType.STRING) // guarda ONLINE o PRESENTIAL como txt en la bd.
	private EventType type; // ONLINE o PRESENTIAL
	
	private String title;
	private String description;
	
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm") // formato para Angular
	@Column(name = "start_time")
	private LocalDateTime startTime;
	
	@Column(name = "external_link")
	private String externalLink; // solo si online: enlace a google meet o zoom, etc
	
	@Column(name ="location")
	private String location; // solo si presential: ubicación del evento
	@Column(name ="image_url")
	private String imageUrl;
}
