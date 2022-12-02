package rs.servicio;

import java.util.List;
import rs.modelo.Usuario;

/**
 * interfaz de servicio de usuarios
 * @author Camacho, Cristian; Jaime, Cesar
 *
 */
public interface UsuarioService {

	void insertar(Usuario usuario);

	void actualizar(Usuario usuario);

	void borrar(Usuario usuario);

	List<Usuario> buscarTodos();

}
