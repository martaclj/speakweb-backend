package com.speakweb.model.entity.pk;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class GroupMemberId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "user_id")
	private Integer userId;
	
	@Column(name = "group_id")
	private Integer groupId;
	
}
