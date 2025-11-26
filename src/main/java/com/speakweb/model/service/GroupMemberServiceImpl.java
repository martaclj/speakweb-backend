package com.speakweb.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.speakweb.model.entity.BGroup;
import com.speakweb.model.entity.User;
import com.speakweb.model.entity.enums.Role;
import com.speakweb.model.repository.GroupMemberRepository;
import com.speakweb.model.repository.GroupRepository;

@Service
public class GroupMemberServiceImpl implements GroupMemberService {

	@Autowired
	private GroupMemberRepository groupMemberRepository;
	
	@Autowired
	private GroupRepository groupRepository;
	
	@Autowired
	private UserService userService; // userservice para comprobar rol
	
	@Override
	public boolean isAdmin(int userId) {

		User user = userService.getUserById(userId);
		
		if (user == null) {
			return false;
		}
		
		return user.getRole() == Role.ADMIN;
	}

	@Override
	public boolean isMemberOfGroup(int userId, int groupId) {

		User user = userService.getUserById(userId);
		BGroup group = groupRepository.findById(groupId).orElse(null);
		
		if (user == null || group == null) {
			return false;
		}
		
		return groupMemberRepository.existsByUserAndGroup(user, group);
		
	}

	@Override
	public boolean isExpertInGroup(int userId, int groupId) {

		User user = userService.getUserById(userId);
		BGroup group = groupRepository.findById(groupId).orElse(null);
		
		if (user == null || group == null) {
			return false;
		}
		
		return groupMemberRepository.existsByUserAndGroupAndIsExpert(user, group, true);
		// true si el usuario está marcado como experto en ese grupo
	}

	@Override
	public boolean canCreateEvent(int userId, int groupId) {

		if (isAdmin(userId)) { // siempre puede crear evento
			return true;
		}
		
		return isExpertInGroup(userId, groupId); // si es experto puede crear
		
	}

	
}
