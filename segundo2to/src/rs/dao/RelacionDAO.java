package rs.dao;

import java.util.List;
import rs.modelo.Relacion;

/**
 * Interfaz de objeto de acceso a datos de relaciones
 * @author Camacho, Cristian; Jaime, Cesar
 *
 */
public interface RelacionDAO {
	
	void insertar(Relacion relacion);

	void actualizar(Relacion relacion);

	void borrar(Relacion relacion);

	List<Relacion> buscarTodos();
}
