package co.edu.uco.kudos.negocio.negocio.reglas.impl.usuario;

import co.edu.uco.kudos.dominio.RolDominio;
import co.edu.uco.kudos.dominio.enums.CodigoRol;
import co.edu.uco.kudos.negocio.negocio.reglas.Regla;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.kudos.transversal.excepciones.KudosNegocioExcepcion;

public final class AsegurarRolPermiteRegistroRegla implements Regla<RolDominio> {

	private static final Regla<RolDominio> INSTANCIA = new AsegurarRolPermiteRegistroRegla();

	private AsegurarRolPermiteRegistroRegla() {
	}

	public static Regla<RolDominio> obtenerInstancia() {
		return INSTANCIA;
	}

	@Override
	public void ejecutar(RolDominio... datos) {
		var codigo = datos[0].getCodigo();
		if (codigo == CodigoRol.ADMINISTRADOR || codigo == CodigoRol.RECTOR || codigo == CodigoRol.NO_DEFINIDO) {
			throw KudosNegocioExcepcion.crear(CatalogoMensajes.Usuario.USUARIO_ERROR_ROL_NO_AUTORREGISTRO);
		}
	}
}
