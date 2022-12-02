package rs.dao.aleatorio;

import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.Logger;

import rs.conexion.AConnection;
import rs.conexion.Factory;
import rs.dao.RelacionDAO;
import rs.dao.UsuarioDAO;
import rs.modelo.Relacion;
import rs.modelo.Usuario;
import rs.util.FileUtil;

/**
 * Clase que accede al archivo de relaciones de forma aleatoria implementa Interfaz RelacionDAO
 * @author Camacho, Cristian; Jaime, César
 *
 */
public class RelacionAleatorioDAO implements RelacionDAO {
	
	final static Logger logger = Logger.getLogger(RelacionAleatorioDAO.class);
	private RandomAccessFile file = null;
	private Hashtable<Integer, Integer> index;
	private int nDeleted;

	private static final int SIZE_ID1 = 15;
	private static final int SIZE_ID2 = 15;

	private static final int SIZE_RECORD = Character.BYTES + Character.BYTES * SIZE_ID1 + Character.BYTES * SIZE_ID2
			+ Integer.BYTES + Integer.BYTES + FileUtil.SIZE_DATE;

	private Hashtable<String, Usuario> usuarios;

	/**
	 * La conexion se establece con el archivo relaciones el cual es leído y genera una tabla hash 
	 * de codigo de relaciones  y posiciones.
	 * 
	 */
	public RelacionAleatorioDAO() {
		
		if (file == null) {
			file = AConnection.getInstancia("relaciones");
			index = new Hashtable<Integer, Integer>();
			nDeleted = 0;
			Relacion relacion;
			int pos = 0;
			char deleted;
			usuarios = cargarUsuarios();
			try {
				file.seek(0);
				while (true) {
					deleted = file.readChar();
					relacion = readRecord();
				
					if (deleted == FileUtil.DELETED) {
						index.put(nDeleted++, pos++);
					} else {
						index.put(relacion.hashCode(), pos++);
					}
				}
			} catch (EOFException e) {
				logger.info("Se termino de leer el archivo relaciones ");
				
			} catch (IOException e) {
				logger.error("Error al leer el archivo relaciones");
			}
		}
	}

	/**
	 * Se obtiene una lista con todas las relaciones
	 * @return lista de relaciones
	 */
	public List<Relacion> buscarTodos() {
		List<Relacion> ret = new ArrayList<Relacion>();
		Relacion relacion;
		char deleted;
		usuarios = cargarUsuarios();
		try {
			file.seek(0);
			while (true) {
				deleted = file.readChar();
				relacion = readRecord();
				if (deleted != FileUtil.DELETED) {
					ret.add(relacion);

				}
			}
		} catch (EOFException e) {
			logger.info("Se termino de cargar la lista de relaciones del archivo ");
			return ret;
		} catch (IOException e) {
			logger.error("Error al carga la lista de relaciones del archivo");
			return null;
		}
	}

	/**
	 * Se inserta una relacion al archivo de relaciones
	 */
	@Override
	public void insertar(Relacion relacion) {
		Integer pos = index.get(relacion.hashCode());
		if (pos != null)
			return;
		int nr = index.size()  * SIZE_RECORD;
		try {
			file.seek(nr);
			file.writeChar(' ');
			writeRecord(relacion);
			index.put(relacion.hashCode(), nr / SIZE_RECORD);
		} catch (IOException e) {
			logger.error("No se pudo agregar la relacion "+ relacion.getUsuario1().getId() 
					+" y "+relacion.getUsuario2().getId()+" al archivo");
		}
	}

	/**
	 * Actualiza la informacion de una relacion en el archivo de relaciones.
	 */
	@Override
	public void actualizar(Relacion relacion) {
		Integer pos = index.get(relacion.hashCode());
		if (pos == null)
			return;
		int nr = pos * SIZE_RECORD;
		try {
			file.seek(nr);
			file.writeChar(' ');
			writeRecord(relacion);
		} catch (IOException e) {
			logger.error("No se pudo modificar la relacion "+ relacion.getUsuario1().getId() 
					+" y "+relacion.getUsuario2().getId()+" del archivo");
		}

	}

	/**
	 * se borra una relacion del archivo de relaciones
	 */
	@Override
	public void borrar(Relacion relacion) {
		Integer pos = index.get(relacion.hashCode());
	
		if (pos == null)
			return;
		int nr = pos * SIZE_RECORD;
		
		try {
			file.seek(nr);
			file.writeChar(FileUtil.DELETED);
			index.remove(relacion.hashCode());
			index.put(nDeleted, pos);
			nDeleted++;
		} catch (IOException e) {
			logger.error("No se pudo borrar la relacion "+ relacion.getUsuario1().getId() 
					+" y "+relacion.getUsuario2().getId()+" del archivo");
		}
	}

	/**
	 * 
	 * @throws IOException
	 */
	public void pack() throws IOException {
		List<Relacion> relaciones = buscarTodos();
		AConnection.backup("relaciones");
		AConnection.delete("relaciones");
		file = AConnection.getInstancia("relaciones");
		index = new Hashtable<Integer, Integer>();
		nDeleted = 0;
		for (Relacion ea : relaciones)
			insertar(ea);
	}

	/**
	 * obtiene una tabla hash de codigo de usuario y usuarios
	 * @return
	 */
	private Hashtable<String, Usuario> cargarUsuarios() {
		Hashtable<String, Usuario> lusuarios = new Hashtable<>();
		UsuarioDAO usuarioDAO = (UsuarioDAO) Factory.getInstancia("USUARIOS");
		List<Usuario> us = usuarioDAO.buscarTodos();
		for (Usuario u : us) 
			lusuarios.put(u.getId(), u);
		
		return lusuarios;
	}

	/**
	 * lee el registro de archivo
	 * @return relacion
	 * @throws IOException
	 */
	private Relacion readRecord() throws IOException {

		return new Relacion(usuarios.get(FileUtil.readString(file, SIZE_ID1)),
				usuarios.get(FileUtil.readString(file, SIZE_ID2)), file.readInt(), file.readInt(),
				FileUtil.readDate(file));
	}

	/**
	 * escribe en el archivo un registro la relacion
	 * @param relacion
	 * @throws IOException
	 */
	private void writeRecord(Relacion relacion) throws IOException {
		FileUtil.writeString(file, relacion.getUsuario1().getId(), SIZE_ID1);
		FileUtil.writeString(file, relacion.getUsuario2().getId(), SIZE_ID2);
		file.writeInt(relacion.getInteraccion());
		file.writeInt(relacion.getLikes());
		FileUtil.writeDate(file, relacion.getFechaAmistad());

	}
}
