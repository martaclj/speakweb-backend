package com.speakweb.model.entity;

import java.io.Serializable;

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
@Table(name = "b_groups")
@Data
public class BGroup implements Serializable { // Bilingual group

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "group_id")
	private int id;
	
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "language1_id", referencedColumnName = "language_id")
	private Language language1;
	
	@ManyToOne
	@JoinColumn(name = "language2_id", referencedColumnName = "language_id")
	private Language language2;
	
	
}
