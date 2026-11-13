package co.edu.uco.kudos.negocio.negocio.reglas.impl.categoria;

import co.edu.uco.kudos.dominio.CategoriaDominio;
import co.edu.uco.kudos.negocio.negocio.reglas.Regla;
import co.edu.uco.kudos.negocio.negocio.reglas.impl.ValidadorCampos;

public final class ValidarDatosCategoriaRegla implements Regla<CategoriaDominio> {

	private static final Regla<CategoriaDominio> INSTANCIA = new ValidarDatosCategoriaRegla();

	private ValidarDatosCategoriaRegla() {
	}

	public static Regla<CategoriaDominio> obtenerInstancia() {
		return INSTANCIA;
	}

	@Override
	public void ejecutar(CategoriaDominio... datos) {
		var categoria = datos[0];
		ValidadorCampos.validarTextoObligatorio(categoria.getNombre(), "nombre de la categoría", 3, 60);
		ValidadorCampos.validarTextoObligatorio(categoria.getDescripcion(), "descripción de la categoría", 10, 250);
	}
}
