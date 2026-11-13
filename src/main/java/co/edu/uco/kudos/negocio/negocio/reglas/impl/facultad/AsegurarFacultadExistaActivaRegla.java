package co.edu.uco.kudos.negocio.negocio.reglas.impl.facultad;

import java.util.UUID;

import co.edu.uco.kudos.dao.factoria.DAOFactory;
import co.edu.uco.kudos.dominio.enums.EstadoRegistro;
import co.edu.uco.kudos.negocio.negocio.reglas.Regla;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.kudos.transversal.excepciones.KudosNegocioExcepcion;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class AsegurarFacultadExistaActivaRegla implements Regla<Object> {

	private static final Regla<Object> INSTANCIA = new AsegurarFacultadExistaActivaRegla();

	private AsegurarFacultadExistaActivaRegla() {
	}

	public static Regla<Object> obtenerInstancia() {
		return INSTANCIA;
	}

	@Override
	public void ejecutar(Object... datos) {
		var idFacultad = (UUID) datos[0];
		var daoFactory = (DAOFactory) datos[1];

		var facultad = daoFactory.obtenerFacultadDAO().consultarPorId(idFacultad);

		if (UtilUUID.esValorDefecto(facultad.getId())) {
			throw KudosNegocioExcepcion.crear(CatalogoMensajes.Facultad.USUARIO_ERROR_NO_EXISTE);
		}
		if (!EstadoRegistro.ACTIVA.name().equals(facultad.getEstado())) {
			throw KudosNegocioExcepcion.crear(CatalogoMensajes.Facultad.USUARIO_ERROR_INACTIVA);
		}
	}
}
