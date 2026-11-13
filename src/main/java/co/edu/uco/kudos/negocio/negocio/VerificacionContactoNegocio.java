package co.edu.uco.kudos.negocio.negocio;

import co.edu.uco.kudos.dominio.UsuarioDominio;
import co.edu.uco.kudos.dominio.enums.TipoVerificacion;

public interface VerificacionContactoNegocio {

	void generarVerificacion(UsuarioDominio usuario, TipoVerificacion tipo);

	void verificarCodigo(String correoInstitucional, TipoVerificacion tipo, String codigo);

	void reenviarCodigo(String correoInstitucional, TipoVerificacion tipo);
}
