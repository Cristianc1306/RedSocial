
import java.time.LocalDate;

/**
 * Clase accesoria calendario
 * @author Jaime, Cesar; Camacho, Cristian
 *
 */
public class Calendario {

	
	public Calendario() {
	}

	/**
	 * obtiene edad en anios 
	 * @param fechaNacimiento
	 * @return anios
	 */
	public static int getTiempo(LocalDate fechaNacimiento) {
		int anios = LocalDate.now().getYear() - fechaNacimiento.getYear();
		int meses = LocalDate.now().getMonthValue() - fechaNacimiento.getMonthValue();
		int dias = LocalDate.now().getDayOfMonth() - fechaNacimiento.getDayOfMonth();
		if (meses < 0 || (meses == 0 && dias < 0)) {
			anios--;
		}
		return anios;
	}
}
