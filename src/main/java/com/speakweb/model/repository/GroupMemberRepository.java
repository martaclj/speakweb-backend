package com.speakweb.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.speakweb.model.entity.BGroup;
import com.speakweb.model.entity.GroupMember;
import com.speakweb.model.entity.UserEntity;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, Integer>{

	boolean existsByUserAndGroup(UserEntity user, BGroup group); // usuario es miembro? evitamos que un usuario se apunte dos veces mismo grupo
	boolean existsByUserAndGroupAndIsExpert(UserEntity user, BGroup group, boolean isExpert); // comprobamos condición experto
	List<GroupMember> findByGroup(BGroup group);
	List<GroupMember> findByUser(UserEntity user); // grupos de un usuario / mis grupos
}
