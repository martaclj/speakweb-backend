package com.speakweb.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.speakweb.model.entity.BGroup;
import com.speakweb.model.entity.GroupMember;
import com.speakweb.model.entity.User;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, Integer>{

	boolean existsByUserAndGroup(User user, BGroup group); // evitamos que un usuario se apunte dos veces mismo grupo
	boolean existsByUserAndGroupAndIsExpert(User user, BGroup group, boolean isExpert); // comprobamos condición experto
	
}
