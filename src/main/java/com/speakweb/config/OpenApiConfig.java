package com.speakweb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

// https://www.secdevoops.es/integrando-open-api-en-tu-proyecto-spring-boot-guia-paso-a-paso/
//https://swagger.io/docs/specification/v3_0/authentication/bearer-authentication/

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("Speakweb API")
						.version("1.0.0")
						.description("API del Proyecto Final de DAW - Documentación"))
				// Configuración de Seguridad JWT
				.addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
				.components(new Components()
						.addSecuritySchemes("bearerAuth", 
								new SecurityScheme()
								.name("bearerAuth")
								.type(SecurityScheme.Type.HTTP)
								.scheme("bearer")
								.bearerFormat("JWT")
								));
		
	}
}
