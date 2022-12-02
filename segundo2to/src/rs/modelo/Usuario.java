package rs.modelo;

import java.time.LocalDate;
import java.util.Objects;

import rs.util.Calendario;

/**
 * Clase usuario
 * @author Jaime, Cesar; Camacho, Cristian
 *
 */
public class Usuario {
	private String id;
	private String nombre;
	private LocalDate fechaNacimiento;
	private Gender genero;
	private String ciudad;
	private String estadoCivil;
	private String nivelAcademico;

	/**
	 * constructor de clase usuario
	 * @param id identificador unico de usuario
	 * @param nombre 
	 * @param genero
	 * @param ciudad
	 * @param fechaNacimiento
	 * @param estadoCivil
	 * @param nivelAcademico
	 */
	public Usuario(String id, String nombre, Gender genero, String ciudad,LocalDate fechaNacimiento,
			String estadoCivil, String nivelAcademico) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.genero = genero;
		this.ciudad = ciudad;
		this.fechaNacimiento = fechaNacimiento;
		this.estadoCivil = estadoCivil;
		this.nivelAcademico = nivelAcademico;
	}
	
	/**
	 * constructor usuario sin parametros
	 */
	public Usuario() {
		
	}

	/**
	 * obtiene el nombre
	 * @return nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * establece el nombre
	 * @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * obtiene el id
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * establece el id
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * obtiene el genero
	 * @return genero
	 */
	public Gender getGenero() {
		return genero;
	}

	/**
	 * establece el genero
	 * @param genero
	 */
	public void setGenero(Gender genero) {
		this.genero = genero;
	}

	/**
	 * obtiene ciudad
	 * @return ciudad
	 */
	public String getCiudad() {
		return ciudad;
	}

	/**
	 * establece ciudad
	 * @param ciudad
	 */
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
    
	/**obtiene fecha de nacimiento
	 * 
	 * @return fecha de nacimiento
	 */
	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	/**
	 * establece fecha de nacimiento
	 * @param fechaNacimiento
	 */
	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	/**
	 * obtiene estado civil
	 * @return estado civil
	 */
	public String getEstadoCivil() {
		return estadoCivil;
	}

	/**
	 * establece estado civil
	 * @param estadoCivil
	 */
	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	/**
	 * obtiene nivel academico
	 * @return nivel academico
	 */
	public String getNivelAcademico() {
		return nivelAcademico;
	}

	/**
	 * establece nivel academico
	 * @param nivelAcademico
	 */
	public void setNivelAcademico(String nivelAcademico) {
		this.nivelAcademico = nivelAcademico;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", fechaNacimiento=" + fechaNacimiento + ", edad="
				+ Calendario.getTiempo(fechaNacimiento) + ",\n genero=" + genero.getGenero() + ", ciudad=" + ciudad + ", estadoCivil=" + estadoCivil
				+ ", nivelAcademico=" + nivelAcademico + "]";
	}

}
