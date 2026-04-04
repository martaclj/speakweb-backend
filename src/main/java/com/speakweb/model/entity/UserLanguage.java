package com.speakweb.model.entity;

import java.io.Serializable;

import com.speakweb.model.entity.enums.Level;
import com.speakweb.model.entity.enums.UserLangType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "user_languages")
@Data
public class UserLanguage  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/* diseño original con PK compuesta según el modelo E/R
	 * 
	 * @EmbeddedId 
	 * private UserLanguageId id; // PK compuesta: user_id y language_id
	 * 
	 * cambiado a una sola pk por tabla
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserEntity user;
	
	@ManyToOne(fetch = FetchType.EAGER) // carga idioma Language siempre en misma consulta
	@JoinColumn(name = "language_id")
	private Language language;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "level")
	private Level level = Level.A1; // nivel por defecto
	
	@Enumerated(EnumType.STRING)
	@Column(name = "type")
	private UserLangType type = UserLangType.LEARNER; // Tipo de usuario, nativo o no nativo (learner)
	
	
}
