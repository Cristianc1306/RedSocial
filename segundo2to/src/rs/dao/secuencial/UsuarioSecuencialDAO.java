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

import org.apache.log4j.Logger;

import rs.dao.UsuarioDAO;
//import rs.dao.aleatorio.RelacionAleatorioDAO;
import rs.modelo.Gender;
import rs.modelo.Usuario;

/**
 * 
 * clase que accede secuencialmente al archivo de usuarios para la carga de datos
 * @author Camacho, Cristian; Jaime, César
 *
 */
public class UsuarioSecuencialDAO implements UsuarioDAO {
	
	final static Logger logger = Logger.getLogger(UsuarioSecuencialDAO.class);
	
	private List<Usuario> list;
	private String name;

	
	/**
	 * 
	 * acceso a usuarios y carga en una lista de usuarios
	 */
	public UsuarioSecuencialDAO() {
		
		ResourceBundle rb = ResourceBundle.getBundle("secuencial");
		name = rb.getString("usuarios");
		list = readFromFile(name);
	}
    
	/**
	 * Lista de usuarios a partir de la lectura del archivo
	 * @param file
	 * @return lista de usuarios
	 */
	private List<Usuario> readFromFile(String file) {

		List<Usuario> list = new ArrayList<>();
		Scanner inFile = null;
		try {
			inFile = new Scanner(new File(file));
			inFile.useDelimiter("\\s*,\\s*");
			while (inFile.hasNext()) {
				Usuario u = new Usuario();			
				u.setId(inFile.next());			
				u.setNombre(inFile.next());
				u.setGenero(Gender.valueOf(inFile.next()));
				u.setCiudad(inFile.next());	
				u.setFechaNacimiento(
						LocalDate.of(inFile.nextInt(), inFile.nextInt(), inFile.nextInt()));
				u.setEstadoCivil(inFile.next());
				u.setNivelAcademico(inFile.next());
				list.add(u);
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
	 * escribe la lista de usuarios en el archivo
	 * @param lista de usuarios
	 * @param file
	 */
	private void writeToFile(List<Usuario> list, String file) {
		Formatter outFile = null;
		try {
			outFile = new Formatter(file);
			for (Usuario u : list) {
				outFile.format("%s,%s,%s,%s,%d,%d,%d,%s,%s,\n", u.getId(), u.getNombre(), u.getGenero().toString(),u.getCiudad(),
						u.getFechaNacimiento().getYear(), u.getFechaNacimiento().getMonthValue(), u.getFechaNacimiento().getDayOfMonth(),
						u.getEstadoCivil(),u.getNivelAcademico());
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
	 * obtiene en una lista todas los usuarios
	 * @return lista de usuarios
	 */
	@Override
	public List<Usuario> buscarTodos() {
		return list;
	}

	/**
	 * insterta un usuario en el archivo
	 * @param usuario
	 */
	@Override
	public void insertar(Usuario usuario) {
		if(!list.contains(usuario)) {
		    list.add(usuario);
		    writeToFile(list, name);
		}
	}

	/**
	 * actualiza la informacion de un usuario en el archivo
	 * @param usuario
	 */
	@Override
	public void actualizar(Usuario usuario) {
		int pos = list.indexOf(usuario);
		list.set(pos, usuario);
		writeToFile(list, name);
	}

	/**
	 * borra usuario del archivo
	 * @param usuario
	 */
	@Override
	public void borrar(Usuario usuario) {
		list.remove(usuario);
		writeToFile(list, name);
	}

}
