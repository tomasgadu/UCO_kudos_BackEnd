package co.edu.uco.kudos.dto;

public final class ResumenCalificacionDTO {

	private double promedio;
	private int cantidad;
	private int miCalificacion;

	public ResumenCalificacionDTO() {
	}

	public ResumenCalificacionDTO(double promedio, int cantidad, int miCalificacion) {
		this.promedio = promedio;
		this.cantidad = cantidad;
		this.miCalificacion = miCalificacion;
	}

	public double getPromedio() {
		return promedio;
	}

	public void setPromedio(double promedio) {
		this.promedio = promedio;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public int getMiCalificacion() {
		return miCalificacion;
	}

	public void setMiCalificacion(int miCalificacion) {
		this.miCalificacion = miCalificacion;
	}
}
