package com.speakweb.model.service;

import java.util.List;

import com.speakweb.model.entity.UserReport;

public interface UserReportService {
	// para el admin, método para ver todas las denuncias de ese usuario en perfil de usuario
	List<UserReport> getUserReportsDetailed(int userId);

}
