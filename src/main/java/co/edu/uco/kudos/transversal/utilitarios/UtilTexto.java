package co.edu.uco.kudos.transversal.utilitarios;

public final class UtilTexto {

	private static UtilTexto instancia;
	public static final String VACIO = "";
	public static final String SOLO_LETRAS_ESPACIOS = "^[a-zA-Z ñÑáÁéÉíÍóÓúÚüÜ]*$";
	public static final String LETRAS_DIGITOS = "^[a-zA-Z0-9]*$";
	public static final String SOLO_DIGITOS = "^[0-9]*$";
	public static final String CORREO = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
	public static final String CELULAR_COLOMBIA = "^3[0-9]{9}$";
	public static final String CLAVE_SEGURA = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).*$";
	public static final String CODIGO_VERIFICACION = "^[0-9]{6}$";

	private UtilTexto() {
	}

	public static UtilTexto getUtilTexto() {
		if (UtilObjeto.esNulo(instancia)) {
			synchronized (UtilTexto.class) {
				if (UtilObjeto.esNulo(instancia)) {
					instancia = new UtilTexto();
				}
			}
		}
		return instancia;
	}

	public boolean esNula(String cadena) {
		return UtilObjeto.esNulo(cadena);
	}

	public boolean esVacia(String cadena) {
		return VACIO.equals(quitarEspacioEnBlanco(cadena));
	}

	public String obtenerValorDefecto(String valor, String valorDefecto) {
		return UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(valor, valorDefecto);
	}

	public String obtenerValorDefecto(String valor) {
		return obtenerValorDefecto(valor, VACIO);
	}

	public String quitarEspacioEnBlanco(String valor) {
		return obtenerValorDefecto(valor).trim();
	}

	public String aplicarMayusculaInicial(String valor) {
		var texto = quitarEspacioEnBlanco(valor);
		if (esVacia(texto)) {
			return texto;
		}
		var palabras = texto.toLowerCase().split("\\s+");
		var resultado = new StringBuilder();
		for (var palabra : palabras) {
			if (!resultado.isEmpty()) {
				resultado.append(" ");
			}
			resultado.append(palabra.substring(0, 1).toUpperCase()).append(palabra.substring(1));
		}
		return resultado.toString();
	}

	public int obtenerLongitudCadena(String valor) {
		return obtenerValorDefecto(valor).length();
	}

	public int obtenerLongitudCadena(String valor, boolean quitarEspaciosBlanco) {
		return quitarEspaciosBlanco ? obtenerLongitudCadena(quitarEspacioEnBlanco(valor))
				: obtenerLongitudCadena(valor);
	}

	public boolean obtenerLongitudCadenaEsValida(String valor, int longitudInicial, int longitudFinal,
			boolean quitarEspaciosBlanco) {
		var longitud = obtenerLongitudCadena(valor, quitarEspaciosBlanco);
		return longitud >= longitudInicial && longitud <= longitudFinal;
	}

	public boolean formatoEsValido(String valor, String patron) {
		return obtenerValorDefecto(valor).matches(obtenerValorDefecto(patron));
	}
}
