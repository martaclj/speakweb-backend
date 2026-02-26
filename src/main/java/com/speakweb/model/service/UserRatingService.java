package com.speakweb.model.service;

import java.util.List;

import com.speakweb.model.dto.RatingDto;
import com.speakweb.model.dto.ReputationDto;
import com.speakweb.model.entity.UserRating;

public interface UserRatingService {
	// para valorar
	UserRating createRating(String reviewerEmail, RatingDto dto);

	// para obtener el dto 
	ReputationDto getUserReputation(int userId);
	
	// para el admin (para que vea todas las valoraciones)
	List<UserRating> getUserRatingsDetailed(int userId);

}
