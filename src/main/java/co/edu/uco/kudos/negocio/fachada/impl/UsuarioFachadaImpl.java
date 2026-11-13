package co.edu.uco.kudos.negocio.fachada.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.kudos.dominio.RolDominio;
import co.edu.uco.kudos.dominio.UsuarioDominio;
import co.edu.uco.kudos.dominio.enums.TipoVerificacion;
import co.edu.uco.kudos.dto.CredencialesDTO;
import co.edu.uco.kudos.dto.RegistroUsuarioDTO;
import co.edu.uco.kudos.dto.UsuarioDTO;
import co.edu.uco.kudos.dto.VerificacionCodigoDTO;
import co.edu.uco.kudos.negocio.fachada.FachadaBase;
import co.edu.uco.kudos.negocio.fachada.UsuarioFachada;
import co.edu.uco.kudos.negocio.fachada.ensamblador.impl.UsuarioEnsambladorDTO;
import co.edu.uco.kudos.negocio.negocio.impl.UsuarioNegocioImpl;
import co.edu.uco.kudos.negocio.negocio.impl.VerificacionContactoNegocioImpl;
import co.edu.uco.kudos.negocio.negocio.reglas.impl.usuario.ObtenerUsuarioActivoRegla;

public final class UsuarioFachadaImpl extends FachadaBase implements UsuarioFachada {

	@Override
	public UsuarioDTO registrarUsuario(RegistroUsuarioDTO datos) {
		return ejecutarEnTransaccion("registrar el usuario", daoFactory -> {
			var dominio = new UsuarioDominio.Builder().nombres(datos.getNombres()).apellidos(datos.getApellidos())
					.correoInstitucional(datos.getCorreoInstitucional()).numeroCelular(datos.getNumeroCelular())
					.clave(datos.getClave()).rol(new RolDominio.Builder().id(datos.getIdRol()).build()).build();
			var registrado = new UsuarioNegocioImpl(daoFactory).registrarUsuario(dominio, datos.getIdFacultad(),
					datos.getIdProgramaAcademico());
			return UsuarioEnsambladorDTO.getInstancia().convertirADTO(registrado);
		});
	}

	@Override
	public void verificarCodigo(VerificacionCodigoDTO datos) {
		ejecutarEnTransaccion("verificar el código", daoFactory -> {
			new VerificacionContactoNegocioImpl(daoFactory).verificarCodigo(datos.getCorreoInstitucional(),
					TipoVerificacion.desdeTexto(datos.getTipo()), datos.getCodigo());
			return null;
		});
	}

	@Override
	public void reenviarCodigo(VerificacionCodigoDTO datos) {
		ejecutarEnTransaccion("reenviar el código", daoFactory -> {
			new VerificacionContactoNegocioImpl(daoFactory).reenviarCodigo(datos.getCorreoInstitucional(),
					TipoVerificacion.desdeTexto(datos.getTipo()));
			return null;
		});
	}

	/** Es transacción porque el inicio de sesión queda en la auditoría. */
	@Override
	public UsuarioDTO iniciarSesion(CredencialesDTO credenciales) {
		return ejecutarEnTransaccion("iniciar sesión", daoFactory -> UsuarioEnsambladorDTO.getInstancia().convertirADTO(
				new UsuarioNegocioImpl(daoFactory).iniciarSesion(credenciales.getCorreoInstitucional(),
						credenciales.getClave())));
	}

	@Override
	public UsuarioDTO consultarMiInformacion(UUID idUsuario) {
		return ejecutarConsulta("consultar su información", daoFactory -> UsuarioEnsambladorDTO.getInstancia()
				.convertirADTO(new UsuarioNegocioImpl(daoFactory).consultarUsuario(idUsuario)));
	}

	@Override
	public UsuarioDTO actualizarMiInformacion(UUID idUsuario, UsuarioDTO datos) {
		return ejecutarEnTransaccion("actualizar su información", daoFactory -> {
			var dominio = UsuarioEnsambladorDTO.getInstancia().convertirADominio(datos);
			var actualizado = new UsuarioNegocioImpl(daoFactory).actualizarMiInformacion(idUsuario, dominio);
			return UsuarioEnsambladorDTO.getInstancia().convertirADTO(actualizado);
		});
	}

	@Override
	public List<UsuarioDTO> consultarUsuariosActivos(UUID idActor, UUID idRol) {
		return ejecutarConsulta("consultar los usuarios", daoFactory -> {
			ObtenerUsuarioActivoRegla.ejecutar(idActor, daoFactory);
			var filtro = new UsuarioDominio.Builder().rol(new RolDominio.Builder().id(idRol).build()).build();
			return UsuarioEnsambladorDTO.getInstancia()
					.convertirADTO(new UsuarioNegocioImpl(daoFactory).consultarUsuariosActivos(filtro));
		});
	}
}
