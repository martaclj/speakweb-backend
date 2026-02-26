package com.speakweb.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.speakweb.model.dto.ReportDto;
import com.speakweb.model.entity.UserEntity;
import com.speakweb.model.entity.UserReport;
import com.speakweb.model.repository.UserReportRepository;
import com.speakweb.model.repository.UserRepository;

@Service
public class UserReportServiceImpl implements UserReportService {
	@Autowired
	private UserReportRepository userReportRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public List<UserReport> getUserReportsDetailed(int userId) {
		UserEntity user = userRepository.findById(userId).orElse(null);
		
		if(user == null) { // usuario no existe
			return List.of();
		}
		
		// saca denuncias del usuario
		return userReportRepository.findByReportedUser(user);
	}

	@Override
	public UserReport createReport(String reporterEmail, ReportDto dto) {
		UserEntity reporter = userRepository.findByEmail(reporterEmail);
		UserEntity reported = userRepository.findById(dto.getReportedUserId()).orElse(null);
		
		if (reporter == null || reported == null) {
			throw new RuntimeException("Usuarios no válidos");
		}
		
		if (reporter.getId() == reported.getId() ) {
			throw new RuntimeException("No puedes denunciarte a ti mismo");
		}
		
		UserReport report = new UserReport();
		report.setReporter(reporter);
		report.setReportedUser(reported);
		report.setReason(dto.getReason());
		
		return userReportRepository.save(report);
	}

}
