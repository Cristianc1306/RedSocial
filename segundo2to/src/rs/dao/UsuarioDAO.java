package rs.dao;

import java.util.List;
import rs.modelo.Usuario;

/**
 * Interfaz de objeto de acceso a datos de usuarios
 * @author Camacho, Cristian; Jaime, Cesar
 *
 */
public interface UsuarioDAO {
	
	void insertar(Usuario usuario);

	void actualizar(Usuario usuario);

	void borrar(Usuario usuario);

	List<Usuario> buscarTodos();
}
