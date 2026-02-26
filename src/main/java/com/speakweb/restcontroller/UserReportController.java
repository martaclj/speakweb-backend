package com.speakweb.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.speakweb.model.dto.ReportDto;
import com.speakweb.model.entity.UserReport;
import com.speakweb.model.service.UserReportService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/reports")
@Tag(name = "10. Denuncias", description = "Gestión de denuncias y reportes de usuario")
public class UserReportController {
	
	@Autowired
	private UserReportService userReportService;
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<UserReport>> getUserReportsDetailed(@PathVariable int userId) {
		try {
			List<UserReport> reports = userReportService.getUserReportsDetailed(userId);
			return ResponseEntity.ok(reports);
		} catch (Exception e) {
			return ResponseEntity.badRequest().build(); // Build the response entity with no body.
		}
	}
	
	@PostMapping
	public ResponseEntity<?> createReport(@RequestBody ReportDto dto, Authentication authentication) {
		try {
			String reporterEmail = authentication.getName();
			UserReport savedReport = userReportService.createReport(reporterEmail, dto);
			return ResponseEntity.status(HttpStatus.CREATED).body(savedReport);
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
}
