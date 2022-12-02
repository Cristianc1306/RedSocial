package rs.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.apache.log4j.Logger;

import com.toedter.calendar.JDateChooser;

import rs.controlador.Constantes;
import rs.controlador.Coordinador;
import rs.modelo.Relacion;
import rs.modelo.Usuario;
import rs.util.Validation;

/**
 * Formulario de consultas de una relacion
 * @author Camacho, Cristan; Jaime, Cesar
 *
 */
public class RelacionesForm extends JDialog {
	
	final static Logger logger = Logger.getLogger(RelacionesForm.class);
	private Coordinador coordinador;

	private JPanel contentPane;
	private String jtfId1C;
	private String jtfId2C;

	private JTextField jtfId1;
	private JTextField jtfId2;
	private JTextField jtfInteraccion;
	private JTextField jtfLikes;
	private JTextField jtfTiempo;

	private JLabel lblErrorInteraccion;
	private JLabel lblErrorLikes;
	private JLabel lblErrorTiempo;

	private JButton btnInsertar;
	private JButton btnModificar;
	private JButton btnBorrar;
	private JButton btnCancelar;

	private JComboBox usuariosJComboBox;
	private JComboBox usuarios2JComboBox;
	private JDateChooser calendario;
	private String fechaDeAmistad;
	private String usuarios[];


