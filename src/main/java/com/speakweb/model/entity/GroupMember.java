package com.speakweb.model.entity;

import java.io.Serializable;

import com.speakweb.model.entity.pk.GroupMemberId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "group_members")
@Data
public class GroupMember implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId 
	private GroupMemberId id; // Pk compuesta por user_id y group_id
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "group_id")
	private BGroup group;
	
	@Column(name = "is_expert")
	private boolean isExpert = false; // condición para crear eventos

}