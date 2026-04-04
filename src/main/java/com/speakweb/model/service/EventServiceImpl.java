package com.speakweb.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.speakweb.model.dto.EventDto;
import com.speakweb.model.entity.BGroup;
import com.speakweb.model.entity.Event;
import com.speakweb.model.entity.UserEntity;
import com.speakweb.model.entity.enums.EventType;
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

		// busco usuario y grupo en la bd
		UserEntity user = userRepository.findByEmail(userEmail);
		BGroup group = groupRepository.findById(dto.getGroupId()).orElse(null);
		
		// si falla, corto ejecución
		if (user == null || group == null) {
			throw new RuntimeException("Datos inválidos");
		}
	
		// compruebo permisos - admin o experto en grupo
		boolean isAdmin = user.getRole().name().equals("ADMIN");
		boolean hasPrivilege = isAdmin || groupMemberService.canCreateEvent(user.getId(), group.getId());
		
		if (!hasPrivilege) {
			throw new RuntimeException
			("No tienes permiso para crear eventos (Solo Expertos pueden crear)");
		}
		
		// instancio evento y le paso datos del dto
		Event event = new Event();
		event.setTitle(dto.getTitle());
		event.setDescription(dto.getDescription());
		event.setStartTime(dto.getStartTime());
		event.setLocation(dto.getLocation());
		event.setExternalLink(dto.getExternalLink());
		event.setGroup(group);
		event.setImageUrl(dto.getImageUrl());
		event.setCreator(user);
		
		/* lógica ONLINE vs. PRESENTIAL
		 * según el tipo, se guarda un campo y se limpia el contrario
		 *  */
		try {
			if (dto.getType() != null) {
				// conversión del string al enum
				EventType type = EventType.valueOf(dto.getType().toUpperCase());
				event.setType(type);
				
				// si online, borro location
				if (type == EventType.ONLINE) {
					event.setLocation(null);
					event.setExternalLink(dto.getExternalLink());
				} else {
				// si presencial, borro el link
					event.setLocation(dto.getLocation());
					event.setExternalLink(null);
				}
			
			} else {
				// valor por defecto
				event.setType(EventType.PRESENTIAL); // si viene nullo por defecto PRESENTIAL
				event.setLocation(dto.getLocation());
			}
		} catch (Exception e) {
			// si error --> x def. presential
			event.setType(EventType.PRESENTIAL);
			event.setLocation(dto.getLocation());
		}
		
		// guardo en bd y devuelvo el evento
		return eventRepository.save(event);
	}

	@Override
	public Event getEventById(int eventId) {
		// busco por id, si no devuelvo null
		return eventRepository.findById(eventId).orElse(null);
	}

	@Override
	public List<Event> getEventsByGroup(int groupId) {
		// obtengo el grupo para buscar sus eventos
		BGroup group = groupRepository.findById(groupId).orElse(null);
		
		return eventRepository.findByGroup(group);
	}
	
	@Override
	public List<Event> getAllEvents() {
		// TODO Auto-generated method stub
		// devuelvo toda la tabla de eventos (para el panel de admin)
		return eventRepository.findAll();
	}

	@Override
	public void deleteEvent(int eventId, String userEmail) {
		Event event = eventRepository.findById(eventId).orElse(null);
		
		if (event == null) {
			throw new RuntimeException("El evento no existe");
		}
		
		// recupero usuario que intenta borrar
		UserEntity user = userRepository.findByEmail(userEmail);
		// si es admin
		boolean isAdmin = user.getRole().name().equals("ADMIN");
		// si es el organizador del evento
		boolean isCreator = event.getCreator().getId() == user.getId();
		
		// Solo el admin o el creador puede borrar el evento (admin todos - creador los suyos)
		if (!isAdmin && !isCreator) {
			throw new RuntimeException("No tienes permiso para cancelar este evento");
		}
		
		// borrado 
		eventRepository.deleteById(eventId);
		
	}


}
