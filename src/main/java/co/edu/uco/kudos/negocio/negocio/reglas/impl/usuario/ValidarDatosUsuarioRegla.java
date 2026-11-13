package co.edu.uco.kudos.negocio.negocio.reglas.impl.usuario;

import co.edu.uco.kudos.dominio.UsuarioDominio;
import co.edu.uco.kudos.negocio.negocio.reglas.Regla;
import co.edu.uco.kudos.negocio.negocio.reglas.impl.ValidadorCampos;
import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;

public final class ValidarDatosUsuarioRegla implements Regla<Object> {

	private static final Regla<Object> INSTANCIA = new ValidarDatosUsuarioRegla();

	private ValidarDatosUsuarioRegla() {
	}

	public static Regla<Object> obtenerInstancia() {
		return INSTANCIA;
	}

	@Override
	public void ejecutar(Object... datos) {
		var usuario = (UsuarioDominio) datos[0];
		var esRegistro = (Boolean) datos[1];

		ValidadorCampos.validarTextoObligatorio(usuario.getNombres(), "nombres", 2, 60);
		ValidadorCampos.validarFormato(usuario.getNombres(), "nombres", UtilTexto.SOLO_LETRAS_ESPACIOS);
		ValidadorCampos.validarTextoObligatorio(usuario.getApellidos(), "apellidos", 2, 60);
		ValidadorCampos.validarFormato(usuario.getApellidos(), "apellidos", UtilTexto.SOLO_LETRAS_ESPACIOS);
		ValidadorCampos.validarTextoObligatorio(usuario.getCorreoInstitucional(), "correo institucional", 6, 100);
		ValidadorCampos.validarFormato(usuario.getCorreoInstitucional(), "correo institucional", UtilTexto.CORREO);
		ValidadorCampos.validarTextoObligatorio(usuario.getNumeroCelular(), "número celular", 10, 10);
		ValidadorCampos.validarFormato(usuario.getNumeroCelular(), "número celular", UtilTexto.CELULAR_COLOMBIA);

		if (Boolean.TRUE.equals(esRegistro)) {
			ValidadorCampos.validarTextoObligatorio(usuario.getClave(), "contraseña", 8, 64);
			ValidadorCampos.validarFormato(usuario.getClave(), "contraseña", UtilTexto.CLAVE_SEGURA);
			ValidadorCampos.validarIdentificadorObligatorio(usuario.getRol().getId(), "rol");
		}
	}
}
