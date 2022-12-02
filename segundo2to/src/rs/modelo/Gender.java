package rs.modelo;

/**
 * Enumeracion de generos
 * @author Camacho, Cristia; Jaime,Cesar
 *
 */
public enum Gender {
	M("mujer"), H("hombre"), NB ("no binario");

	private String genero;

	Gender(String genero) {
		this.genero = genero;
	}

	/**
	 * obtiene genero
	 * @return genero
	 */
	public String getGenero() {
		return genero;
	}
}
