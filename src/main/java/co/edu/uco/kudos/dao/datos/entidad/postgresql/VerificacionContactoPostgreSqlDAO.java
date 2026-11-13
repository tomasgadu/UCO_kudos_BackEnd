package co.edu.uco.kudos.dao.datos.entidad.postgresql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.kudos.dao.datos.entidad.VerificacionContactoDAO;
import co.edu.uco.kudos.dao.datos.entidad.SqlDAO;
import co.edu.uco.kudos.entidad.VerificacionContactoEntidad;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.kudos.transversal.excepciones.KudosDatosExcepcion;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;
import co.edu.uco.kudos.transversal.utilitarios.UtilSQL;
import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class VerificacionContactoPostgreSqlDAO extends SqlDAO implements VerificacionContactoDAO {

	public VerificacionContactoPostgreSqlDAO(Connection conexion) {
		super(conexion);
	}

	@Override
	public void crear(VerificacionContactoEntidad entidad) {
		var sql = "INSERT INTO verificacion_contacto (id_verificacion_contacto, id_usuario, tipo, valor_verificado, codigo, fecha_envio, fecha_expiracion, fecha_verificacion, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (var sentencia = getConexion().prepareStatement(sql)) {
			sentencia.setObject(1, entidad.getId());
			sentencia.setObject(2, entidad.getUsuario().getId());
			sentencia.setString(3, entidad.getTipo());
			sentencia.setString(4, entidad.getValorVerificado());
			sentencia.setString(5, entidad.getCodigo());
			sentencia.setTimestamp(6, UtilSQL.aTimestamp(entidad.getFechaEnvio()));
			sentencia.setTimestamp(7, UtilSQL.aTimestamp(entidad.getFechaExpiracion()));
			sentencia.setTimestamp(8, UtilSQL.aTimestamp(entidad.getFechaVerificacion()));
			sentencia.setString(9, entidad.getEstado());
			sentencia.executeUpdate();
		} catch (SQLException excepcion) {
			throw KudosDatosExcepcion.crear(CatalogoMensajes.Datos.USUARIO_ERROR_CREANDO.formatted("VerificacionContacto"),
					excepcion.getMessage(), excepcion);
		}
	}
	@Override
	public void actualizar(UUID id, VerificacionContactoEntidad entidad) {
		var sql = "UPDATE verificacion_contacto SET id_usuario = ?, tipo = ?, valor_verificado = ?, codigo = ?, fecha_envio = ?, fecha_expiracion = ?, fecha_verificacion = ?, estado = ? WHERE id_verificacion_contacto = ?";
		try (var sentencia = getConexion().prepareStatement(sql)) {
			sentencia.setObject(1, entidad.getUsuario().getId());
			sentencia.setString(2, entidad.getTipo());
			sentencia.setString(3, entidad.getValorVerificado());
			sentencia.setString(4, entidad.getCodigo());
			sentencia.setTimestamp(5, UtilSQL.aTimestamp(entidad.getFechaEnvio()));
			sentencia.setTimestamp(6, UtilSQL.aTimestamp(entidad.getFechaExpiracion()));
			sentencia.setTimestamp(7, UtilSQL.aTimestamp(entidad.getFechaVerificacion()));
			sentencia.setString(8, entidad.getEstado());
			sentencia.setObject(9, id);
			sentencia.executeUpdate();
		} catch (SQLException excepcion) {
			throw KudosDatosExcepcion.crear(CatalogoMensajes.Datos.USUARIO_ERROR_ACTUALIZANDO.formatted("VerificacionContacto"),
					excepcion.getMessage(), excepcion);
		}
	}
	@Override
	public VerificacionContactoEntidad consultarPorId(UUID id) {
		if (UtilUUID.esValorDefecto(id)) {
			return new VerificacionContactoEntidad();
		}
		var filtro = new VerificacionContactoEntidad();
		filtro.setId(id);
		var resultado = consultarPorFiltro(filtro);
		return resultado.isEmpty() ? new VerificacionContactoEntidad() : resultado.get(0);
	}

	@Override
	public List<VerificacionContactoEntidad> consultarTodos() {
		return consultarPorFiltro(new VerificacionContactoEntidad());
	}

	@Override
	public List<VerificacionContactoEntidad> consultarPorFiltro(VerificacionContactoEntidad filtro) {
		var filtroTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(filtro, new VerificacionContactoEntidad());
		var parametros = new ArrayList<Object>();
		var sql = new StringBuilder("SELECT t.id_verificacion_contacto, t.id_usuario, t.tipo, t.valor_verificado, t.codigo, t.fecha_envio, t.fecha_expiracion, t.fecha_verificacion, t.estado FROM verificacion_contacto t ");
		if (!UtilUUID.esValorDefecto(filtroTmp.getId())) {
			agregarCondicion(sql, parametros, "t.id_verificacion_contacto = ?", filtroTmp.getId());
		}
		if (!UtilUUID.esValorDefecto(filtroTmp.getUsuario().getId())) {
			agregarCondicion(sql, parametros, "t.id_usuario = ?", filtroTmp.getUsuario().getId());
		}
		if (!UtilTexto.getUtilTexto().esVacia(filtroTmp.getTipo())) {
			agregarCondicion(sql, parametros, "t.tipo = ?", filtroTmp.getTipo());
		}
		if (!UtilTexto.getUtilTexto().esVacia(filtroTmp.getEstado())) {
			agregarCondicion(sql, parametros, "t.estado = ?", filtroTmp.getEstado());
		}
		sql.append(" ORDER BY t.fecha_envio DESC");
		return consultar(sql.toString(), parametros);
	}

	private List<VerificacionContactoEntidad> consultar(String sql, List<Object> parametros) {
		var resultado = new ArrayList<VerificacionContactoEntidad>();
		try (var sentencia = getConexion().prepareStatement(sql)) {
			for (int posicion = 0; posicion < parametros.size(); posicion++) {
				sentencia.setObject(posicion + 1, parametros.get(posicion));
			}
			try (var filas = sentencia.executeQuery()) {
				while (filas.next()) {
					var entidad = new VerificacionContactoEntidad();
					entidad.setId(filas.getObject("id_verificacion_contacto", UUID.class));
					entidad.getUsuario().setId(filas.getObject("id_usuario", UUID.class));
					entidad.setTipo(filas.getString("tipo"));
					entidad.setValorVerificado(filas.getString("valor_verificado"));
					entidad.setCodigo(filas.getString("codigo"));
					entidad.setFechaEnvio(UtilSQL.aFechaHora(filas.getTimestamp("fecha_envio")));
					entidad.setFechaExpiracion(UtilSQL.aFechaHora(filas.getTimestamp("fecha_expiracion")));
					entidad.setFechaVerificacion(UtilSQL.aFechaHora(filas.getTimestamp("fecha_verificacion")));
					entidad.setEstado(filas.getString("estado"));
					resultado.add(entidad);
				}
			}
		} catch (SQLException excepcion) {
			throw KudosDatosExcepcion.crear(CatalogoMensajes.Datos.USUARIO_ERROR_CONSULTANDO.formatted("VerificacionContacto"),
					excepcion.getMessage(), excepcion);
		}
		return resultado;
	}
}
