package rs.servicio;

import java.util.List;
import rs.conexion.Factory;
import rs.dao.UsuarioDAO;
import rs.modelo.Usuario;

/**
 * implementacion del servicio de usuarios
 * @author Jaime, Cesar; Camacho, Cristian
 *
 */
public class UsuarioServiceImpl implements UsuarioService {

	private UsuarioDAO usuarioDAO; 
		
	/**
	 * obtiene la interfaz UsuarioDAO de factory
	 */
	public UsuarioServiceImpl(){
		usuarioDAO = (UsuarioDAO) Factory.getInstancia("USUARIOS");
	}
	
	/**
	 * inserta usuario 
	 * @param usuario
	 */
	@Override
	public void insertar(Usuario usuario) {
		usuarioDAO.insertar(usuario);				
	}

	/**
	 * actualiza usuario
	 * @param usuario
	 */
	@Override
	public void actualizar(Usuario usuario) {
		usuarioDAO.actualizar(usuario);						
	}

	/**
	 * borra usuario
	 * @param usuario
	 */
	@Override
	public void borrar(Usuario usuario) {
		usuarioDAO.borrar(usuario);
		
	}

	/**
	 * obtiene lista de usuario
	 * @return lista usuarios
	 */
	@Override
	public List<Usuario> buscarTodos() {
		return usuarioDAO.buscarTodos();
		
	}

}
