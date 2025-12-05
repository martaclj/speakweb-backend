package com.speakweb.model.service;

import java.util.List;

import com.speakweb.model.dto.EventDto;
import com.speakweb.model.entity.Event;

public interface EventService {

	Event createEvent(String userEmail, EventDto dto);
	// Event getEventById(int eventId);
	List<Event> getEventsByGroup(int groupId); // eventos de un grupo determinado
}
