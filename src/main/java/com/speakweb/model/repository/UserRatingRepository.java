package com.speakweb.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.speakweb.model.entity.UserEntity;
import com.speakweb.model.entity.UserRating;

@Repository
public interface UserRatingRepository extends JpaRepository<UserRating, Integer> {
	List<UserRating> findByReviewedUser(UserEntity reviewedUser);
	
	// evitar que 1 usuario vote 2 veces en mismo evento y persona
	boolean existsByReviewerAndReviewedUserAndEventId(UserEntity reviewer, UserEntity reviewedUser, int eventId);
}
