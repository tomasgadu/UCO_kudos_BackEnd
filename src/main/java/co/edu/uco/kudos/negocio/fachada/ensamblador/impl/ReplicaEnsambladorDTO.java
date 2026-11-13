package co.edu.uco.kudos.negocio.fachada.ensamblador.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.kudos.dominio.KudoDominio;
import co.edu.uco.kudos.dominio.ReplicaDominio;
import co.edu.uco.kudos.dominio.enums.EstadoPublicacion;
import co.edu.uco.kudos.dto.ReplicaDTO;
import co.edu.uco.kudos.negocio.fachada.ensamblador.EnsambladorDTO;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;

public final class ReplicaEnsambladorDTO implements EnsambladorDTO<ReplicaDominio, ReplicaDTO> {

	private static final EnsambladorDTO<ReplicaDominio, ReplicaDTO> INSTANCIA = new ReplicaEnsambladorDTO();

	private ReplicaEnsambladorDTO() {
	}

	public static EnsambladorDTO<ReplicaDominio, ReplicaDTO> getInstancia() {
		return INSTANCIA;
	}

	@Override
	public ReplicaDTO convertirADTO(ReplicaDominio dominio) {
		var origen = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(dominio, new ReplicaDominio.Builder().build());
		var destino = new ReplicaDTO();
		destino.setId(origen.getId());
		destino.setAutor(UsuarioEnsambladorDTO.getInstancia().convertirADTO(origen.getAutor()));
		destino.setContenido(origen.getContenido());
		destino.setFechaCreacion(origen.getFechaCreacion());
		destino.setEstado(origen.getEstado().name());
		destino.setIdPadre(origen.getIdPadre());
		destino.setIdKudoRaiz(origen.getKudoRaiz().getId());
		return destino;
	}

	@Override
	public ReplicaDominio convertirADominio(ReplicaDTO dto) {
		var origen = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(dto, new ReplicaDTO());
		return new ReplicaDominio.Builder().id(origen.getId())
				.autor(UsuarioEnsambladorDTO.getInstancia().convertirADominio(origen.getAutor()))
				.contenido(origen.getContenido()).fechaCreacion(origen.getFechaCreacion())
				.estado(EstadoPublicacion.desdeTexto(origen.getEstado())).idPadre(origen.getIdPadre())
				.kudoRaiz(new KudoDominio.Builder().id(origen.getIdKudoRaiz()).build()).build();
	}

	@Override
	public List<ReplicaDTO> convertirADTO(List<ReplicaDominio> lista) {
		var listaTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(lista, new ArrayList<ReplicaDominio>());
		return listaTmp.stream().map(this::convertirADTO).toList();
	}
}
