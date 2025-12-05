package com.speakweb.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.speakweb.model.entity.Language;

@Repository
public interface LanguageRepository  extends JpaRepository<Language, Integer> {

	boolean existsByCode(String code);

}
