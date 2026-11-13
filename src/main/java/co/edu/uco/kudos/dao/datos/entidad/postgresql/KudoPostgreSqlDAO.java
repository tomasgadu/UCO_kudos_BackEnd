package co.edu.uco.kudos.dao.datos.entidad.postgresql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.kudos.dao.datos.entidad.KudoDAO;
import co.edu.uco.kudos.dao.datos.entidad.SqlDAO;
import co.edu.uco.kudos.entidad.KudoEntidad;
import co.edu.uco.kudos.transversal.catalogo.CatalogoMensajes;
import co.edu.uco.kudos.transversal.excepciones.KudosDatosExcepcion;
import co.edu.uco.kudos.transversal.utilitarios.UtilObjeto;
import co.edu.uco.kudos.transversal.utilitarios.UtilSQL;
import co.edu.uco.kudos.transversal.utilitarios.UtilTexto;
import co.edu.uco.kudos.transversal.utilitarios.UtilUUID;

public final class KudoPostgreSqlDAO extends SqlDAO implements KudoDAO {

	private static final String SELECT = "SELECT p.id_publicacion, p.id_autor, p.contenido, p.fecha_creacion, p.estado, "
			+ "k.id_destinatario, k.id_categoria, a.nombres AS autor_nombres, a.apellidos AS autor_apellidos, "
			+ "d.nombres AS destinatario_nombres, d.apellidos AS destinatario_apellidos, c.nombre AS categoria_nombre "
			+ "FROM kudo k JOIN publicacion p ON p.id_publicacion = k.id_kudo "
			+ "JOIN usuario a ON a.id_usuario = p.id_autor JOIN usuario d ON d.id_usuario = k.id_destinatario "
			+ "JOIN categoria c ON c.id_categoria = k.id_categoria ";

	public KudoPostgreSqlDAO(Connection conexion) {
		super(conexion);
	}

	@Override
	public void crear(KudoEntidad entidad) {
		var sqlPublicacion = "INSERT INTO publicacion (id_publicacion, tipo, id_autor, contenido, fecha_creacion, estado) VALUES (?, 'KUDO', ?, ?, ?, ?)";
		var sqlKudo = "INSERT INTO kudo (id_kudo, id_destinatario, id_categoria) VALUES (?, ?, ?)";
		try (var publicacion = getConexion().prepareStatement(sqlPublicacion);
				var kudo = getConexion().prepareStatement(sqlKudo)) {
			publicacion.setObject(1, entidad.getId());
			publicacion.setObject(2, entidad.getAutor().getId());
			publicacion.setString(3, entidad.getContenido());
			publicacion.setTimestamp(4, UtilSQL.aTimestamp(entidad.getFechaCreacion()));
			publicacion.setString(5, entidad.getEstado());
			publicacion.executeUpdate();
			kudo.setObject(1, entidad.getId());
			kudo.setObject(2, entidad.getDestinatario().getId());
			kudo.setObject(3, entidad.getCategoria().getId());
			kudo.executeUpdate();
		} catch (SQLException excepcion) {
			throw KudosDatosExcepcion.crear(CatalogoMensajes.Datos.USUARIO_ERROR_CREANDO.formatted("Kudo"),
					excepcion.getMessage(), excepcion);
		}
	}

	@Override
	public void actualizar(UUID id, KudoEntidad entidad) {
		var sql = "UPDATE publicacion SET estado = ? WHERE id_publicacion = ?";
		try (var sentencia = getConexion().prepareStatement(sql)) {
			sentencia.setString(1, entidad.getEstado());
			sentencia.setObject(2, id);
			sentencia.executeUpdate();
		} catch (SQLException excepcion) {
			throw KudosDatosExcepcion.crear(CatalogoMensajes.Datos.USUARIO_ERROR_ACTUALIZANDO.formatted("Kudo"),
					excepcion.getMessage(), excepcion);
		}
	}

	@Override
	public KudoEntidad consultarPorId(UUID id) {
		if (UtilUUID.esValorDefecto(id)) {
			return new KudoEntidad();
		}
		var filtro = new KudoEntidad();
		filtro.setId(id);
		var resultado = consultarPorFiltro(filtro);
		return resultado.isEmpty() ? new KudoEntidad() : resultado.get(0);
	}

	@Override
	public List<KudoEntidad> consultarTodos() {
		return consultarPorFiltro(new KudoEntidad());
	}

	@Override
	public List<KudoEntidad> consultarPorFiltro(KudoEntidad filtro) {
		var filtroTmp = UtilObjeto.obtenerValorDefectoSiValorOriginalEsNulo(filtro, new KudoEntidad());
		var parametros = new ArrayList<Object>();
		var sql = new StringBuilder(SELECT);
		if (!UtilUUID.esValorDefecto(filtroTmp.getId())) {
			agregarCondicion(sql, parametros, "p.id_publicacion = ?", filtroTmp.getId());
		}
		if (!UtilUUID.esValorDefecto(filtroTmp.getAutor().getId())) {
			agregarCondicion(sql, parametros, "p.id_autor = ?", filtroTmp.getAutor().getId());
		}
		if (!UtilUUID.esValorDefecto(filtroTmp.getDestinatario().getId())) {
			agregarCondicion(sql, parametros, "k.id_destinatario = ?", filtroTmp.getDestinatario().getId());
		}
		if (!UtilUUID.esValorDefecto(filtroTmp.getCategoria().getId())) {
			agregarCondicion(sql, parametros, "k.id_categoria = ?", filtroTmp.getCategoria().getId());
		}
		if (!UtilTexto.getUtilTexto().esVacia(filtroTmp.getEstado())) {
			agregarCondicion(sql, parametros, "p.estado = ?", filtroTmp.getEstado());
		}
		sql.append(" ORDER BY p.fecha_creacion DESC");
		return consultar(sql.toString(), parametros);
	}

	private List<KudoEntidad> consultar(String sql, List<Object> parametros) {
		var resultado = new ArrayList<KudoEntidad>();
		try (var sentencia = getConexion().prepareStatement(sql)) {
			for (int posicion = 0; posicion < parametros.size(); posicion++) {
				sentencia.setObject(posicion + 1, parametros.get(posicion));
			}
			try (var filas = sentencia.executeQuery()) {
				while (filas.next()) {
					var entidad = new KudoEntidad();
					entidad.setId(filas.getObject("id_publicacion", UUID.class));
					entidad.getAutor().setId(filas.getObject("id_autor", UUID.class));
					entidad.getAutor().setNombres(filas.getString("autor_nombres"));
					entidad.getAutor().setApellidos(filas.getString("autor_apellidos"));
					entidad.setContenido(filas.getString("contenido"));
					entidad.setFechaCreacion(UtilSQL.aFechaHora(filas.getTimestamp("fecha_creacion")));
					entidad.setEstado(filas.getString("estado"));
					entidad.getDestinatario().setId(filas.getObject("id_destinatario", UUID.class));
					entidad.getDestinatario().setNombres(filas.getString("destinatario_nombres"));
					entidad.getDestinatario().setApellidos(filas.getString("destinatario_apellidos"));
					entidad.getCategoria().setId(filas.getObject("id_categoria", UUID.class));
					entidad.getCategoria().setNombre(filas.getString("categoria_nombre"));
					resultado.add(entidad);
				}
			}
		} catch (SQLException excepcion) {
			throw KudosDatosExcepcion.crear(CatalogoMensajes.Datos.USUARIO_ERROR_CONSULTANDO.formatted("Kudo"),
					excepcion.getMessage(), excepcion);
		}
		return resultado;
	}
}
