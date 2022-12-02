package rs.dao.secuencial;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;

import java.util.Formatter;
import java.util.FormatterClosedException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import rs.conexion.Factory;
import rs.dao.RelacionDAO;
import rs.dao.UsuarioDAO;
//import rs.dao.aleatorio.RelacionAleatorioDAO;
import rs.modelo.Relacion;
import rs.modelo.Usuario;

/**
 * clase que accede secuencialmente al archivo relaciones para la carga de datos
 * @author Camacho, Cristian; Jaime, César
 *
 */
public class RelacionSecuencialDAO implements RelacionDAO {
	
	final static Logger logger = Logger.getLogger(RelacionSecuencialDAO.class);
	
	private List<Relacion> list;
	private String name;
	private TreeMap<String, Usuario> usuarios;

	/**
	 * acceso a relaciones y carga en una lista de relaciones
	 */
	public RelacionSecuencialDAO() {
		
		ResourceBundle rb = ResourceBundle.getBundle("secuencial");
		name = rb.getString("relaciones");
	    usuarios=cargaUsuarios();
		list = readFromFile(name);
	}

	/**
	 * Lista de relaciones a partir de la lectura del archivo
	 * @param file
	 * @return lista de relaciones
	 */
	private List<Relacion> readFromFile(String file) {
		List<Relacion> list = new ArrayList<>();
		Scanner inFile = null;
		try {
			inFile = new Scanner(new File(file));
			inFile.useDelimiter("\\s*,\\s*");
			while (inFile.hasNext()) {
				Relacion r = new Relacion();			
				r.setUsuario1(usuarios.get(inFile.next()));
				r.setUsuario2(usuarios.get(inFile.next()));
				r.setInteraccion(inFile.nextInt());
				r.setLikes(inFile.nextInt());
				r.setFechaAmistad(LocalDate.of(inFile.nextInt(), inFile.nextInt(), inFile.nextInt()));
				list.add(r);
			}
		} catch (FileNotFoundException fileNotFoundException) {
			logger.error("Error opening file.");
			fileNotFoundException.printStackTrace();
		} catch (NoSuchElementException noSuchElementException) {
			logger.error("Error in file record structure");
			noSuchElementException.printStackTrace();
		} catch (IllegalStateException illegalStateException) {
			logger.error("Error reading from file.");
			illegalStateException.printStackTrace();
		} finally {
			if (inFile != null)
				inFile.close();
		}
		return list;
	}

	/**
	 * escribe la lista de relaciones en el archivo
	 * @param lista de relaciones
	 * @param file
	 */
	private void writeToFile(List<Relacion> list, String file) {
		Formatter outFile = null;
	
		try {
			outFile = new Formatter(file);
			for (Relacion r : list) {

				outFile.format("%s,%s,%d,%d,%d,%d,%d,\n", r.getUsuario1().getId(), r.getUsuario2().getId(), r.getInteraccion(),
				r.getLikes(),r.getFechaAmistad().getYear(),r.getFechaAmistad().getMonthValue(),r.getFechaAmistad().getDayOfMonth());
			}
		} catch (FileNotFoundException fileNotFoundException) {
			logger.error("Error creating file.");
		} catch (FormatterClosedException formatterClosedException) {
			logger.error("Error writing to file.");
		} finally {
			if (outFile != null)
				outFile.close();
		}
	}

	/**
	 * obtiene en una lista todas las relaciones
	 * @return lista de relaciones
	 */
	@Override
	public List<Relacion> buscarTodos() {
		return list;
	}

	/**
	 * insterta una relacion en el archivo
	 * @param relacion
	 */
	@Override
	public void insertar(Relacion relacion) {
		if(!list.contains(relacion)) {
		    list.add(relacion);
		    writeToFile(list, name);
		}
	}

	/**
	 * actualiza la informacion de la relacion en el archivo
	 * @param relacion
	 */
	@Override
	public void actualizar(Relacion relacion) {
		int pos = list.indexOf(relacion);
		list.set(pos, relacion);
		writeToFile(list, name);
	}

	/**
	 * borra relacion del archivo
	 * @param relacion
	 */
	@Override
	public void borrar(Relacion relacion) {
		list.remove(relacion);
		writeToFile(list, name);
	}
	
	/**
	 * carga los usuarios del archivo en un mapa de id usuarios y usuarios
	 * @return mapa de id de usuario y usarios
	 */
	private TreeMap<String, Usuario> cargaUsuarios() {
		TreeMap<String, Usuario> usuariosM =new TreeMap<String, Usuario>();
		UsuarioDAO usuarioDAO = (UsuarioDAO) Factory.getInstancia("USUARIOS");
		List<Usuario> us= usuarioDAO.buscarTodos();
		for(Usuario u:us)
			usuariosM.put(u.getId(), u);
		return usuariosM;
		
	}

}
