package rs.servicio;

import java.util.List;

import rs.modelo.Relacion;

/**
 * interfaz de servicio de relaciones
 * @author Camacho, Cristian; Jaime, Cesar
 *
 */
public interface RelacionService {

	void insertar(Relacion relacion);

	void actualizar(Relacion relacion);

	void borrar(Relacion relacion);

	List<Relacion> buscarTodos();

}
