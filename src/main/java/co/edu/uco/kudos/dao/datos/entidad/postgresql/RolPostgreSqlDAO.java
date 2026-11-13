package co.edu.uco.kudos.dao.datos.entidad.postgresql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.kudos.dao.datos.entidad.RolDAO;
import co.edu.uco.kudos.dao.datos.entidad.SqlDAO;
import co.edu.uco.kudos.entidad.RolEntidad;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.kudos.transversal.excepciones.KudosDatosExcepcion;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;
import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class RolPostgreSqlDAO extends SqlDAO implements RolDAO {

	public RolPostgreSqlDAO(Connection conexion) {
		super(conexion);
	}

	@Override
	public RolEntidad consultarPorId(UUID id) {
		if (UtilUUID.esValorDefecto(id)) {
			return new RolEntidad();
		}
		var filtro = new RolEntidad();
		filtro.setId(id);
		var resultado = consultarPorFiltro(filtro);
		return resultado.isEmpty() ? new RolEntidad() : resultado.get(0);
	}

	@Override
	public List<RolEntidad> consultarTodos() {
		return consultarPorFiltro(new RolEntidad());
	}

	@Override
	public List<RolEntidad> consultarPorFiltro(RolEntidad filtro) {
		var filtroTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(filtro, new RolEntidad());
		var parametros = new ArrayList<Object>();
		var sql = new StringBuilder("SELECT t.id_rol, t.codigo, t.nombre, t.descripcion, t.id_rol_aprobador FROM rol t ");
		if (!UtilUUID.esValorDefecto(filtroTmp.getId())) {
			agregarCondicion(sql, parametros, "t.id_rol = ?", filtroTmp.getId());
		}
		if (!UtilTexto.getUtilTexto().esVacia(filtroTmp.getCodigo())) {
			agregarCondicion(sql, parametros, "t.codigo = ?", filtroTmp.getCodigo());
		}
		if (!UtilTexto.getUtilTexto().esVacia(filtroTmp.getNombre())) {
			agregarCondicion(sql, parametros, "t.nombre = ?", filtroTmp.getNombre());
		}
		if (!UtilUUID.esValorDefecto(filtroTmp.getIdRolAprobador())) {
			agregarCondicion(sql, parametros, "t.id_rol_aprobador = ?", filtroTmp.getIdRolAprobador());
		}
		sql.append(" ORDER BY t.nombre");
		return consultar(sql.toString(), parametros);
	}

	private List<RolEntidad> consultar(String sql, List<Object> parametros) {
		var resultado = new ArrayList<RolEntidad>();
		try (var sentencia = getConexion().prepareStatement(sql)) {
			for (int posicion = 0; posicion < parametros.size(); posicion++) {
				sentencia.setObject(posicion + 1, parametros.get(posicion));
			}
			try (var filas = sentencia.executeQuery()) {
				while (filas.next()) {
					var entidad = new RolEntidad();
					entidad.setId(filas.getObject("id_rol", UUID.class));
					entidad.setCodigo(filas.getString("codigo"));
					entidad.setNombre(filas.getString("nombre"));
					entidad.setDescripcion(filas.getString("descripcion"));
					entidad.setIdRolAprobador(filas.getObject("id_rol_aprobador", UUID.class));
					resultado.add(entidad);
				}
			}
		} catch (SQLException excepcion) {
			throw KudosDatosExcepcion.crear(CatalogoMensajes.Datos.USUARIO_ERROR_CONSULTANDO.formatted("Rol"),
					excepcion.getMessage(), excepcion);
		}
		return resultado;
	}
}
