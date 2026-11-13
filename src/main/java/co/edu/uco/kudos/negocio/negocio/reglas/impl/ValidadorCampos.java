package co.edu.uco.kudos.negocio.negocio.reglas.impl;

import java.util.UUID;

import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.kudos.transversal.excepciones.KudosNegocioExcepcion;
import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class ValidadorCampos {

	private ValidadorCampos() {
	}

	public static void validarTextoObligatorio(String valor, String campo, int minimo, int maximo) {
		if (UtilTexto.getUtilTexto().esVacia(valor)) {
			throw KudosNegocioExcepcion.crear(CatalogoMensajes.Validacion.USUARIO_ERROR_CAMPO_OBLIGATORIO.formatted(campo));
		}
		validarLongitud(valor, campo, minimo, maximo);
	}

	public static void validarTextoOpcional(String valor, String campo, int minimo, int maximo) {
		if (!UtilTexto.getUtilTexto().esVacia(valor)) {
			validarLongitud(valor, campo, minimo, maximo);
		}
	}

	public static void validarFormato(String valor, String campo, String patron) {
		if (!UtilTexto.getUtilTexto().formatoEsValido(valor, patron)) {
			throw KudosNegocioExcepcion.crear(CatalogoMensajes.Validacion.USUARIO_ERROR_CAMPO_FORMATO.formatted(campo));
		}
	}

	public static void validarIdentificadorObligatorio(UUID valor, String campo) {
		if (UtilUUID.esValorDefecto(valor)) {
			throw KudosNegocioExcepcion.crear(CatalogoMensajes.Validacion.USUARIO_ERROR_CAMPO_OBLIGATORIO.formatted(campo));
		}
	}

	private static void validarLongitud(String valor, String campo, int minimo, int maximo) {
		if (!UtilTexto.getUtilTexto().obtenerLongitudCadenaEsValida(valor, minimo, maximo, true)) {
			throw KudosNegocioExcepcion
					.crear(CatalogoMensajes.Validacion.USUARIO_ERROR_CAMPO_LONGITUD.formatted(campo, minimo, maximo));
		}
	}
}
