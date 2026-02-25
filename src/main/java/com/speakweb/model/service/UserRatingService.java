package com.speakweb.model.service;

import com.speakweb.model.dto.RatingDto;
import com.speakweb.model.dto.ReputationDto;
import com.speakweb.model.entity.UserRating;

public interface UserRatingService {
	UserRating createRating(String reviewerEmail, RatingDto dto);

	ReputationDto getUserReputation(int userId);

}
