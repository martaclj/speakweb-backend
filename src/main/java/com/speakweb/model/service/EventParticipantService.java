package com.speakweb.model.service;

import java.util.List;

import com.speakweb.model.entity.EventParticipant;

public interface EventParticipantService {
	
	boolean isUserInEvent(int userId, int eventId); // ya apuntado?
	EventParticipant addUserToEvent(int userId, int eventId); // añadir usu a evento
	List<EventParticipant> getParticipantsByEvent(int eventId); // lista participantes evento
	List<EventParticipant> getEventsByUser(int userId); // eventos a los que está apuntado un usuario

}
