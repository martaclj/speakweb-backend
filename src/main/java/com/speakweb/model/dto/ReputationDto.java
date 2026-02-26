package com.speakweb.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// dto para mostrar el resumen de la reputación
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReputationDto {
	private String score;
	private int count;
}
