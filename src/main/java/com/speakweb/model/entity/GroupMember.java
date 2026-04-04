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
@Table(name = "group_members")
@Data
public class GroupMember implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserEntity user;
	
	@ManyToOne
	@JoinColumn(name = "group_id")
	private BGroup group;
	
	/* true si usuario es nativo o nivel c1/c2 en algún idioma del grupo
	 *  los expertos SÍ pueden crear eventos
	 *  los usuarios normales NO pueden crear eventos */
	@Column(name = "is_expert")
	private boolean isExpert = false; // condición para crear eventos

}