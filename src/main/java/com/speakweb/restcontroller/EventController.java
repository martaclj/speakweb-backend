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

import com.speakweb.model.dto.EventDto;
import com.speakweb.model.entity.Event;
import com.speakweb.model.service.EventService;

@RestController
@RequestMapping("/api/events")
public class EventController {

	@Autowired
    private EventService eventService;
	
	@GetMapping("/group/{groupId}")
	public ResponseEntity<List<Event>> getEventsByGroup(@PathVariable int groupId) {
		List<Event> events = eventService.getEventsByGroup(groupId);				
		if (events == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(events);
	}
	
	@PostMapping
	public ResponseEntity<?> createEvent(@RequestBody EventDto dto, Authentication authentication) {
		
		try {
			String email = authentication.getName();
			Event created = eventService.createEvent(email, dto);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(created);
			
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
}
