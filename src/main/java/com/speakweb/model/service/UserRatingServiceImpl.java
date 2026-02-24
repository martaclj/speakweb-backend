package com.speakweb.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.speakweb.model.dto.RatingDto;
import com.speakweb.model.entity.Event;
import com.speakweb.model.entity.UserEntity;
import com.speakweb.model.entity.UserRating;
import com.speakweb.model.repository.EventRepository;
import com.speakweb.model.repository.UserRatingRepository;
import com.speakweb.model.repository.UserRepository;

@Service
public class UserRatingServiceImpl implements UserRatingService{
	@Autowired
	private UserRatingRepository userRatingRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EventRepository eventRepository;
	
	@Override
	public UserRating createRating(String reviewerEmail, RatingDto dto) {
		UserEntity reviewer = userRepository.findByEmail(reviewerEmail);
		UserEntity reviewedUser = userRepository.findById(dto.getReviewedUserId()).orElse(null);
		Event event = eventRepository.findById(dto.getEventId()).orElse(null);
		
		if (reviewer == null || reviewedUser == null || event == null) {
			throw new RuntimeException("Datos inválidos para la valoración.");
				
		}
		
		// evitar valorarse a uno mismo
		if (reviewer.getId() == reviewedUser.getId()) {
			throw new RuntimeException("No puedes valorarte a ti mismo.");
		}
		
		// evitar duplicadas (1 por evento y usuario)
		boolean alreadyRated = userRatingRepository.existsByReviewerAndReviewedUserAndEventId(reviewer, reviewedUser, event.getId());
		if (alreadyRated) {
			throw new RuntimeException("Ya has valorado a este usuario en este evento.");
		}
		
		UserRating rating = new UserRating();
		rating.setReviewer(reviewer);
		rating.setReviewedUser(reviewedUser);
		rating.setEvent(event);
		rating.setScore(dto.getScore());
		rating.setComments(dto.getComments());
		
		return userRatingRepository.save(rating);
		
	}
}
