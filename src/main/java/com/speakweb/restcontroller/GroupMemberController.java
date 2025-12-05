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

import com.speakweb.model.dto.JoinGroupDto;
import com.speakweb.model.entity.GroupMember;
import com.speakweb.model.service.GroupMemberService;

@RestController
@RequestMapping("/api/group-members")
public class GroupMemberController {

	@Autowired
    private GroupMemberService groupMemberService;
	
	@GetMapping("/my-groups")
	public ResponseEntity<List<GroupMember>> getMyGroups(Authentication authentication) {
		String email = authentication.getName();
		return ResponseEntity.ok(groupMemberService.getMyGroups(email));
	}
	
	@GetMapping("/group/{groupId}")
	public ResponseEntity<List<GroupMember>> getMembersByGroup(@PathVariable int groupId) {
		List<GroupMember> members = groupMemberService.getGroupMembers(groupId);
		
		if (members == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(members);
	}
	
	@PostMapping("/join")
	public ResponseEntity<?> joinGroup(@RequestBody JoinGroupDto dto, Authentication authentication) {
		
		try {
			String email = authentication.getName();
			
			GroupMember member = groupMemberService.joinGroup(email, dto.getGroupId());
			
			return ResponseEntity.status(HttpStatus.CREATED).body(member);
		
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
}