	/**
	 * Crea el marco.
	 */
	public RelacionesForm() {
		 logger.debug("Cargando panel de relaciones");
		setBounds(450, 150, 550, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblId1 = new JLabel("Id usuario 1:");
		lblId1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblId1.setBounds(42, 35, 107, 14);
		contentPane.add(lblId1);

		jtfId1 = new JTextField();
		jtfId1.setBounds(170, 35, 86, 20);
		contentPane.add(jtfId1);
		jtfId1.setColumns(10);

		JLabel lblId2 = new JLabel("Id usuario 2:");
		lblId2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblId2.setBounds(42, 80, 107, 14);
		contentPane.add(lblId2);

		jtfId2 = new JTextField();
		jtfId2.setBounds(170, 80, 86, 20);
		contentPane.add(jtfId2);
		jtfId2.setColumns(10);

		JLabel lblInterracion = new JLabel("Interacción diaria:");
		lblInterracion.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblInterracion.setBounds(42, 135, 120, 14);
		contentPane.add(lblInterracion);

		jtfInteraccion = new JTextField();
		jtfInteraccion.setBounds(170, 130, 86, 20);
		contentPane.add(jtfInteraccion);
		jtfInteraccion.setColumns(10);

		JLabel lblLikes = new JLabel("Cantidad de Likes:");
		lblLikes.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblLikes.setBounds(42, 170, 120, 14);
		contentPane.add(lblLikes);

		jtfLikes = new JTextField();
		jtfLikes.setColumns(10);
		jtfLikes.setBounds(170, 165, 86, 20);
		contentPane.add(jtfLikes);

		JLabel lblTiempo = new JLabel("Fecha de amistad:");
		lblTiempo.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblTiempo.setBounds(42, 205, 120, 14);
		contentPane.add(lblTiempo);
		
		jtfTiempo = new JTextField();
		jtfTiempo.setColumns(10);
		jtfTiempo.setBounds(170, 200, 86, 20);
		contentPane.add(jtfTiempo);

		calendario =new JDateChooser();
		calendario.setBounds(170, 200, 100, 20);
		contentPane.add(calendario);

		lblErrorInteraccion = new JLabel("");
		lblErrorInteraccion.setForeground(Color.RED);
		lblErrorInteraccion.setBounds(266, 130, 300, 14);
		contentPane.add(lblErrorInteraccion);

		lblErrorLikes = new JLabel("");
		lblErrorLikes.setForeground(Color.RED);
		lblErrorLikes.setBounds(266, 165, 300, 14);
		contentPane.add(lblErrorLikes);

		lblErrorTiempo = new JLabel("");
		lblErrorTiempo.setForeground(Color.RED);
		lblErrorTiempo.setBounds(286, 200, 300, 14);
		contentPane.add(lblErrorTiempo);

		Handler handler = new Handler();

		btnInsertar = new JButton("Insertar");
		btnInsertar.setBounds(85, 250, 114, 32);
		contentPane.add(btnInsertar);
		btnInsertar.addActionListener(handler);

		btnModificar = new JButton("Modificar");
		btnModificar.setBounds(85, 250, 114, 32);
		contentPane.add(btnModificar);
		btnModificar.addActionListener(handler);

		btnBorrar = new JButton("Borrar");
		btnBorrar.setBounds(85, 250, 114, 32);
		contentPane.add(btnBorrar);
		btnBorrar.addActionListener(handler);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(225, 250, 114, 32);
		contentPane.add(btnCancelar);
		btnCancelar.addActionListener(handler);

		usuariosJComboBox = new JComboBox();
		usuarios2JComboBox = new JComboBox();
		add(usuariosJComboBox);
		add(usuarios2JComboBox);

		setModal(true);
	}

	/**
	 * acciones
	 * @param accion
	 * @param relacion
	 */
	public void accion(int accion, Relacion relacion) {

		btnInsertar.setVisible(false);
		btnModificar.setVisible(false);
		btnBorrar.setVisible(false);
		
		jtfId1.setVisible(true);
		jtfId2.setVisible(true);
		jtfTiempo.setVisible(false);
		calendario.setVisible(true);
		
		jtfId1.setEditable(false);
		jtfId2.setEditable(false);
		jtfInteraccion.setEditable(true);
		jtfLikes.setEditable(true);
		jtfTiempo.setEditable(false);
		

		limpiar();
		remove(usuariosJComboBox);
		remove(usuarios2JComboBox);

		if (accion == Constantes.INSERTAR) {
			btnInsertar.setVisible(true);
			jtfId1.setVisible(false);
			jtfId2.setVisible(false);
			cargarComboBox();
			calendario.setDate(Validation.isDate(LocalDate.now().toString(), "yyyy-MM-dd"));
		}
		if (accion == Constantes.MODIFICAR) {
			btnModificar.setVisible(true);
			mostrar(relacion);
		}
		if (accion == Constantes.BORRAR) {
			btnBorrar.setVisible(true);
			jtfInteraccion.setEditable(false);
			jtfLikes.setEditable(false);
			jtfTiempo.setVisible(true);
			calendario.setVisible(false);
			mostrar(relacion);
		}

	}

	/**
	 * Establece texto de formulario y lo muestra
	 * @param relacion
	 */
	private void mostrar(Relacion relacion) {
		
		calendario.setDate(Validation.isDate(relacion.getFechaAmistad().toString(), "yyyy-MM-dd"));

		jtfId1.setText(relacion.getUsuario1().getId());
		jtfId2.setText(relacion.getUsuario2().getId());
		jtfInteraccion.setText(Integer.toString(relacion.getInteraccion()));
		jtfLikes.setText(Integer.toString(relacion.getLikes()));
		jtfTiempo.setText(relacion.getFechaAmistad().toString());

	}

	/**
	 * carga el combobox y su manejador de eventos
	 */
	private void cargarComboBox() {

		cargarUsuarios();
		jtfId1C = usuarios[0];
		jtfId2C = usuarios[0];
		usuariosJComboBox = new JComboBox(usuarios); // set up JComboBox
		usuariosJComboBox.setMaximumRowCount(10);
		usuariosJComboBox.setBounds(165, 25, 100, 30);
		usuariosJComboBox.addItemListener(new ItemListener() // anonymous inner class
		{
			// handle JComboBox event
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					jtfId1C = usuarios[usuariosJComboBox.getSelectedIndex()];
				}
			}
		});
		usuarios2JComboBox = new JComboBox(usuarios); // set up JComboBox
		usuarios2JComboBox.setMaximumRowCount(10);
		usuarios2JComboBox.setBounds(165, 75, 100, 30);
		usuarios2JComboBox.addItemListener(new ItemListener() // anonymous inner class
		{
			// handle JComboBox event
			public void itemStateChanged(ItemEvent event) {

				if (event.getStateChange() == ItemEvent.SELECTED) {
					jtfId2C = usuarios[usuarios2JComboBox.getSelectedIndex()];
				}
			}
		});

