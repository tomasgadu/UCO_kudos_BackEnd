package co.edu.uco.kudos.negocio.negocio.reglas;

import java.text.MessageFormat;

import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.kudos.transversal.excepciones.KudosNegocioExcepcion;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;

public interface Regla<T> {

	@SuppressWarnings("unchecked")
	void ejecutar(T... datos);

	static void validarCantidadDatos(Object[] datos, int cantidad) {
		var recibidos = UtilObjeto.esNulo(datos) ? 0 : datos.length;
		if (recibidos < cantidad) {
			var mensajeTecnico = MessageFormat.format(CatalogoMensajes.General.TECNICO_ERROR_CANTIDAD_DATOS_REGLA,
					cantidad, recibidos);
			throw KudosNegocioExcepcion.crear(CatalogoMensajes.General.USUARIO_ERROR_NO_CONTROLADO, mensajeTecnico);
		}
	}
}
