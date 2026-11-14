package co.edu.uco.kudos.transversal.utilitarios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import co.edu.uco.kudos.dominio.enums.EstadoRegistro;

class UtilTextoTest {

	private final UtilTexto texto = UtilTexto.getUtilTexto();

	@Test
	@DisplayName("Aplica mayúscula inicial a cada palabra y normaliza espacios")
	void mayusculaInicial() {
		assertEquals("Laura María", texto.aplicarMayusculaInicial("  laura   MARÍA "));
	}

	@ParameterizedTest(name = "\"{0}\" devuelve vacío")
	@NullSource
	@ValueSource(strings = { "", "   " })
	@DisplayName("Un texto nulo o vacío devuelve vacío sin NullPointerException")
	void mayusculaInicialVacio(String valor) {
		assertEquals(UtilTexto.VACIO, texto.aplicarMayusculaInicial(valor));
	}

	@Test
	@DisplayName("Limpia los saltos de línea antes de escribir en el log")
	void limpiarParaRegistro() {
		assertEquals("correo_LINEA FALSA", texto.limpiarParaRegistro("correo\nLINEA FALSA"));
		assertEquals("a_b", texto.limpiarParaRegistro("a\rb"));
		assertEquals(UtilTexto.VACIO, texto.limpiarParaRegistro(null));
	}

	@Test
	@DisplayName("Un enum NO_DEFINIDO se convierte en texto vacío para no filtrar por él")
	void textoDeEnum() {
		assertEquals("ACTIVA", texto.textoDeEnum(EstadoRegistro.ACTIVA));
		assertEquals(UtilTexto.VACIO, texto.textoDeEnum(EstadoRegistro.NO_DEFINIDO));
		assertEquals(UtilTexto.VACIO, texto.textoDeEnum(null));
	}

	@Test
	@DisplayName("El correo debe pertenecer a alguno de los dominios permitidos")
	void correoEnDominiosPermitidos() {
		assertTrue(texto.correoPerteneceADominios("tomas.gallo@uco.edu.co", "uco.edu.co,gmail.com"));
		assertTrue(texto.correoPerteneceADominios("Tomas.Gallo@Gmail.com", "uco.edu.co, gmail.com"));
		assertFalse(texto.correoPerteneceADominios("tomas@hotmail.com", "uco.edu.co,gmail.com"));
		assertFalse(texto.correoPerteneceADominios(null, "uco.edu.co"));
	}

	@Test
	@DisplayName("Valida el formato del correo")
	void formatoCorreo() {
		assertTrue(texto.formatoEsValido("laura.gomez@uco.edu.co", UtilTexto.CORREO));
		assertFalse(texto.formatoEsValido("laura.gomez", UtilTexto.CORREO));
	}

	@Test
	@DisplayName("La clave segura exige minúscula, mayúscula y número")
	void claveSegura() {
		assertTrue(texto.formatoEsValido("Kudos2026*", UtilTexto.CLAVE_SEGURA));
		assertFalse(texto.formatoEsValido("kudos2026", UtilTexto.CLAVE_SEGURA));
	}
}
