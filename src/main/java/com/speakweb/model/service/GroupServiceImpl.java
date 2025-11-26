package com.speakweb.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.speakweb.model.entity.BGroup;
import com.speakweb.model.repository.GroupRepository;

@Service
public class GroupServiceImpl implements GroupService {

	@Autowired
	private GroupRepository groupRepository;
	
	@Override
	public BGroup getGroupById(int groupId) {

		return groupRepository.findById(groupId).orElse(null);
		
	}
	
}
