package co.edu.uco.kudos.negocio.negocio.reglas;

public interface Regla<T> {

	@SuppressWarnings("unchecked")
	void ejecutar(T... datos);
}
