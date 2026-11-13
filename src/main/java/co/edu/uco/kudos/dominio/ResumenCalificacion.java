package co.edu.uco.kudos.dominio;

import java.util.List;
import java.util.UUID;

public record ResumenCalificacion(double promedio, int cantidad, int miCalificacion) {

	private static final int SIN_CALIFICACION = 0;

	public static ResumenCalificacion de(List<CalificacionDominio> calificaciones, UUID idUsuario) {
		var promedio = calificaciones.stream().mapToInt(CalificacionDominio::getEstrellas).average().orElse(0);
		var mia = calificaciones.stream().filter(calificacion -> calificacion.getAutor().getId().equals(idUsuario))
				.mapToInt(CalificacionDominio::getEstrellas).findFirst().orElse(SIN_CALIFICACION);
		return new ResumenCalificacion(Math.round(promedio * 10) / 10.0, calificaciones.size(), mia);
	}
}
