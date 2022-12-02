package rs.modelo;

import java.time.LocalDate;
import java.util.Objects;

import rs.util.Calendario;
/**
 * Relacion entre usuarios
 * @author Camacho, Cristian; Jaime,Cesar
 *
 */
public class Relacion {
	private Usuario usuario1;
	private Usuario usuario2;
	private int interaccion;
	private int likes;
	private LocalDate fechaAmistad;
	
	/**
	 * constructor de relacion
	 * @param usuario1
	 * @param usuario2
	 * @param interaccion entero que cuantifica la interaccion entre usuarios
	 * @param likes cantidad de likes en la relacion
	 * @param fechaAmistad fecha en que su relación fue creada
	 */
	public Relacion(Usuario usuario1, Usuario usuario2, int interaccion, int likes,LocalDate fechaAmistad) {
		super();
		this.usuario1 = usuario1;
		this.usuario2 = usuario2;
		this.interaccion = interaccion;
		this.likes = likes;
		this.fechaAmistad = fechaAmistad;
	}

	/**
	 * constructor relacion sin parametros
	 */
	public Relacion() {
		
	}

	/**
	 * obtiene usuario1
	 * @return usuario1
	 */
	public Usuario getUsuario1() {
		return usuario1;
	}

	/**
	 * establece usuario1
	 * @param usuario1
	 */
	public void setUsuario1(Usuario usuario1) {
		this.usuario1 = usuario1;
	}

	/**
	 * obtiene usuario2
	 * @return usuario2
	 */
	public Usuario getUsuario2() {
		return usuario2;
	}

	/**
	 * establece usuario2
	 * @param usuario2
	 */
	public void setUsuario2(Usuario usuario2) {
		this.usuario2 = usuario2;
	}

	/**
	 * obtiene interaccion de los usuarios
	 * @return entero que cuantifica interaccion
	 */
	public int getInteraccion() {
		return interaccion;
	}

	/**
	 * establece interaccion
	 * @param interaccion
	 */
	public void setInteraccion(int interaccion) {
		this.interaccion = interaccion;
	}

	/**
	 * obtiene cantidad de likes
	 * @return cantidad de likes
	 */
	public int getLikes() {
		return likes;
	}

	/**
	 * establece cantidad de likes
	 * @param likes
	 */
	public void setLikes(int likes) {
		this.likes = likes;
	}
	
	/**
	 * obtiene fecha que comenzó la relacion
	 * @return fecha
	 */
	public LocalDate getFechaAmistad() {
	
		return fechaAmistad;
	}


	/**
	 * establece fecha que comenzó la relación
	 * @param fechaAmistad
	 */
	public void setFechaAmistad(LocalDate fechaAmistad) {
		this.fechaAmistad = fechaAmistad;
	}
	
	/**
	 * obtiene el tiempo desde que comenzó la relacion
	 * @return
	 */
	public int getTiempoAmistad() {
        return Calendario.getTiempo(fechaAmistad);
	}

	/**
	 * toString relacion
	 */
	@Override
	public String toString() {
		return "Relacion ["+ usuario1 + " " + usuario2 + " " + interaccion + " "
				+ likes + " " + Calendario.getTiempo(fechaAmistad) + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(usuario1, usuario2);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Relacion other = (Relacion) obj;
		if((Objects.equals(usuario1, other.usuario1) && Objects.equals(usuario2, other.usuario2)))
			return true;
		return Objects.equals(usuario1, other.usuario2) && Objects.equals(usuario2, other.usuario1);
	}

}
