package rs.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.apache.log4j.Logger;

import rs.controlador.Constantes;
import rs.controlador.Coordinador;
import rs.modelo.Gender;
import rs.modelo.Usuario;
import rs.util.Validation;

/**
 * formulario de usuarios que responde a la consulta hecha
 * @author Camacho, Cristian; Jaime, Cesar
 *
 */
public class UsuariosFormConsultas extends JDialog {

	final static Logger logger = Logger.getLogger(UsuariosFormConsultas.class);
	private Coordinador coordinador;
	private int opcion;
	private String nombre;
	private JPanel contentPane;
	private JProgressBar progressBar;
	private String jtfId;
	private String jtfId2;

	private JLabel lblErrorId;
	private JLabel lblErrorId2;
	private JLabel lblEspera;

	private JButton btnConsultar;
	private JButton btnCancelar;

	private JLabel lblId;
	private JLabel lblId2;

	private String usuarios[];
	private JComboBox usuariosJComboBox;
	private JComboBox usuarios2JComboBox;

	private Usuario u1;
	private Usuario u2;
	private ConsultasHilo consultasHilo;
	private ExecutorService ejecutor = Executors.newCachedThreadPool();

	/**
	 * Crea el marco
	 */
	public UsuariosFormConsultas() {
		logger.debug("Cargando panel de consultas de usuarios");
		setBounds(450, 150, 500, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		progressBar = new JProgressBar(0, 100);
		progressBar.setValue(0);
		progressBar.setBounds(90, 200, 230, 35);
		progressBar.setStringPainted(true);

		lblId = new JLabel("Id usuario  :");
		lblId.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblId.setBounds(42, 30, 107, 14);
		contentPane.add(lblId);

		lblId2 = new JLabel("Id usuario 2:");
		lblId2.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblId2.setBounds(42, 84, 107, 14);
		contentPane.add(lblId2);

		lblErrorId = new JLabel("");
		lblErrorId.setForeground(Color.RED);
		lblErrorId.setBounds(255, 24, 300, 14);
		contentPane.add(lblErrorId);

		lblErrorId2 = new JLabel("");
		lblErrorId2.setForeground(Color.RED);
		lblErrorId2.setBounds(255, 55, 300, 14);
		contentPane.add(lblErrorId2);

		Handler handler = new Handler();

		btnConsultar = new JButton("Consultar");
		btnConsultar.setBounds(85, 130, 114, 32); // 202
		contentPane.add(btnConsultar);
		btnConsultar.addActionListener(handler);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(225, 130, 114, 32);
		contentPane.add(btnCancelar);
		btnCancelar.addActionListener(handler);

		lblEspera = new JLabel("");
		lblEspera.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblEspera.setBounds(90, 180, 300, 14);
		contentPane.add(lblEspera);

		setModal(false);

		contentPane.add(progressBar);
	}

	/**
	 * manejador de eventos
	 * @param opcion la consulta elegida
	 * @param nombre titulo de la consulta
	 */
	public void accion(int opcion, String nombre) {

		setTitle(nombre);
		this.nombre = nombre;
		this.opcion = opcion;
		setCursor(null);
		lblId.setVisible(true);
		lblId2.setVisible(true);

		lblEspera.setText("");
		progressBar.setVisible(false);
		btnConsultar.setEnabled(true);
		cargarUsuarios();
		jtfId = usuarios[0];
		jtfId2 = usuarios[0];

		if (usuariosJComboBox != null)
			remove(usuariosJComboBox);

		usuariosJComboBox = new JComboBox(usuarios);
		usuariosJComboBox.setMaximumRowCount(10);
		usuariosJComboBox.setBounds(150, 25, 100, 30);
		usuariosJComboBox.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent event) {

				if (event.getStateChange() == ItemEvent.SELECTED) {
					jtfId = usuarios[usuariosJComboBox.getSelectedIndex()];
				}
			}
		});

		if (usuarios2JComboBox != null)
			remove(usuarios2JComboBox);

		usuarios2JComboBox = new JComboBox(usuarios);
		usuarios2JComboBox.setMaximumRowCount(10);
		usuarios2JComboBox.setBounds(150, 75, 100, 30);
		usuarios2JComboBox.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent event) {

				if (event.getStateChange() == ItemEvent.SELECTED) {
					jtfId2 = usuarios[usuarios2JComboBox.getSelectedIndex()];
				}
			}
		});

		if (opcion == Constantes.AMIGOS_DE || opcion == Constantes.SUGERENCIA_AMISTAD) {
			lblId2.setVisible(false);
			usuarios2JComboBox.setVisible(false);

		}
		if (opcion == Constantes.GRADO_PROMEDIO || opcion == Constantes.MAS_INFLUYENTES || opcion == Constantes.DENSIDAD
				|| opcion == Constantes.MAS_INTERACTUA) {
			lblId.setVisible(false);
			usuariosJComboBox.setVisible(false);
			lblId2.setVisible(false);
			usuarios2JComboBox.setVisible(false);
		}

		add(usuariosJComboBox);
		add(usuarios2JComboBox);

	}

	/**
	 * carga los usuarios en el formulario
	 */
	private void cargarUsuarios() {
		usuarios = new String[coordinador.listaUsuarios().size()];
		int i = 0;
		for (Usuario u : coordinador.listaUsuarios()) {
			usuarios[i] = u.getId();
			i++;
		}
	}

	/**
	 * manejador de eventos
	 * @author usuario
	 *
	 */
	private class Handler implements ActionListener, PropertyChangeListener {
		public void actionPerformed(ActionEvent event) {

			if (event.getSource() == btnCancelar) {
				if (consultasHilo != null)
					consultasHilo.cancel(true);

				coordinador.cancelarConsultasUsuarios(estaClase());

			}

			u1 = coordinador.buscarUsuario(new Usuario(jtfId, null, null, null, null, null, null));

			u2 = coordinador.buscarUsuario(new Usuario(jtfId2, null, null, null, null, null, null));

			if (event.getSource() == btnConsultar) {
				lblEspera.setText("Calculando...");
				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				consultasHilo = new ConsultasHilo(10, coordinador, estaClase());
				consultasHilo.addPropertyChangeListener(this);
				ejecutor.execute(consultasHilo);
				progressBar.setVisible(true);
				btnConsultar.setEnabled(false);
			}

		}

		/**
		 * Se invoca cuando cambia la propiedad de progreso de la tarea.
		 * 
		 */
		@Override
		public void propertyChange(PropertyChangeEvent evt) {

			if ("progress" == evt.getPropertyName()) {
				int progress = (Integer) evt.getNewValue();
				progressBar.setValue(progress);

			}

		}

	}

	/**
	 * muestra el formulario respuesta a la consulta elegida
	 */
	public void mostrarConsulta() {

		switch (opcion) {
		case Constantes.GRADO_PROMEDIO:
			JOptionPane.showMessageDialog(null, "El grado promedio es:\n" + coordinador.mostrarGradoPromedio());
			break;
		case Constantes.MAS_INFLUYENTES:
			coordinador.losMetodosUsuarioList(null, null, null, Constantes.MAS_INFLUYENTES);
			break;
		case Constantes.CAMINO_MAS_NUEVO:
			if (!coordinador.listaUsuarios().contains(u1) || !coordinador.listaUsuarios().contains(u2)) {
				JOptionPane.showMessageDialog(null, "Se actualizaron los datos");
				accion(opcion, nombre);
			} else {

				if (coordinador.listaCaminoMasNuevo(jtfId, jtfId2) == null) {
					JOptionPane.showMessageDialog(null, "No existe un camino entre " + jtfId + " y " + jtfId2);
				}

				else {
					coordinador.losMetodosUsuarioList(null, jtfId, jtfId2, Constantes.CAMINO_MAS_NUEVO);
				}
			}
			break;
		case Constantes.TIEMPO_AMISTAD:
			if (!coordinador.listaUsuarios().contains(u1) || !coordinador.listaUsuarios().contains(u2)) {
				JOptionPane.showMessageDialog(null, "Se actualizaron los datos");

				accion(opcion, nombre);
			} else {
				if (coordinador.mostrarRelacionDeAmistad(u1, u2) == null)
					JOptionPane.showMessageDialog(null, u1.getNombre() + " y " + u2.getNombre() + " no son amigos");
				else {
					JOptionPane.showMessageDialog(null,
							"La relación entre " + u1.getNombre() + " y " + u2.getNombre() + " es:\n"
									+ "Tiempo de Amistad=" + coordinador.mostrarRelacionDeAmistad(u1, u2).getTiempoAmistad()
									+ " años" + "\nLikes=" + coordinador.mostrarRelacionDeAmistad(u1, u2).getLikes()
									+ "\nInteracción=" + coordinador.mostrarRelacionDeAmistad(u1, u2).getInteraccion()
									+ " horas promedio al dia");
				}
			}
			break;
		case Constantes.AMIGOS_DE:
			if (!coordinador.listaUsuarios().contains(u1)) {
				JOptionPane.showMessageDialog(null, "Se actualizaron los datos");
				accion(opcion, nombre);
			} else {
				coordinador.losMetodosUsuarioList(u1, null, null, Constantes.AMIGOS_DE);
			}

			break;
		case Constantes.DENSIDAD:
			coordinador.losMetodosUsuarioList(null, null, null, Constantes.DENSIDAD);
			break;
		case Constantes.SUGERENCIA_AMISTAD:
			if (!coordinador.listaUsuarios().contains(u1)) {
				JOptionPane.showMessageDialog(null, "Se actualizaron los datos");
				accion(opcion, nombre);
			} else {
				coordinador.losMetodosUsuarioList(u1, null, null, Constantes.SUGERENCIA_AMISTAD);
			}

			break;
		case Constantes.MAS_INTERACTUA:
			JOptionPane.showMessageDialog(null,
					"El usuario que mas interactua en la red es el\n" + coordinador.mostrarElQueMasInteractua());
			break;
		}
		setCursor(null);
		lblEspera.setText("");
		progressBar.setVisible(false);
		progressBar.setValue(0);
		btnConsultar.setEnabled(true);
	}

	/**
	 * establece coordinador
	 * @param coordinador
	 */
	public void setCoordinador(Coordinador coordinador) {
		this.coordinador = coordinador;
	}

	/**
	 * obtiene usuariosFormConsultas
	 * @return usuariosFormConsultas
	 */
	private UsuariosFormConsultas estaClase() {

		return this;
	}

}
