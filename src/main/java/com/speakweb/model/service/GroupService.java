package com.speakweb.model.service;

import java.util.List;

import com.speakweb.model.dto.GroupDto;
import com.speakweb.model.entity.BGroup;

public interface GroupService {

	BGroup getGroupById(int groupId);
	
	List<BGroup> getAllGroups(); // ver todos 
	BGroup createGroup(GroupDto dto); // crear un grupo bilingüe nuevo
	
}
