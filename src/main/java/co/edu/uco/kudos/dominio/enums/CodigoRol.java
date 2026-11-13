package co.edu.uco.kudos.dominio.enums;

import java.util.EnumSet;
import java.util.Set;

public enum CodigoRol {

	ADMINISTRADOR(TipoAmbito.NINGUNO,
			EnumSet.of(Permiso.GESTIONAR_ESTRUCTURA, Permiso.GESTIONAR_CATEGORIAS, Permiso.GESTIONAR_PARAMETROS,
					Permiso.CONSULTAR_AUDITORIA)),
	RECTOR(TipoAmbito.NINGUNO, EnumSet.of(Permiso.GESTIONAR_CATEGORIAS, Permiso.VETAR, Permiso.REVISAR_REPORTES,
			Permiso.CONSULTAR_AUDITORIA)),
	COORDINADOR_ACADEMICO(TipoAmbito.NINGUNO, EnumSet.of(Permiso.GESTIONAR_CATEGORIAS, Permiso.VETAR,
			Permiso.REVISAR_REPORTES, Permiso.CONSULTAR_AUDITORIA)),
	COORDINADOR_FACULTAD(TipoAmbito.FACULTAD, EnumSet.noneOf(Permiso.class)),
	COORDINADOR_PROGRAMA(TipoAmbito.PROGRAMA, EnumSet.noneOf(Permiso.class)),
	DOCENTE(TipoAmbito.PROGRAMA, EnumSet.noneOf(Permiso.class)),
	ESTUDIANTE(TipoAmbito.PROGRAMA, EnumSet.noneOf(Permiso.class)),
	NO_DEFINIDO(TipoAmbito.NINGUNO, EnumSet.noneOf(Permiso.class));

	private final TipoAmbito ambito;
	private final Set<Permiso> permisos;

	CodigoRol(TipoAmbito ambito, Set<Permiso> permisos) {
		this.ambito = ambito;
		this.permisos = permisos;
	}

	public TipoAmbito getAmbito() {
		return ambito;
	}

	public boolean tienePermiso(Permiso permiso) {
		return permisos.contains(permiso);
	}

	public boolean esCargoUnico() {
		return this == RECTOR || this == COORDINADOR_ACADEMICO;
	}

	public static CodigoRol desdeTexto(String texto) {
		for (var valor : values()) {
			if (valor.name().equalsIgnoreCase(String.valueOf(texto).trim())) {
				return valor;
			}
		}
		return NO_DEFINIDO;
	}
}
