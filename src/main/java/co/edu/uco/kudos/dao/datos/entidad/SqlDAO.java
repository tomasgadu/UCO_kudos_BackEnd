package co.edu.uco.kudos.dao.datos.entidad;

import java.sql.Connection;

import co.edu.uco.kudos.transversal.utilitarios.UtilSQL;

public abstract class SqlDAO {

	private final Connection conexion;

	protected SqlDAO(Connection conexion) {
		UtilSQL.asegurarConexionAbierta(conexion);
		this.conexion = conexion;
	}

	protected Connection getConexion() {
		return conexion;
	}

	protected static void agregarCondicion(StringBuilder sql, java.util.List<Object> parametros, String condicion,
			Object valor) {
		sql.append(parametros.isEmpty() ? "WHERE " : "AND ").append(condicion).append(" ");
		parametros.add(valor);
	}
}
