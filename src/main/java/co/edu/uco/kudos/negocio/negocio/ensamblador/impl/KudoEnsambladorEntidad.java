package co.edu.uco.kudos.negocio.negocio.ensamblador.impl;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.kudos.dominio.KudoDominio;
import co.edu.uco.kudos.dominio.enums.EstadoPublicacion;
import co.edu.uco.kudos.entidad.KudoEntidad;
import co.edu.uco.kudos.negocio.negocio.ensamblador.EnsambladorEntidad;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;
import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;

public final class KudoEnsambladorEntidad implements EnsambladorEntidad<KudoDominio, KudoEntidad> {

	private static final EnsambladorEntidad<KudoDominio, KudoEntidad> INSTANCIA = new KudoEnsambladorEntidad();

	private KudoEnsambladorEntidad() {
	}

	public static EnsambladorEntidad<KudoDominio, KudoEntidad> getInstancia() {
		return INSTANCIA;
	}

	@Override
	public KudoEntidad convertirAEntidad(KudoDominio dominio) {
		var origen = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(dominio, new KudoDominio.Builder().build());
		var destino = new KudoEntidad();
		destino.setId(origen.getId());
		destino.setAutor(UsuarioEnsambladorEntidad.getInstancia().convertirAEntidad(origen.getAutor()));
		destino.setContenido(origen.getContenido());
		destino.setFechaCreacion(origen.getFechaCreacion());
		destino.setEstado(UtilTexto.getUtilTexto().textoDeEnum(origen.getEstado()));
		destino.setDestinatario(UsuarioEnsambladorEntidad.getInstancia().convertirAEntidad(origen.getDestinatario()));
		destino.setCategoria(CategoriaEnsambladorEntidad.getInstancia().convertirAEntidad(origen.getCategoria()));
		return destino;
	}

	@Override
	public KudoDominio convertirADominio(KudoEntidad entidad) {
		var origen = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(entidad, new KudoEntidad());
		return new KudoDominio.Builder().id(origen.getId())
				.autor(UsuarioEnsambladorEntidad.getInstancia().convertirADominio(origen.getAutor()))
				.contenido(origen.getContenido()).fechaCreacion(origen.getFechaCreacion())
				.estado(EstadoPublicacion.desdeTexto(origen.getEstado()))
				.destinatario(UsuarioEnsambladorEntidad.getInstancia().convertirADominio(origen.getDestinatario()))
				.categoria(CategoriaEnsambladorEntidad.getInstancia().convertirADominio(origen.getCategoria())).build();
	}

	@Override
	public List<KudoDominio> convertirADominio(List<KudoEntidad> lista) {
		var listaTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(lista, new ArrayList<KudoEntidad>());
		return listaTmp.stream().map(this::convertirADominio).toList();
	}
}
