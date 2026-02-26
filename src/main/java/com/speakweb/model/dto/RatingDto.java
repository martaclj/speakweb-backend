package com.speakweb.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//Dto para puntuar
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RatingDto {
	private int reviewedUserId;
	private int eventId;
	private int score;
	private String comments;

}
