package co.edu.uco.kudos.controlador.configuracion;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Permite que el Front-End (otro repositorio, otro puerto) consuma la API.
 */
@Configuration
public class ConfiguracionCors implements WebMvcConfigurer {

	@Value("${kudos.cors.origen-permitido}")
	private String origenPermitido;

	@Override
	public void addCorsMappings(CorsRegistry registro) {
		registro.addMapping("/api/**").allowedOrigins(origenPermitido)
				.allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS").allowedHeaders("*");
	}
}
