package co.edu.uco.kudos.negocio.negocio.reglas.impl.programa;

import co.edu.uco.kudos.dominio.ProgramaAcademicoDominio;
import co.edu.uco.kudos.negocio.negocio.reglas.Regla;
import co.edu.uco.kudos.negocio.negocio.reglas.impl.ValidadorCampos;

public final class ValidarDatosProgramaAcademicoRegla implements Regla<ProgramaAcademicoDominio> {

	private static final Regla<ProgramaAcademicoDominio> INSTANCIA = new ValidarDatosProgramaAcademicoRegla();

	private ValidarDatosProgramaAcademicoRegla() {
	}

	public static Regla<ProgramaAcademicoDominio> obtenerInstancia() {
		return INSTANCIA;
	}

	@Override
	public void ejecutar(ProgramaAcademicoDominio... datos) {
		var programa = datos[0];
		ValidadorCampos.validarTextoObligatorio(programa.getNombre(), "nombre del programa", 3, 100);
		ValidadorCampos.validarIdentificadorObligatorio(programa.getFacultad().getId(), "facultad");
	}
}
