package com.speakweb.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.speakweb.model.dto.RatingDto;
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
}