		add(usuariosJComboBox);
		add(usuarios2JComboBox);
	}

	/**
	 * carga los usuarios
	 */
	private void cargarUsuarios() {
		int i = 0;
		usuarios = new String[coordinador.listaUsuarios().size()];
		for (Usuario u : coordinador.listaUsuarios()) {
			usuarios[i] = u.getId();
			i++;
		}
	}

	/**
	 * pone en blanco el formulario.
	 */
	private void limpiar() {

		jtfInteraccion.setText("");
		jtfLikes.setText("");
		
		lblErrorInteraccion.setText("");
		lblErrorLikes.setText("");
		lblErrorTiempo.setText("");
	}

	/**
	 * manejador de eventos
	 * @author Camacho, Cristian
	 */
	private class Handler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			
			if (event.getSource() == btnCancelar)
				coordinador.cancelarRelacion();;

			if (event.getSource() == btnBorrar) {
				int resp = JOptionPane.showConfirmDialog(null, "Estás seguro de borrar este registro?", "Confirmar",
						JOptionPane.YES_NO_OPTION);
				if (JOptionPane.OK_OPTION == resp) {
					coordinador.borrarRelacion(
							new Relacion(new Usuario(jtfId1.getText(), null, null, null, null, null, null),
									new Usuario(jtfId2.getText(), null, null, null, null, null, null), 0, 0, null));
					 logger.info("Se borro la relacion de:"+jtfId1.getText()+" y "+jtfId2.getText()+" del frame");
					 return;
				}
				
			}

			boolean valido = true;

			// validar interaccion
			int interaccion = Validation.isInteger(jtfInteraccion.getText().trim());
			if (interaccion == -1) {
				lblErrorInteraccion.setText("Interacción no válido");
				valido = false;
			}

			// validar likes
			int likes = Validation.isInteger(jtfLikes.getText());
			if (likes == -1) {
				lblErrorLikes.setText("Likes no válido");
				valido = false;
			}

			// validar fecha de Amistad 
			LocalDate tiempo =null;
			if (calendario.getDate() == null) {
				lblErrorTiempo.setText("Ingrese una fecha");
				valido = false;
			}
			else {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				fechaDeAmistad=sdf.format(calendario.getDate());
				tiempo = Validation.isDate(fechaDeAmistad);
			}


			if (!valido) {
				logger.error("Campos no válidos!");
				return;
			}
			
			if (event.getSource() == btnInsertar) {
				Usuario u1 = coordinador.buscarUsuario(new Usuario(jtfId1C,null,null,null,null,null,null));
				Usuario u2 = coordinador.buscarUsuario(new Usuario(jtfId2C,null,null,null,null,null,null));
				Relacion r = new Relacion(u1, u2, interaccion, likes, tiempo);
				if(u1.equals(u2)) {
					JOptionPane.showMessageDialog(null, "Relación invalida:Los dos usuarios son iguales");
					logger.error("relacion invalida los dos usuarios son iguales");
					return;
				}
				
				if (coordinador.buscarRelacion(r)!=null) {
					JOptionPane.showMessageDialog(null, "Esta relación ya existe");
					logger.error("Esta relacion ya existe");
				} else {
					coordinador.insertarRelacion(r);
					logger.info("se agrego la relacion:"+jtfId1C+" y "+jtfId2C +" al frame");
				}
			}
			if (event.getSource() == btnModificar) {
				Relacion r = new Relacion(coordinador.buscarUsuario(new Usuario(jtfId1.getText(),null,null,null,null,null,null)),
						coordinador.buscarUsuario(new Usuario(jtfId2.getText(),null,null,null,null,null,null)), interaccion, likes, tiempo);
				coordinador.modifcarRelacion(r);
				logger.info("Se modifico la relacion:"+jtfId1+" y "+jtfId2+" del frame");
			}
		}

	}

	/**
	 * establece coordinador
	 * @param coordinador
	 */
	public void setCoordinador(Coordinador coordinador) {
		this.coordinador = coordinador;
	}

}
