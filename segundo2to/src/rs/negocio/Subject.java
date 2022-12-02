package rs.negocio;

import java.util.ArrayList;
import java.util.List;

/**
 * sujeto observador de cambio de estados
 * @author usuario
 *
 */
public class Subject {

	private List<Observer> observers = new ArrayList<Observer>();
	
	/**
	 * notifica los cambios de estado
	 */
	public void refresh() {				
		notifyAllObservers();
	}
	
	/**
	 * adjunta observador
	 * @param observer
	 */
	public void attach(Observer observer) {
		observers.add(observer);
	}

	/**
	 * actualiza los cambios de estado
	 */
	public void notifyAllObservers() {
		for (Observer observer : observers) {
			observer.update();
		}
	}
}
