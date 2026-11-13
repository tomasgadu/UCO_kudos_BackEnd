package co.edu.uco.kudos.negocio.negocio.reglas.impl.usuario;

import java.util.UUID;

import co.edu.uco.kudos.dao.factoria.DAOFactory;
import co.edu.uco.kudos.dominio.RolDominio;
import co.edu.uco.kudos.dominio.enums.EstadoCuenta;
import co.edu.uco.kudos.entidad.CoordinacionFacultadEntidad;
import co.edu.uco.kudos.entidad.CoordinacionProgramaEntidad;
import co.edu.uco.kudos.entidad.UsuarioEntidad;
import co.edu.uco.kudos.negocio.negocio.reglas.Regla;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.kudos.transversal.excepciones.KudosNegocioExcepcion;
import co.edu.uco.kudos.transversal.utilitarios.UtilFecha;


public final class AsegurarCargoDisponibleRegla implements Regla<Object> {

	private static final Regla<Object> INSTANCIA = new AsegurarCargoDisponibleRegla();

	private AsegurarCargoDisponibleRegla() {
	}

	public static Regla<Object> obtenerInstancia() {
		return INSTANCIA;
	}

	@Override
	public void ejecutar(Object... datos) {
		var rol = (RolDominio) datos[0];
		var idFacultad = (UUID) datos[1];
		var idPrograma = (UUID) datos[2];
		var idSolicitante = (UUID) datos[3];
		var daoFactory = (DAOFactory) datos[4];

		var ocupado = switch (rol.getCodigo()) {
		case RECTOR, COORDINADOR_ACADEMICO -> existeTitularActivo(rol.getId(), idSolicitante, daoFactory);
		case COORDINADOR_FACULTAD -> {
			var filtro = new CoordinacionFacultadEntidad();
			filtro.getFacultad().setId(idFacultad);
			yield daoFactory.obtenerCoordinacionFacultadDAO().consultarPorFiltro(filtro).stream()
					.anyMatch(c -> UtilFecha.esValorDefecto(c.getFechaFin()));
		}
		case COORDINADOR_PROGRAMA -> {
			var filtro = new CoordinacionProgramaEntidad();
			filtro.getProgramaAcademico().setId(idPrograma);
			yield daoFactory.obtenerCoordinacionProgramaDAO().consultarPorFiltro(filtro).stream()
					.anyMatch(c -> UtilFecha.esValorDefecto(c.getFechaFin()));
		}
		default -> false;
		};

		if (ocupado) {
			throw KudosNegocioExcepcion.crear(CatalogoMensajes.Usuario.USUARIO_ERROR_CARGO_OCUPADO);
		}
	}

	private boolean existeTitularActivo(UUID idRol, UUID idSolicitante, DAOFactory daoFactory) {
		var filtro = new UsuarioEntidad();
		filtro.getRol().setId(idRol);
		filtro.setEstadoCuenta(EstadoCuenta.ACTIVA.name());
		return daoFactory.obtenerUsuarioDAO().consultarPorFiltro(filtro).stream()
				.anyMatch(u -> !u.getId().equals(idSolicitante));
	}
}
