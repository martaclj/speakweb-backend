package com.speakweb.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.speakweb.model.dto.EventDto;
import com.speakweb.model.entity.BGroup;
import com.speakweb.model.entity.Event;
import com.speakweb.model.entity.UserEntity;
import com.speakweb.model.repository.EventRepository;
import com.speakweb.model.repository.GroupRepository;
import com.speakweb.model.repository.UserRepository;

@Service
public class EventServiceImpl implements EventService {
	
	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private GroupRepository groupRepository;
	
	@Autowired
    private UserRepository userRepository;
    
    @Autowired
    private GroupMemberService groupMemberService;

	@Override
	public Event createEvent(String userEmail, EventDto dto) {

		UserEntity user = userRepository.findByEmail(userEmail);
		BGroup group = groupRepository.findById(dto.getGroupId()).orElse(null);
		
		if (user == null || group == null) {
			throw new RuntimeException("Datos inválidos");
		}
		
		boolean hasPrivilege = groupMemberService.canCreateEvent(user.getId(), group.getId());
		
		if (!hasPrivilege) {
			throw new RuntimeException
			("No tienes permiso para crear eventos (Solo Expertos pueden crear)");
		}
		
		Event event = new Event();
		event.setTitle(dto.getTitle());
		event.setDescription(dto.getDescription());
		event.setStartTime(dto.getStartTime());
		event.setLocation(dto.getLocation());
		event.setExternalLink(dto.getExternalLink());
		event.setGroup(group);
		
		return eventRepository.save(event);
	}

	/* @Override
	public Event getEventById(int eventId) {

		return eventRepository.findById(eventId).orElse(null);
	}
	*/

	@Override
	public List<Event> getEventsByGroup(int groupId) {

		BGroup group = groupRepository.findById(groupId).orElse(null);
		
		return eventRepository.findByGroup(group);
	}

}
