package com.speakweb.model.entity.pk;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class UserLanguageId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "user_id")
	private Integer userId;
	
	@Column( name = "language_id")
	private Integer languageId;
	
}
