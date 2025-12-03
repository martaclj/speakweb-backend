package com.speakweb.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.speakweb.model.dto.GroupDto;
import com.speakweb.model.entity.BGroup;
import com.speakweb.model.entity.Language;
import com.speakweb.model.repository.GroupRepository;
import com.speakweb.model.repository.LanguageRepository;

@Service
public class GroupServiceImpl implements GroupService {

	@Autowired
	private GroupRepository groupRepository;
	
	@Autowired
	private LanguageRepository languageRepository;
	
	@Override
	public BGroup getGroupById(int groupId) {

		return groupRepository.findById(groupId).orElse(null);
		
	}

	@Override
	public List<BGroup> getAllGroups() {

		return groupRepository.findAll();
	}

	@Override
	public BGroup createGroup(GroupDto dto) {

		// Busco objetos completos a partir del id del dto recibido
		Language lang1 = languageRepository.findById(dto.getLanguage1Id()).orElse(null);
		Language lang2 = languageRepository.findById(dto.getLanguage2Id()).orElse(null);		

		// existen?
		if (lang1 == null || lang2 == null) {
			throw new RuntimeException("Uno de los idiomas no existe");
		}
		
		// creamos la entidad
		BGroup newGroup = new BGroup();
		newGroup.setName(dto.getName());
		newGroup.setLanguage1(lang1);
		newGroup.setLanguage2(lang2);
		
		return groupRepository.save(newGroup);
	}
	
}
