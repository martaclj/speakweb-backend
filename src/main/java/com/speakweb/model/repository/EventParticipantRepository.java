package com.speakweb.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.speakweb.model.entity.Event;
import com.speakweb.model.entity.EventParticipant;
import com.speakweb.model.entity.User;

@Repository
public interface EventParticipantRepository extends JpaRepository<EventParticipant, Integer>{

	boolean existsByEventAndUser(Event event, User user); // está apuntado ya al evento?
	List<EventParticipant> findByEvent(Event event); // lista los participantes de 1 evento
	List<EventParticipant> findByUser(User user); // a cuántos eventos está apuntado el usuario? eventos en los que está apuntado un usuario
	
}
