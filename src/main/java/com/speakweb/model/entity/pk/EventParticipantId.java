package com.speakweb.model.entity.pk;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class EventParticipantId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "event_id")
	private Integer eventId;
	
	@Column(name = "user_id")
	private Integer userId;
	
}
