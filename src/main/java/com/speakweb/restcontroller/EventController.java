package com.speakweb.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.speakweb.model.dto.EventDto;
import com.speakweb.model.entity.Event;
import com.speakweb.model.service.EventService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/events")
@Tag(name = "05. Eventos", description = "Ver y crear eventos-quedadas")
public class EventController {

	@Autowired
    private EventService eventService;
	
	// Obtener evento por id
	@GetMapping("/{id}")
	public ResponseEntity<Event> getEventById(@PathVariable int id) {
		Event event = eventService.getEventById(id);
		
		if (event == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(event);
	}
	
	// Listar eventos por grupo
	@GetMapping("/group/{groupId}")
	public ResponseEntity<List<Event>> getEventsByGroup(@PathVariable int groupId) {
		List<Event> events = eventService.getEventsByGroup(groupId);				
		if (events == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(events);
	}
	
	// Crear eventos
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
	
	// Borrar eventos
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteEvent(@PathVariable int id) {
		try {
			eventService.deleteEvent(id);
			
			return ResponseEntity.ok("Evento cancelado correctamente");
		
		} catch (Exception e) {
			e.printStackTrace();
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("No se puede eliminar el evento. Tiene participantes inscritos.");
		}
	}
	
}
