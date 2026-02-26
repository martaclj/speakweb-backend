package com.speakweb.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.speakweb.model.dto.RatingDto;
import com.speakweb.model.dto.ReputationDto;
import com.speakweb.model.entity.UserRating;
import com.speakweb.model.service.UserRatingService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/ratings")
@Tag(name = "09. Valoraciones", description = "Valorar a otros usuarios en un evento")
public class UserRatingController {

	@Autowired
	private UserRatingService userRatingService;
	
	@PostMapping
	public ResponseEntity<?> createRating(@RequestBody RatingDto dto, Authentication authentication) {
		try {
			String reviewerEmail = authentication.getName();
			UserRating savedRating = userRatingService.createRating(reviewerEmail, dto);
			return ResponseEntity.status(HttpStatus.CREATED).body(savedRating);
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	// devuelve el dto con la nota y denuncias
	@GetMapping("/user/{userId}/reputation")
	public ResponseEntity<ReputationDto> getUserReputation(@PathVariable int userId) {
		try {
			ReputationDto reputation = userRatingService.getUserReputation(userId);
			return ResponseEntity.ok(reputation);
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@GetMapping("/user/{userId}/details")
	public ResponseEntity<List<UserRating>> getUserRatingDetailed(@PathVariable int userId) {
		try {
			List<UserRating> ratings = userRatingService.getUserRatingsDetailed(userId);
			return ResponseEntity.ok(ratings);
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
		
	}
}

