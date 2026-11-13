package co.edu.uco.kudos.negocio.negocio;

import java.util.List;
import java.util.UUID;

import co.edu.uco.kudos.dominio.UsuarioDominio;

public interface UsuarioNegocio {

	UsuarioDominio registrarUsuario(UsuarioDominio datos, UUID idFacultad, UUID idProgramaAcademico);

	UsuarioDominio iniciarSesion(String correoInstitucional, String clave);

	UsuarioDominio consultarUsuario(UUID id);

	UsuarioDominio actualizarMiInformacion(UUID idUsuario, UsuarioDominio datos);

	List<UsuarioDominio> consultarUsuariosActivos(UsuarioDominio filtro);

	void evaluarActivacion(UUID idUsuario);
}
