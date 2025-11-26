package com.speakweb.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.speakweb.model.entity.User;
import com.speakweb.model.entity.UserLanguage;

@Repository
public interface UserLanguageRepository extends JpaRepository<UserLanguage, Integer> {

	List<UserLanguage> findByUser(User user); // lista de idiomas asociados al usuario
	
}
