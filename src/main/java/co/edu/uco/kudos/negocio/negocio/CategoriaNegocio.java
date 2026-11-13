package co.edu.uco.kudos.negocio.negocio;

import java.util.List;
import java.util.UUID;

import co.edu.uco.kudos.dominio.CategoriaDominio;

public interface CategoriaNegocio {

	CategoriaDominio crearCategoria(CategoriaDominio datos);

	CategoriaDominio modificarCategoria(UUID id, CategoriaDominio datos);

	CategoriaDominio activarCategoria(UUID id);

	CategoriaDominio desactivarCategoria(UUID id);

	List<CategoriaDominio> consultarCategorias(CategoriaDominio filtro);
}
