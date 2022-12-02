package rs.gui;

import java.util.Random;

import javax.swing.SwingWorker;

import rs.controlador.Coordinador;

/**
 * Hilo de ejecución de consultas
 * @author Camacho, Cristian; Jaime, Cesar
 *
 */
public class ConsultasHilo extends SwingWorker<Void, Void> {

	private int baseSleepTime;
	private Coordinador coordinador;
	private UsuariosFormConsultas usuariosFormConsultas;

	/**
	 * constructor de consultasHilo
	 * @param baseSleepTime 
	 * @param coordinador
	 * @param usuariosFormConsultas
	 */
	public ConsultasHilo(int baseSleepTime, Coordinador coordinador, UsuariosFormConsultas usuariosFormConsultas) {
		super();
		this.baseSleepTime = baseSleepTime;
		this.coordinador = coordinador;
		this.usuariosFormConsultas=usuariosFormConsultas;
	}

	/**
	 * Tarea principal. Ejecutado en un hilo secundario.
	 */
	@Override
	public Void doInBackground() {
		Random random = new Random();
		int progress = 0;
		// Initialize progress property.
		setProgress(0);
		while (progress < 10 && !isCancelled()) {
			// Sleep for up to one second.
			try {
				Thread.sleep(random.nextInt(1000) + baseSleepTime);
			} catch (InterruptedException ignore) {
			}
			// Make random progress.
			progress += random.nextInt(10);
			setProgress(Math.min(progress, 100));
		}

		return null;
	}

	/**
	 * Ejecutado en el hilo primario.
	 */
	@Override
	public void done() {
		if (!isCancelled())
			coordinador.realizarConsulta(usuariosFormConsultas);

	}
}
