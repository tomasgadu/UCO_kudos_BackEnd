package co.edu.uco.kudos.negocio.fachada;

import java.util.List;
import java.util.UUID;

import co.edu.uco.kudos.dto.CredencialGoogleDTO;
import co.edu.uco.kudos.dto.CredencialesDTO;
import co.edu.uco.kudos.dto.RegistroUsuarioDTO;
import co.edu.uco.kudos.dto.UsuarioDTO;
import co.edu.uco.kudos.dto.VerificacionCodigoDTO;

public interface UsuarioFachada {

	UsuarioDTO registrarUsuario(RegistroUsuarioDTO datos);

	void verificarCodigo(VerificacionCodigoDTO datos);

	void reenviarCodigo(VerificacionCodigoDTO datos);

	UsuarioDTO iniciarSesion(CredencialesDTO credenciales);

	UsuarioDTO iniciarSesionConGoogle(CredencialGoogleDTO credencial);

	UsuarioDTO consultarMiInformacion(UUID idUsuario);

	UsuarioDTO actualizarMiInformacion(UUID idUsuario, UsuarioDTO datos);

	List<UsuarioDTO> consultarUsuariosActivos(UUID idActor, UUID idRol);
}
