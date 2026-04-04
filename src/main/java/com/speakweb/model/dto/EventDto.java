package com.speakweb.model.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventDto {

	private String title;
	private String description;
	
	// formato de fecha para Angular: Ej: 2026-04-04T16:00
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime startTime;
	
	private String location; // solo si presential
	private String externalLink; // solo si online
	private int groupId;
	private String imageUrl;
	private String type; // ONLINE o PRESENTIAL
	
}
