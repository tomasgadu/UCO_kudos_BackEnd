package co.edu.uco.kudos.negocio.fachada.impl;

import java.util.List;
import java.util.UUID;

import co.edu.uco.kudos.dto.CategoriaDTO;
import co.edu.uco.kudos.negocio.fachada.CategoriaFachada;
import co.edu.uco.kudos.negocio.fachada.FachadaBase;
import co.edu.uco.kudos.negocio.fachada.ensamblador.impl.CategoriaEnsambladorDTO;
import co.edu.uco.kudos.negocio.negocio.impl.CategoriaNegocioImpl;

public final class CategoriaFachadaImpl extends FachadaBase implements CategoriaFachada {

	@Override
	public CategoriaDTO crearCategoria(UUID idActor, CategoriaDTO datos) {
		return ejecutarEnTransaccion("crear la categoría", daoFactory -> {
			var dominio = CategoriaEnsambladorDTO.getInstancia().convertirADominio(datos);
			var creada = new CategoriaNegocioImpl(daoFactory).crearCategoria(idActor, dominio);
			return CategoriaEnsambladorDTO.getInstancia().convertirADTO(creada);
		});
	}

	@Override
	public CategoriaDTO modificarCategoria(UUID idActor, UUID id, CategoriaDTO datos) {
		return ejecutarEnTransaccion("modificar la categoría", daoFactory -> {
			var dominio = CategoriaEnsambladorDTO.getInstancia().convertirADominio(datos);
			var modificada = new CategoriaNegocioImpl(daoFactory).modificarCategoria(idActor, id, dominio);
			return CategoriaEnsambladorDTO.getInstancia().convertirADTO(modificada);
		});
	}

	@Override
	public CategoriaDTO activarCategoria(UUID idActor, UUID id) {
		return ejecutarEnTransaccion("activar la categoría", daoFactory -> CategoriaEnsambladorDTO.getInstancia()
				.convertirADTO(new CategoriaNegocioImpl(daoFactory).activarCategoria(idActor, id)));
	}

	@Override
	public CategoriaDTO desactivarCategoria(UUID idActor, UUID id) {
		return ejecutarEnTransaccion("desactivar la categoría", daoFactory -> CategoriaEnsambladorDTO.getInstancia()
				.convertirADTO(new CategoriaNegocioImpl(daoFactory).desactivarCategoria(idActor, id)));
	}

	@Override
	public List<CategoriaDTO> consultarCategorias(CategoriaDTO filtro) {
		return ejecutarConsulta("consultar las categorías", daoFactory -> {
			var dominio = CategoriaEnsambladorDTO.getInstancia().convertirADominio(filtro);
			return CategoriaEnsambladorDTO.getInstancia()
					.convertirADTO(new CategoriaNegocioImpl(daoFactory).consultarCategorias(dominio));
		});
	}
}
