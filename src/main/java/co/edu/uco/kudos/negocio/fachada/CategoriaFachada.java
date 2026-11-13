package co.edu.uco.kudos.negocio.fachada;

import java.util.List;
import java.util.UUID;

import co.edu.uco.kudos.dto.CategoriaDTO;

public interface CategoriaFachada {

	CategoriaDTO crearCategoria(CategoriaDTO datos);

	CategoriaDTO modificarCategoria(UUID id, CategoriaDTO datos);

	CategoriaDTO activarCategoria(UUID id);

	CategoriaDTO desactivarCategoria(UUID id);

	List<CategoriaDTO> consultarCategorias(CategoriaDTO filtro);
}
