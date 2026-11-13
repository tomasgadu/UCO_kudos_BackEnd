package co.edu.uco.kudos.negocio.fachada;

import java.util.List;
import java.util.UUID;

import co.edu.uco.kudos.dto.CategoriaDTO;

public interface CategoriaFachada {

	CategoriaDTO crearCategoria(UUID idActor, CategoriaDTO datos);

	CategoriaDTO modificarCategoria(UUID idActor, UUID id, CategoriaDTO datos);

	CategoriaDTO activarCategoria(UUID idActor, UUID id);

	CategoriaDTO desactivarCategoria(UUID idActor, UUID id);

	List<CategoriaDTO> consultarCategorias(CategoriaDTO filtro);
}
