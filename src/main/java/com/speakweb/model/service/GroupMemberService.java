package com.speakweb.model.service;

import java.util.List;

import com.speakweb.model.entity.GroupMember;

public interface GroupMemberService {

	boolean isAdmin(int userId); // el admin puede crear eventos siempre
	boolean isMemberOfGroup(int userId, int groupId); // es miembro?
	boolean isExpertInGroup(int userId, int groupId); // es experto?
	boolean canCreateEvent(int userId, int groupId); // es admin o experto del grupo? -> puede abrir evento
	
	GroupMember joinGroup(String userEmail, int groupId); // userEmail viene del token de seguridad
	List<GroupMember> getGroupMembers(int groupId); // miembros de un grupo
	List<GroupMember> getMyGroups(String userEmail); // email del token
}
