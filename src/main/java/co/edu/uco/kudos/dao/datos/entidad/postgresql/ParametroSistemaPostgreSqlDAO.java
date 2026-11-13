package co.edu.uco.kudos.dao.datos.entidad.postgresql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.kudos.dao.datos.entidad.ParametroSistemaDAO;
import co.edu.uco.kudos.dao.datos.entidad.SqlDAO;
import co.edu.uco.kudos.entidad.ParametroSistemaEntidad;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.kudos.transversal.excepciones.KudosDatosExcepcion;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;
import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class ParametroSistemaPostgreSqlDAO extends SqlDAO implements ParametroSistemaDAO {

	public ParametroSistemaPostgreSqlDAO(Connection conexion) {
		super(conexion);
	}

	@Override
	public void crear(ParametroSistemaEntidad entidad) {
		var sql = "INSERT INTO parametro_sistema (id_parametro_sistema, clave, valor, descripcion) VALUES (?, ?, ?, ?)";
		try (var sentencia = getConexion().prepareStatement(sql)) {
			sentencia.setObject(1, entidad.getId());
			sentencia.setString(2, entidad.getClave());
			sentencia.setString(3, entidad.getValor());
			sentencia.setString(4, entidad.getDescripcion());
			sentencia.executeUpdate();
		} catch (SQLException excepcion) {
			throw KudosDatosExcepcion.crear(CatalogoMensajes.Datos.USUARIO_ERROR_CREANDO.formatted("ParametroSistema"),
					excepcion.getMessage(), excepcion);
		}
	}
	@Override
	public void actualizar(UUID id, ParametroSistemaEntidad entidad) {
		var sql = "UPDATE parametro_sistema SET clave = ?, valor = ?, descripcion = ? WHERE id_parametro_sistema = ?";
		try (var sentencia = getConexion().prepareStatement(sql)) {
			sentencia.setString(1, entidad.getClave());
			sentencia.setString(2, entidad.getValor());
			sentencia.setString(3, entidad.getDescripcion());
			sentencia.setObject(4, id);
			sentencia.executeUpdate();
		} catch (SQLException excepcion) {
			throw KudosDatosExcepcion.crear(CatalogoMensajes.Datos.USUARIO_ERROR_ACTUALIZANDO.formatted("ParametroSistema"),
					excepcion.getMessage(), excepcion);
		}
	}
	@Override
	public ParametroSistemaEntidad consultarPorId(UUID id) {
		if (UtilUUID.esValorDefecto(id)) {
			return new ParametroSistemaEntidad();
		}
		var filtro = new ParametroSistemaEntidad();
		filtro.setId(id);
		var resultado = consultarPorFiltro(filtro);
		return resultado.isEmpty() ? new ParametroSistemaEntidad() : resultado.get(0);
	}

	@Override
	public List<ParametroSistemaEntidad> consultarTodos() {
		return consultarPorFiltro(new ParametroSistemaEntidad());
	}

	@Override
	public List<ParametroSistemaEntidad> consultarPorFiltro(ParametroSistemaEntidad filtro) {
		var filtroTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(filtro, new ParametroSistemaEntidad());
		var parametros = new ArrayList<Object>();
		var sql = new StringBuilder("SELECT t.id_parametro_sistema, t.clave, t.valor, t.descripcion FROM parametro_sistema t ");
		if (!UtilUUID.esValorDefecto(filtroTmp.getId())) {
			agregarCondicion(sql, parametros, "t.id_parametro_sistema = ?", filtroTmp.getId());
		}
		if (!UtilTexto.getUtilTexto().esVacia(filtroTmp.getClave())) {
			agregarCondicion(sql, parametros, "t.clave = ?", filtroTmp.getClave());
		}
		sql.append(" ORDER BY t.clave");
		return consultar(sql.toString(), parametros);
	}

	private List<ParametroSistemaEntidad> consultar(String sql, List<Object> parametros) {
		var resultado = new ArrayList<ParametroSistemaEntidad>();
		try (var sentencia = getConexion().prepareStatement(sql)) {
			for (int posicion = 0; posicion < parametros.size(); posicion++) {
				sentencia.setObject(posicion + 1, parametros.get(posicion));
			}
			try (var filas = sentencia.executeQuery()) {
				while (filas.next()) {
					var entidad = new ParametroSistemaEntidad();
					entidad.setId(filas.getObject("id_parametro_sistema", UUID.class));
					entidad.setClave(filas.getString("clave"));
					entidad.setValor(filas.getString("valor"));
					entidad.setDescripcion(filas.getString("descripcion"));
					resultado.add(entidad);
				}
			}
		} catch (SQLException excepcion) {
			throw KudosDatosExcepcion.crear(CatalogoMensajes.Datos.USUARIO_ERROR_CONSULTANDO.formatted("ParametroSistema"),
					excepcion.getMessage(), excepcion);
		}
		return resultado;
	}
}
