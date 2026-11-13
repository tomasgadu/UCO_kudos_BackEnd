package co.edu.uco.kudos.dominio;

import java.time.LocalDateTime;
import java.util.UUID;

import co.edu.uco.kudos.dominio.enums.CodigoRol;
import co.edu.uco.kudos.dominio.enums.EstadoCuenta;
import co.edu.uco.kudos.dominio.enums.EstadoPublicacion;

public final class FabricaDominioPrueba {

	public static final LocalDateTime AHORA = LocalDateTime.of(2026, 11, 13, 10, 0);

	private FabricaDominioPrueba() {
	}

	public static UsuarioDominio usuario(CodigoRol codigo) {
		var rol = new RolDominio.Builder().id(UUID.randomUUID()).codigo(codigo).nombre(codigo.name()).build();
		return new UsuarioDominio.Builder().id(UUID.randomUUID()).nombres("Laura").apellidos("Gómez")
				.correoInstitucional("laura.gomez@uco.edu.co").numeroCelular("3001112233").correoVerificado(true)
				.celularVerificado(true).estadoCuenta(EstadoCuenta.ACTIVA).rol(rol).build();
	}

	public static UsuarioDominio estudiante() {
		return usuario(CodigoRol.ESTUDIANTE);
	}

	public static KudoDominio kudo(EstadoPublicacion estado) {
		return new KudoDominio.Builder().id(UUID.randomUUID()).autor(estudiante()).destinatario(estudiante())
				.contenido("Gracias por tu apoyo en el proyecto").fechaCreacion(AHORA).estado(estado).build();
	}
}
