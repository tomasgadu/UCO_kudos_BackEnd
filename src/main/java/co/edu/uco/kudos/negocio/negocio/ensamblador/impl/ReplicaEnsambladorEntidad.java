package co.edu.uco.kudos.negocio.negocio.ensamblador.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.kudos.dominio.KudoDominio;
import co.edu.uco.kudos.dominio.ReplicaDominio;
import co.edu.uco.kudos.dominio.enums.EstadoPublicacion;
import co.edu.uco.kudos.entidad.ReplicaEntidad;
import co.edu.uco.kudos.negocio.negocio.ensamblador.EnsambladorEntidad;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;
import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;

public final class ReplicaEnsambladorEntidad implements EnsambladorEntidad<ReplicaDominio, ReplicaEntidad> {

	private static final EnsambladorEntidad<ReplicaDominio, ReplicaEntidad> INSTANCIA = new ReplicaEnsambladorEntidad();

	private ReplicaEnsambladorEntidad() {
	}

	public static EnsambladorEntidad<ReplicaDominio, ReplicaEntidad> getInstancia() {
		return INSTANCIA;
	}

	@Override
	public ReplicaEntidad convertirAEntidad(ReplicaDominio dominio) {
		var origen = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(dominio, new ReplicaDominio.Builder().build());
		var destino = new ReplicaEntidad();
		destino.setId(origen.getId());
		destino.setAutor(UsuarioEnsambladorEntidad.getInstancia().convertirAEntidad(origen.getAutor()));
		destino.setContenido(origen.getContenido());
		destino.setFechaCreacion(origen.getFechaCreacion());
		destino.setEstado(UtilTexto.getUtilTexto().textoDeEnum(origen.getEstado()));
		destino.setIdPadre(origen.getIdPadre());
		destino.setIdKudoRaiz(origen.getKudoRaiz().getId());
		return destino;
	}

	@Override
	public ReplicaDominio convertirADominio(ReplicaEntidad entidad) {
		var origen = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(entidad, new ReplicaEntidad());
		return new ReplicaDominio.Builder().id(origen.getId())
				.autor(UsuarioEnsambladorEntidad.getInstancia().convertirADominio(origen.getAutor()))
				.contenido(origen.getContenido()).fechaCreacion(origen.getFechaCreacion())
				.estado(EstadoPublicacion.desdeTexto(origen.getEstado())).idPadre(origen.getIdPadre())
				.kudoRaiz(new KudoDominio.Builder().id(origen.getIdKudoRaiz()).build()).build();
	}

	@Override
	public List<ReplicaDominio> convertirADominio(List<ReplicaEntidad> lista) {
		var listaTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(lista, new ArrayList<ReplicaEntidad>());
		return listaTmp.stream().map(this::convertirADominio).toList();
	}
}
