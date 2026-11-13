package co.edu.uco.kudos.negocio.negocio.reglas.impl.usuario;

import java.util.UUID;

import co.edu.uco.kudos.dao.factoria.DAOFactory;
import co.edu.uco.kudos.dominio.RolDominio;
import co.edu.uco.kudos.negocio.negocio.reglas.Regla;
import co.edu.uco.kudos.negocio.negocio.reglas.impl.facultad.AsegurarFacultadExistaActivaRegla;
import co.edu.uco.kudos.negocio.negocio.reglas.impl.programa.AsegurarProgramaExistaActivoRegla;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.kudos.transversal.excepciones.KudosNegocioExcepcion;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class AsegurarAmbitoCorrespondaRolRegla implements Regla<Object> {

	private static final Regla<Object> INSTANCIA = new AsegurarAmbitoCorrespondaRolRegla();

	private AsegurarAmbitoCorrespondaRolRegla() {
	}

	public static Regla<Object> obtenerInstancia() {
		return INSTANCIA;
	}

	@Override
	public void ejecutar(Object... datos) {
		var rol = (RolDominio) datos[0];
		var idFacultad = (UUID) datos[1];
		var idPrograma = (UUID) datos[2];
		var daoFactory = (DAOFactory) datos[3];

		var tieneFacultad = !UtilUUID.esValorDefecto(idFacultad);
		var tienePrograma = !UtilUUID.esValorDefecto(idPrograma);

		switch (rol.getCodigo().getAmbito()) {
		case PROGRAMA -> {
			if (!tienePrograma || tieneFacultad) {
				throw KudosNegocioExcepcion.crear(CatalogoMensajes.Usuario.USUARIO_ERROR_AMBITO_PROGRAMA);
			}
			AsegurarProgramaExistaActivoRegla.obtenerInstancia().ejecutar(idPrograma, daoFactory);
		}
		case FACULTAD -> {
			if (!tieneFacultad || tienePrograma) {
				throw KudosNegocioExcepcion.crear(CatalogoMensajes.Usuario.USUARIO_ERROR_AMBITO_FACULTAD);
			}
			AsegurarFacultadExistaActivaRegla.obtenerInstancia().ejecutar(idFacultad, daoFactory);
		}
		default -> {
			if (tieneFacultad || tienePrograma) {
				throw KudosNegocioExcepcion.crear(CatalogoMensajes.Usuario.USUARIO_ERROR_AMBITO_NO_APLICA);
			}
		}
		}
	}
}
