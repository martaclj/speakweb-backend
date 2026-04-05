// servicio para almacenar imágenes en carpeta local uploads
// repositorio consultado: https://github.com/philipplackner/SpringBootFileUploadDownload
package com.speakweb.model.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;



@Service
public class LocalImageStorageService {
	// carpeta base
	private final Path rootPath = Paths.get("uploads");

	public String storeFile(MultipartFile file) throws IOException {
		// crear directorio si no existe
		if (!Files.exists(rootPath)) {
			Files.createDirectories(rootPath);
		}
		// extraer extensión
		String originalName = file.getOriginalFilename();
		String ext = getFileExtension(originalName);
		// generar nombre UUID
		String storedName = UUID.randomUUID() + (ext.isEmpty() ? "" : "." + ext);
		Path filePath = rootPath.resolve(storedName);
		// copiar el archivo usando InputStream
		try (InputStream inputStream = file.getInputStream();
				OutputStream outputStream = Files.newOutputStream(filePath, StandardOpenOption.CREATE_NEW)) {
			StreamUtils.copy(inputStream, outputStream);
		}
		// devolver la url relativa
		return "/uploads/" + storedName;
	}
	
	private String getFileExtension(String fileName) {
		if (fileName == null) return "";
		int lastDot = fileName.lastIndexOf('.');
		return lastDot == -1 ? "" : fileName.substring(lastDot + 1);
	}
}
