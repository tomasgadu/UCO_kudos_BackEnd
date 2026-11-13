package co.edu.uco.kudos.negocio.negocio;

import java.util.List;
import java.util.UUID;

import co.edu.uco.kudos.dominio.CategoriaDominio;

public interface CategoriaNegocio {

	CategoriaDominio crearCategoria(UUID idActor, CategoriaDominio datos);

	CategoriaDominio modificarCategoria(UUID idActor, UUID id, CategoriaDominio datos);

	CategoriaDominio activarCategoria(UUID idActor, UUID id);

	CategoriaDominio desactivarCategoria(UUID idActor, UUID id);

	List<CategoriaDominio> consultarCategorias(CategoriaDominio filtro);
}
