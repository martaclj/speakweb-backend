package com.speakweb.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.speakweb.model.entity.BGroup;
import com.speakweb.model.entity.GroupMember;
import com.speakweb.model.entity.UserEntity;
import com.speakweb.model.entity.enums.Role;
import com.speakweb.model.repository.GroupMemberRepository;
import com.speakweb.model.repository.GroupRepository;
import com.speakweb.model.repository.UserLanguageRepository;
import com.speakweb.model.repository.UserRepository;

@Service
public class GroupMemberServiceImpl implements GroupMemberService {

	@Autowired
	private GroupMemberRepository groupMemberRepository;
	
	@Autowired
	private GroupRepository groupRepository;
	
	@Autowired
	private UserService userService; // userservice para comprobar rol
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserLanguageRepository userLanguageRepository;
	
	@Override
	public boolean isAdmin(int userId) {

		UserEntity user = userService.getUserById(userId);
		
		if (user == null) {
			return false;
		}
		
		return user.getRole() == Role.ADMIN;
	}

	@Override
	public boolean isMemberOfGroup(int userId, int groupId) {

		UserEntity user = userService.getUserById(userId);
		BGroup group = groupRepository.findById(groupId).orElse(null);
		
		if (user == null || group == null) {
			return false;
		}
		
		return groupMemberRepository.existsByUserAndGroup(user, group);
		
	}

	@Override
	public boolean isExpertInGroup(int userId, int groupId) {

		UserEntity user = userService.getUserById(userId);
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

	// acciones en el grupo
	@Override
	public GroupMember joinGroup(String userEmail, int groupId) {
		UserEntity user = userRepository.findByEmail(userEmail);
		BGroup group = groupRepository.findById(groupId).orElse(null);
		
		if (user == null || group == null) {
			throw new RuntimeException("Usuario o Grupo no encontrado");
		}
		if (isMemberOfGroup(user.getId(), groupId)) {
			throw new RuntimeException("Ya eres miembro de este grupo");
		}
		
		
		GroupMember newMember = new GroupMember();
		newMember.setUser(user);
		newMember.setGroup(group);
		newMember.setExpert(false); // por defecto entra como usu normal
		
		return groupMemberRepository.save(newMember);
	}

	@Override
	public List<GroupMember> getGroupMembers(int groupId) {
		BGroup group = groupRepository.findById(groupId).orElse(null);
		
		if(group == null) {
		return null;
		}
		
		return groupMemberRepository.findByGroup(group);
	}

	@Override
	public List<GroupMember> getMyGroups(String userEmail) {
		UserEntity user = userRepository.findByEmail(userEmail);
        return groupMemberRepository.findByUser(user);
	}

	
}
