package com.speakweb.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.speakweb.model.entity.Event;
import com.speakweb.model.entity.EventParticipant;
import com.speakweb.model.entity.User;
import com.speakweb.model.repository.EventParticipantRepository;
import com.speakweb.model.repository.EventRepository;

@Service
public class EventParticipantServiceImpl implements EventParticipantService {

	@Autowired
	private EventParticipantRepository eventParticipantRepository;
	
	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private UserService userService;
	
	@Override
	public boolean isUserInEvent(int userId, int eventId) {

		User user = userService.getUserById(userId);
		Event event = eventRepository.findById(eventId).orElse(null);
		
		if (user == null || event == null) {
			return false;
		}
		
		return eventParticipantRepository.existsByEventAndUser(event, user);
	}

	@Override
	public EventParticipant addUserToEvent(int userId, int eventId) {

		User user = userService.getUserById(userId);
		Event event = eventRepository.findById(eventId).orElse(null);

		if (user == null || event == null) {
			return null;
		}
		
		// Evitar registros duplicados con existsByEventAndUser
		if (eventParticipantRepository.existsByEventAndUser(event, user)) {
			return null;
			
		}
		
		EventParticipant participant = new EventParticipant();
		participant.setUser(user);
		participant.setEvent(event);
		
		return eventParticipantRepository.save(participant); // usuario añadido a evento

	}

	@Override
	public List<EventParticipant> getParticipantsByEvent(int eventId) {

		Event event = eventRepository.findById(eventId).orElse(null);
		
		return eventParticipantRepository.findByEvent(event);
	}

	@Override
	public List<EventParticipant> getEventsByUser(int userId) {
		User user = userService.getUserById(userId);
		
		return eventParticipantRepository.findByUser(user); // list de usuarios
	}

}
