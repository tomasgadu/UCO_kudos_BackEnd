package co.edu.uco.kudos.dao.datos.entidad.postgresql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.kudos.dao.datos.entidad.UsuarioDAO;
import co.edu.uco.kudos.dao.datos.entidad.SqlDAO;
import co.edu.uco.kudos.entidad.UsuarioEntidad;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.kudos.transversal.excepciones.KudosDatosExcepcion;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;
import co.edu.uco.kudos.transversal.utilitarios.UtilSQL;
import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class UsuarioPostgreSqlDAO extends SqlDAO implements UsuarioDAO {

	public UsuarioPostgreSqlDAO(Connection conexion) {
		super(conexion);
	}

	@Override
	public void crear(UsuarioEntidad entidad) {
		var sql = "INSERT INTO usuario (id_usuario, nombres, apellidos, correo_institucional, numero_celular, clave_hash, correo_verificado, celular_verificado, estado_cuenta, id_rol, fecha_registro) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (var sentencia = getConexion().prepareStatement(sql)) {
			sentencia.setObject(1, entidad.getId());
			sentencia.setString(2, entidad.getNombres());
			sentencia.setString(3, entidad.getApellidos());
			sentencia.setString(4, entidad.getCorreoInstitucional());
			sentencia.setString(5, entidad.getNumeroCelular());
			sentencia.setString(6, entidad.getClave());
			sentencia.setBoolean(7, entidad.isCorreoVerificado());
			sentencia.setBoolean(8, entidad.isCelularVerificado());
			sentencia.setString(9, entidad.getEstadoCuenta());
			sentencia.setObject(10, entidad.getRol().getId());
			sentencia.setTimestamp(11, UtilSQL.aTimestamp(entidad.getFechaRegistro()));
			sentencia.executeUpdate();
		} catch (SQLException excepcion) {
			throw KudosDatosExcepcion.crear(CatalogoMensajes.Datos.USUARIO_ERROR_CREANDO.formatted("Usuario"),
					excepcion.getMessage(), excepcion);
		}
	}
	@Override
	public void actualizar(UUID id, UsuarioEntidad entidad) {
		var sql = "UPDATE usuario SET nombres = ?, apellidos = ?, correo_institucional = ?, numero_celular = ?, clave_hash = ?, correo_verificado = ?, celular_verificado = ?, estado_cuenta = ?, id_rol = ?, fecha_registro = ? WHERE id_usuario = ?";
		try (var sentencia = getConexion().prepareStatement(sql)) {
			sentencia.setString(1, entidad.getNombres());
			sentencia.setString(2, entidad.getApellidos());
			sentencia.setString(3, entidad.getCorreoInstitucional());
			sentencia.setString(4, entidad.getNumeroCelular());
			sentencia.setString(5, entidad.getClave());
			sentencia.setBoolean(6, entidad.isCorreoVerificado());
			sentencia.setBoolean(7, entidad.isCelularVerificado());
			sentencia.setString(8, entidad.getEstadoCuenta());
			sentencia.setObject(9, entidad.getRol().getId());
			sentencia.setTimestamp(10, UtilSQL.aTimestamp(entidad.getFechaRegistro()));
			sentencia.setObject(11, id);
			sentencia.executeUpdate();
		} catch (SQLException excepcion) {
			throw KudosDatosExcepcion.crear(CatalogoMensajes.Datos.USUARIO_ERROR_ACTUALIZANDO.formatted("Usuario"),
					excepcion.getMessage(), excepcion);
		}
	}
	@Override
	public UsuarioEntidad consultarPorId(UUID id) {
		if (UtilUUID.esValorDefecto(id)) {
			return new UsuarioEntidad();
		}
		var filtro = new UsuarioEntidad();
		filtro.setId(id);
		var resultado = consultarPorFiltro(filtro);
		return resultado.isEmpty() ? new UsuarioEntidad() : resultado.get(0);
	}

	@Override
	public List<UsuarioEntidad> consultarTodos() {
		return consultarPorFiltro(new UsuarioEntidad());
	}

	@Override
	public List<UsuarioEntidad> consultarPorFiltro(UsuarioEntidad filtro) {
		var filtroTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(filtro, new UsuarioEntidad());
		var parametros = new ArrayList<Object>();
		var sql = new StringBuilder("SELECT t.id_usuario, t.nombres, t.apellidos, t.correo_institucional, t.numero_celular, t.clave_hash, t.correo_verificado, t.celular_verificado, t.estado_cuenta, t.id_rol, t.fecha_registro, j9.codigo AS id_rol_codigo, j9.nombre AS id_rol_nombre FROM usuario t LEFT JOIN rol j9 ON j9.id_rol = t.id_rol ");
		if (!UtilUUID.esValorDefecto(filtroTmp.getId())) {
			agregarCondicion(sql, parametros, "t.id_usuario = ?", filtroTmp.getId());
		}
		if (!UtilTexto.getUtilTexto().esVacia(filtroTmp.getCorreoInstitucional())) {
			agregarCondicion(sql, parametros, "t.correo_institucional = ?", filtroTmp.getCorreoInstitucional());
		}
		if (!UtilTexto.getUtilTexto().esVacia(filtroTmp.getNumeroCelular())) {
			agregarCondicion(sql, parametros, "t.numero_celular = ?", filtroTmp.getNumeroCelular());
		}
		if (!UtilTexto.getUtilTexto().esVacia(filtroTmp.getEstadoCuenta())) {
			agregarCondicion(sql, parametros, "t.estado_cuenta = ?", filtroTmp.getEstadoCuenta());
		}
		if (!UtilUUID.esValorDefecto(filtroTmp.getRol().getId())) {
			agregarCondicion(sql, parametros, "t.id_rol = ?", filtroTmp.getRol().getId());
		}
		sql.append(" ORDER BY t.nombres, t.apellidos");
		return consultar(sql.toString(), parametros);
	}

	private List<UsuarioEntidad> consultar(String sql, List<Object> parametros) {
		var resultado = new ArrayList<UsuarioEntidad>();
		try (var sentencia = getConexion().prepareStatement(sql)) {
			for (int posicion = 0; posicion < parametros.size(); posicion++) {
				sentencia.setObject(posicion + 1, parametros.get(posicion));
			}
			try (var filas = sentencia.executeQuery()) {
				while (filas.next()) {
					var entidad = new UsuarioEntidad();
					entidad.setId(filas.getObject("id_usuario", UUID.class));
					entidad.setNombres(filas.getString("nombres"));
					entidad.setApellidos(filas.getString("apellidos"));
					entidad.setCorreoInstitucional(filas.getString("correo_institucional"));
					entidad.setNumeroCelular(filas.getString("numero_celular"));
					entidad.setClave(filas.getString("clave_hash"));
					entidad.setCorreoVerificado(filas.getBoolean("correo_verificado"));
					entidad.setCelularVerificado(filas.getBoolean("celular_verificado"));
					entidad.setEstadoCuenta(filas.getString("estado_cuenta"));
					entidad.getRol().setId(filas.getObject("id_rol", UUID.class));
					entidad.getRol().setCodigo(filas.getString("id_rol_codigo"));
					entidad.getRol().setNombre(filas.getString("id_rol_nombre"));
					entidad.setFechaRegistro(UtilSQL.aFechaHora(filas.getTimestamp("fecha_registro")));
					resultado.add(entidad);
				}
			}
		} catch (SQLException excepcion) {
			throw KudosDatosExcepcion.crear(CatalogoMensajes.Datos.USUARIO_ERROR_CONSULTANDO.formatted("Usuario"),
					excepcion.getMessage(), excepcion);
		}
		return resultado;
	}
}
