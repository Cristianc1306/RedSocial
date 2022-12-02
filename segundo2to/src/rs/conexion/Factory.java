package rs.conexion;

import java.util.Hashtable;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
/**
 * Crea las instancias para clases de lecturas de datos. 
 * @author Jaime, César; Camacho, Cristian
 *
 */
public class Factory {
	final static Logger logger = Logger.getLogger(Factory.class);
	private static Hashtable<String, Object> instancias = new Hashtable<String, Object>();

	/**
	 * obtiene la instancia
	 * @param objName usuarios o relaciones 
	 * @return objeto relacionado con la lectura de usuarios o relaciones.
	 */
	public static Object getInstancia(String objName) {
		try {
			// verifico si existe un objeto relacionado a objName
			// en la hashtable
			Object obj = instancias.get(objName);
			// si no existe entonces lo instancio y lo agrego
			if (obj == null) {
				ResourceBundle rb = ResourceBundle.getBundle("factory");
				String sClassname = rb.getString(objName);
				obj = Class.forName(sClassname).newInstance();
				// agrego el objeto a la hashtable
				logger.debug(obj);
			}
			return obj;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
}
