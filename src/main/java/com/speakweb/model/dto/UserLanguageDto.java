package com.speakweb.model.dto;

import com.speakweb.model.entity.enums.Level;
import com.speakweb.model.entity.enums.UserLangType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLanguageDto {

	private int languageId;
	private Level level;
	private UserLangType type;
}
