package com.speakweb.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.speakweb.model.entity.UserEntity;
import com.speakweb.model.entity.UserReport;

@Repository
public interface UserReportRepository extends JpaRepository<UserReport, Integer> {
	// contar denuncias recibidas por el usuario
	int countByReportedUser(UserEntity reportedUser);
	
	// para el admin
	List<UserReport> findByReportedUser(UserEntity reportedUser);

}
