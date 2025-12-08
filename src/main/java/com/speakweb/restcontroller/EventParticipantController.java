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

import com.speakweb.model.dto.JoinEventDto;
import com.speakweb.model.entity.EventParticipant;
import com.speakweb.model.entity.UserEntity;
import com.speakweb.model.service.EventParticipantService;
import com.speakweb.model.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/event-participants")
@Tag(name = "06. Asistir a Eventos", description = "Apuntarse a un evento y listar sus participantes")
public class EventParticipantController {

	@Autowired
	private EventParticipantService participantService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/join")
	public ResponseEntity<?> joinEvent(@RequestBody JoinEventDto dto, Authentication authentication) {
		try {
			String email = authentication.getName();
			
			UserEntity user = userService.getUserByEmail(email);
			
			EventParticipant participation = participantService.addUserToEvent(user.getId(), dto.getEventId());
			
			return ResponseEntity.status(HttpStatus.CREATED).body(participation);
		
		} catch (RuntimeException e) {
			
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping("/event/{eventId}")
	public ResponseEntity<List<EventParticipant>> getParticipants(@PathVariable int eventId) {
		
		List<EventParticipant> list = participantService.getParticipantsByEvent(eventId);
		
		if (list == null) {
			
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(list);
	}
	
}
