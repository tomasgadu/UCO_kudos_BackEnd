package co.edu.uco.kudos.negocio.negocio.reglas.impl.publicacion;

import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.kudos.transversal.excepciones.KudosNegocioExcepcion;
import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;

public final class SanearContenidoPublicacionRegla {

	private static final String ETIQUETAS_HTML = "<[^>]*>";

	private SanearContenidoPublicacionRegla() {
	}

	public static String ejecutar(String contenido) {
		var saneado = UtilTexto.getUtilTexto().obtenerValorDefecto(contenido).replaceAll(ETIQUETAS_HTML, UtilTexto.VACIO)
				.trim();
		if (UtilTexto.getUtilTexto().esVacia(saneado)) {
			throw KudosNegocioExcepcion.crear(CatalogoMensajes.Publicacion.USUARIO_ERROR_CONTENIDO_OBLIGATORIO);
		}
		return saneado;
	}
}
