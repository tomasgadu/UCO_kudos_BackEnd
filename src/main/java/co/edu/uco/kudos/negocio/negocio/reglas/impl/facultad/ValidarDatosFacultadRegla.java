package co.edu.uco.kudos.negocio.negocio.reglas.impl.facultad;

import co.edu.uco.kudos.dominio.FacultadDominio;
import co.edu.uco.kudos.negocio.negocio.reglas.Regla;
import co.edu.uco.kudos.negocio.negocio.reglas.impl.ValidadorCampos;

public final class ValidarDatosFacultadRegla implements Regla<FacultadDominio> {

	private static final Regla<FacultadDominio> INSTANCIA = new ValidarDatosFacultadRegla();

	private ValidarDatosFacultadRegla() {
	}

	public static Regla<FacultadDominio> obtenerInstancia() {
		return INSTANCIA;
	}

	@Override
	public void ejecutar(FacultadDominio... datos) {
		ValidadorCampos.validarTextoObligatorio(datos[0].getNombre(), "nombre de la facultad", 3, 100);
	}
}
