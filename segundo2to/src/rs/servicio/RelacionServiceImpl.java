package rs.servicio;

import java.util.List;
import rs.conexion.Factory;
import rs.dao.RelacionDAO;
import rs.modelo.Relacion;

/**
 * implementacion del servicio de relaciones
 * @author Jaime, Cesar; Camacho, Cristian
 *
 */
public class RelacionServiceImpl implements RelacionService {

	private RelacionDAO relacionDAO; 
	
	/**
	 * obtiene la interfaz RelacionDAO de factory
	 */
	public RelacionServiceImpl(){
		relacionDAO = (RelacionDAO) Factory.getInstancia("RELACIONES");
	}
	
	/**
	 * inserta relacion 
	 * @param relacion
	 */
	@Override
	public void insertar(Relacion relacion) {
		relacionDAO.insertar(relacion);				
	}

	/**
	 * actualiza relacion
	 * @param relacion
	 */
	@Override
	public void actualizar(Relacion relacion) {
		relacionDAO.actualizar(relacion);						
	}

	/**
	 * borra relacion
	 * @param relacion
	 */
	@Override
	public void borrar(Relacion relacion) {
		relacionDAO.borrar(relacion);
		
	}

	/**
	 * obtiene lista de relaciones
	 * @return lista relaciones
	 */
	@Override
	public List<Relacion> buscarTodos() {
		return relacionDAO.buscarTodos();
		
	}

}
