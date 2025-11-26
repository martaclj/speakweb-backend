package com.speakweb.model.service;

public interface GroupMemberService {

	boolean isAdmin(int userId); // el admin puede crear eventos siempre
	boolean isMemberOfGroup(int userId, int groupId); // es miembro?
	boolean isExpertInGroup(int userId, int groupId); // es experto?
	boolean canCreateEvent(int userId, int groupId); // es admin o experto del grupo? -> puede abrir evento
	
}
