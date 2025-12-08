package com.speakweb.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.speakweb.model.dto.GroupDto;
import com.speakweb.model.entity.BGroup;
import com.speakweb.model.service.GroupService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/groups")
@Tag(name = "03. Comunidades (Grupos)", description = "Ver todos los grupos disponibles y crear grupos nuevos")
public class GroupController {

	@Autowired
	private GroupService groupService;
	
	// List de groups
	@GetMapping
	public ResponseEntity<List<BGroup>> getAllGroups() {
		return ResponseEntity.ok(groupService.getAllGroups());
	}
	
	// Crear group
	@PostMapping
	public ResponseEntity<?> createGroup(@RequestBody GroupDto dto) {
		try {
			BGroup createdGroup = groupService.createGroup(dto);
			return ResponseEntity.status(HttpStatus.CREATED).body(createdGroup);
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
