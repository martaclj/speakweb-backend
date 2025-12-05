package com.speakweb.model.dto;

import java.time.LocalDateTime;

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
	private LocalDateTime startTime;
	private String location;
	private String externalLink;
	private int groupId;
	
}
