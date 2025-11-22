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
	
	private String title;
	private String description;
	
	@Column(name = "start_time")
	private LocalDateTime startTime;
	
	@Column(name = "external_link")
	private String externalLink;
	
	@Column(name ="location")
	private String location;
}
