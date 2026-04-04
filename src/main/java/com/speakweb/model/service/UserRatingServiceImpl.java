package com.speakweb.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.speakweb.model.dto.RatingDto;
import com.speakweb.model.dto.ReputationDto;
import com.speakweb.model.entity.Event;
import com.speakweb.model.entity.UserEntity;
import com.speakweb.model.entity.UserRating;
import com.speakweb.model.repository.EventRepository;
import com.speakweb.model.repository.UserRatingRepository;
import com.speakweb.model.repository.UserReportRepository;
import com.speakweb.model.repository.UserRepository;

@Service
public class UserRatingServiceImpl implements UserRatingService{
	@Autowired
	private UserRatingRepository userRatingRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private UserReportRepository userReportRepository;
	
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
		// para que se guarde con la fecha actual
		// rating.setCreatedAt(LocalDateTime.now());
		
		return userRatingRepository.save(rating);
		
	}
	
	@Override
	public ReputationDto getUserReputation(int userId) {
		UserEntity user = userRepository.findById(userId).orElse(null);
		
		// usuario no existe -> 5.0 y 0 denuncias
		if (user == null) {
			return new ReputationDto("5.0", 0);
		}
		
		// cálculo nota media estrellas
		List<UserRating> ratings = userRatingRepository.findByReviewedUser(user);
		String formattedScore = "5.0";
		
		if (!ratings.isEmpty()) {
			double sum = 0;
			for (UserRating r : ratings) {
				sum += r.getScore();
			}
			double average = sum / ratings.size();
			
			/* resolver problema con los decimales:
			 * para sacar un decimal:
			 * average = 4.3333
			 * average * 10.0 = 43.333
			 * Math.round(43.333) = 43
			 * 43 / 10.0 = 4.3
			 */
			
			double roundedAverage = Math.round(average * 10.0) / 10.0;
			formattedScore = String.valueOf(roundedAverage); 
			// porque en ReputationDto tengo private String score;
		}
		// recuento de denuncias 
		int reportsCount = userReportRepository.countByReportedUser(user);
		
		// devuelvo un dto con estos datos concretos
		return new ReputationDto(formattedScore, reportsCount);
	}

	@Override
	public List<UserRating> getUserRatingsDetailed(int userId) {
		UserEntity user = userRepository.findById(userId).orElse(null);
		
		if (user == null) {
			return List.of(); // no usuario, lista vacía
		}
		
		// devuelvo todas las valoraciones
		return userRatingRepository.findByReviewedUser(user);
	}
}
