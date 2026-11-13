package co.edu.uco.kudos.negocio.negocio.reglas.impl.programa;

import java.util.UUID;

import co.edu.uco.kudos.dao.factoria.DAOFactory;
import co.edu.uco.kudos.dominio.enums.EstadoRegistro;
import co.edu.uco.kudos.negocio.negocio.reglas.Regla;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.kudos.transversal.excepciones.KudosNegocioExcepcion;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class AsegurarProgramaExistaActivoRegla implements Regla<Object> {

	private static final Regla<Object> INSTANCIA = new AsegurarProgramaExistaActivoRegla();

	private AsegurarProgramaExistaActivoRegla() {
	}

	public static Regla<Object> obtenerInstancia() {
		return INSTANCIA;
	}

	@Override
	public void ejecutar(Object... datos) {
		var idPrograma = (UUID) datos[0];
		var daoFactory = (DAOFactory) datos[1];

		var programa = daoFactory.obtenerProgramaAcademicoDAO().consultarPorId(idPrograma);

		if (UtilUUID.esValorDefecto(programa.getId())) {
			throw KudosNegocioExcepcion.crear(CatalogoMensajes.ProgramaAcademico.USUARIO_ERROR_NO_EXISTE);
		}
		if (!EstadoRegistro.ACTIVA.name().equals(programa.getEstado())) {
			throw KudosNegocioExcepcion.crear(CatalogoMensajes.ProgramaAcademico.USUARIO_ERROR_INACTIVO);
		}
	}
}
