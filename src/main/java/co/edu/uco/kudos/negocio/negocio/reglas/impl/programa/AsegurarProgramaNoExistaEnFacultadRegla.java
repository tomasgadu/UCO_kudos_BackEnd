package co.edu.uco.kudos.negocio.negocio.reglas.impl.programa;

import java.util.UUID;

import co.edu.uco.kudos.dao.factoria.DAOFactory;
import co.edu.uco.kudos.entidad.ProgramaAcademicoEntidad;
import co.edu.uco.kudos.negocio.negocio.reglas.Regla;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.kudos.transversal.excepciones.KudosNegocioExcepcion;

public final class AsegurarProgramaNoExistaEnFacultadRegla implements Regla<Object> {

	private static final Regla<Object> INSTANCIA = new AsegurarProgramaNoExistaEnFacultadRegla();

	private AsegurarProgramaNoExistaEnFacultadRegla() {
	}

	public static Regla<Object> obtenerInstancia() {
		return INSTANCIA;
	}

	@Override
	public void ejecutar(Object... datos) {
		var nombre = (String) datos[0];
		var idFacultad = (UUID) datos[1];
		var daoFactory = (DAOFactory) datos[2];

		var filtro = new ProgramaAcademicoEntidad();
		filtro.getFacultad().setId(idFacultad);

		var repetido = daoFactory.obtenerProgramaAcademicoDAO().consultarPorFiltro(filtro).stream()
				.anyMatch(programa -> programa.getNombre().equalsIgnoreCase(nombre));

		if (repetido) {
			throw KudosNegocioExcepcion.crear(CatalogoMensajes.ProgramaAcademico.USUARIO_ERROR_NOMBRE_EXISTE);
		}
	}
}
