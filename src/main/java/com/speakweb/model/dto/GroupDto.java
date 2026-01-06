package com.speakweb.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupDto {

	private String name;
	private String description;
	private String imageUrl;
	private int language1Id;
	private int language2Id;
	
}
