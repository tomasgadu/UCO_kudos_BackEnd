package co.edu.uco.kudos.negocio.fachada.ensamblador.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.kudos.dominio.KudoDominio;
import co.edu.uco.kudos.dominio.enums.EstadoPublicacion;
import co.edu.uco.kudos.dto.KudoDTO;
import co.edu.uco.kudos.negocio.fachada.ensamblador.EnsambladorDTO;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;

public final class KudoEnsambladorDTO implements EnsambladorDTO<KudoDominio, KudoDTO> {

	private static final EnsambladorDTO<KudoDominio, KudoDTO> INSTANCIA = new KudoEnsambladorDTO();

	private KudoEnsambladorDTO() {
	}

	public static EnsambladorDTO<KudoDominio, KudoDTO> getInstancia() {
		return INSTANCIA;
	}

	@Override
	public KudoDTO convertirADTO(KudoDominio dominio) {
		var origen = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(dominio, new KudoDominio.Builder().build());
		var destino = new KudoDTO();
		destino.setId(origen.getId());
		destino.setAutor(UsuarioEnsambladorDTO.getInstancia().convertirADTO(origen.getAutor()));
		destino.setContenido(origen.getContenido());
		destino.setFechaCreacion(origen.getFechaCreacion());
		destino.setEstado(origen.getEstado().name());
		destino.setDestinatario(UsuarioEnsambladorDTO.getInstancia().convertirADTO(origen.getDestinatario()));
		destino.setCategoria(CategoriaEnsambladorDTO.getInstancia().convertirADTO(origen.getCategoria()));
		return destino;
	}

	@Override
	public KudoDominio convertirADominio(KudoDTO dto) {
		var origen = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(dto, new KudoDTO());
		return new KudoDominio.Builder().id(origen.getId())
				.autor(UsuarioEnsambladorDTO.getInstancia().convertirADominio(origen.getAutor()))
				.contenido(origen.getContenido()).fechaCreacion(origen.getFechaCreacion())
				.estado(EstadoPublicacion.desdeTexto(origen.getEstado()))
				.destinatario(UsuarioEnsambladorDTO.getInstancia().convertirADominio(origen.getDestinatario()))
				.categoria(CategoriaEnsambladorDTO.getInstancia().convertirADominio(origen.getCategoria())).build();
	}

	@Override
	public List<KudoDTO> convertirADTO(List<KudoDominio> lista) {
		var listaTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(lista, new ArrayList<KudoDominio>());
		return listaTmp.stream().map(this::convertirADTO).toList();
	}
}
