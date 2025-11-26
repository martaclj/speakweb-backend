package com.speakweb.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.speakweb.model.entity.BGroup;
import com.speakweb.model.entity.Event;
import com.speakweb.model.repository.EventRepository;
import com.speakweb.model.repository.GroupRepository;

@Service
public class EventServiceImpl implements EventService {
	
	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private GroupRepository groupRepository;

	@Override
	public Event createEvent(Event event) {

		return eventRepository.save(event);
	}

	@Override
	public Event getEventById(int eventId) {

		return eventRepository.findById(eventId).orElse(null);
	}

	@Override
	public List<Event> getEventsByGroup(int groupId) {

		BGroup group = groupRepository.findById(groupId).orElse(null);
		
		return eventRepository.findByGroup(group);
	}

}
