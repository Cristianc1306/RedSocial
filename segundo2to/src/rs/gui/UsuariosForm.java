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
import rs.modelo.Gender;
import rs.modelo.Usuario;
import rs.util.Validation;

public class UsuariosForm extends JDialog {
	
	private final static Logger logger = Logger.getLogger(UsuariosForm.class);
	private Coordinador coordinador;

	private JPanel contentPane;
	private JTextField jtfId;
	private JTextField jtfNombre;
	private JTextField jtfGenero;
	private JTextField jtfCiudad;
	private JTextField jtfEdad;
	private JTextField jtfEstadocivil;
	private JTextField jtfNivelAcademico;

	private JLabel lblErrorId;
	private JLabel lblErrorNombre;
	private JLabel lblErrorCiudad;
	private JLabel lblErrorEdad;

	private JButton btnInsertar;
	private JButton btnModificar;
	private JButton btnBorrar;
	private JButton btnCancelar;

	private String generos[] = { "H=Hombre", "M=Mujer", "NB=No Binario", };
	private String genero;
	private String estadosCiviles[] = { "Casado", "Soltero", "Viudo", "Otro" };
	private String estadoCivil;
	private String nivelesAcademicos[] = { "Primario", "Secundario", "Superior", "Otro" };
	private String nivelAcademico;
	private JComboBox generoJComboBox;
	private JComboBox estadoCivilJComboBox;
	private JComboBox nivelAcademicoJComboBox;
	private JDateChooser calendario;
	private String fechaDeNac;

	/**
	 * Create the frame.
	 */
	public UsuariosForm() {
		logger.debug("Cargando panel de usuarios");
		setBounds(450, 150, 550, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblId = new JLabel("Id:");
		lblId.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblId.setBounds(42, 27, 107, 14);
		contentPane.add(lblId);

		jtfId = new JTextField();
		jtfId.setBounds(185, 24, 86, 20);
		contentPane.add(jtfId);
		jtfId.setColumns(10);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNombre.setBounds(42, 58, 107, 14);
		contentPane.add(lblNombre);

		jtfNombre = new JTextField();
		jtfNombre.setBounds(185, 55, 86, 20);
		contentPane.add(jtfNombre);
		jtfNombre.setColumns(10);

		JLabel lblGenero = new JLabel("Género:");
		lblGenero.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblGenero.setBounds(42, 91, 107, 14);
		contentPane.add(lblGenero);
		
		jtfGenero = new JTextField();
		jtfGenero.setBounds(185, 86, 86, 20);
		contentPane.add(jtfGenero);
		jtfGenero.setColumns(10);

		generoJComboBox = new JComboBox(generos);
		genero=generos[0];
		generoJComboBox.setBounds(185, 86, 100, 25);
		generoJComboBox.addItemListener(new ItemListener() // anonymous inner class
		{
			// handle JComboBox event
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					genero = generos[generoJComboBox.getSelectedIndex()];
				}
			}
		});
		contentPane.add(generoJComboBox);
		

		JLabel lblCiudad = new JLabel("Ciudad:");
		lblCiudad.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblCiudad.setBounds(42, 130, 107, 14);
		contentPane.add(lblCiudad);

		jtfCiudad = new JTextField();
		jtfCiudad.setColumns(10);
		jtfCiudad.setBounds(185, 127, 86, 20);
		contentPane.add(jtfCiudad);

		JLabel lblEdad = new JLabel("Fecha de Nacimiento:");
		lblEdad.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblEdad.setBounds(42, 161, 130, 14);
		contentPane.add(lblEdad);
		
		jtfEdad = new JTextField();
		jtfEdad.setBounds(185, 158, 86, 20);
		contentPane.add(jtfEdad);
		jtfEdad.setColumns(10);

		calendario = new JDateChooser();
		calendario.setBounds(185, 158, 100, 20);
		contentPane.add(calendario);

		JLabel lblEstadocivil = new JLabel("Estado civil:");
		lblEstadocivil.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblEstadocivil.setBounds(42, 202, 107, 14);
		contentPane.add(lblEstadocivil);
		
		jtfEstadocivil = new JTextField();
		jtfEstadocivil.setBounds(185, 199, 86, 20);
		contentPane.add(jtfEstadocivil);
		jtfEstadocivil.setColumns(10);

		estadoCivilJComboBox = new JComboBox(estadosCiviles);
		estadoCivil=estadosCiviles[0];
		estadoCivilJComboBox.setBounds(185, 194, 100, 25);
		estadoCivilJComboBox.addItemListener(new ItemListener() // anonymous inner class
		{
			// handle JComboBox event
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					estadoCivil = estadosCiviles[estadoCivilJComboBox.getSelectedIndex()];
				}
			}
		});
		contentPane.add(estadoCivilJComboBox);

		JLabel lblNivelAcademico = new JLabel("Nivel Académico:");
		lblNivelAcademico.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNivelAcademico.setBounds(42, 242, 107, 14);
		contentPane.add(lblNivelAcademico);
		
		jtfNivelAcademico = new JTextField();
		jtfNivelAcademico.setBounds(185, 239, 86, 20);
		contentPane.add(jtfNivelAcademico);
		jtfNivelAcademico.setColumns(10);

		nivelAcademicoJComboBox = new JComboBox(nivelesAcademicos);
		nivelAcademico=nivelesAcademicos[0];
		nivelAcademicoJComboBox.setBounds(185, 234, 100, 25);
		nivelAcademicoJComboBox.addItemListener(new ItemListener() // anonymous inner class
		{
			// handle JComboBox event
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					nivelAcademico = nivelesAcademicos[nivelAcademicoJComboBox.getSelectedIndex()];

				}

			}
		});
		contentPane.add(nivelAcademicoJComboBox);

		lblErrorId = new JLabel("");
		lblErrorId.setForeground(Color.RED);
		lblErrorId.setBounds(275, 24, 300, 14);
		contentPane.add(lblErrorId);

		lblErrorNombre = new JLabel("");
		lblErrorNombre.setForeground(Color.RED);
		lblErrorNombre.setBounds(275, 55, 300, 14);
		contentPane.add(lblErrorNombre);


		lblErrorCiudad = new JLabel("");
		lblErrorCiudad.setForeground(Color.RED);
		lblErrorCiudad.setBounds(275, 130, 300, 14);
		contentPane.add(lblErrorCiudad);

		lblErrorEdad = new JLabel("");
		lblErrorEdad.setForeground(Color.RED);
		lblErrorEdad.setBounds(300, 161, 300, 14);
		contentPane.add(lblErrorEdad);

		Handler handler = new Handler();

		btnInsertar = new JButton("Insertar");
		btnInsertar.setBounds(85, 300, 114, 32); // 202
		contentPane.add(btnInsertar);
		btnInsertar.addActionListener(handler);

		btnModificar = new JButton("Modificar");
		btnModificar.setBounds(85, 300, 114, 32);
		contentPane.add(btnModificar);
		btnModificar.addActionListener(handler);

		btnBorrar = new JButton("Borrar");
		btnBorrar.setBounds(85, 300, 114, 32);
		contentPane.add(btnBorrar);
		btnBorrar.addActionListener(handler);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(225, 300, 114, 32);
		contentPane.add(btnCancelar);
		btnCancelar.addActionListener(handler);
		

		setModal(true);
	}

	public void accion(int accion, Usuario usuario) {

		btnInsertar.setVisible(false);
		btnModificar.setVisible(false);
		btnBorrar.setVisible(false);
		
		jtfGenero.setVisible(false);
		jtfEdad.setVisible(false);
		jtfEstadocivil.setVisible(false);
		jtfNivelAcademico.setVisible(false);
		
		calendario.setVisible(true);
		generoJComboBox.setVisible(true);
		estadoCivilJComboBox.setVisible(true);
		nivelAcademicoJComboBox.setVisible(true);

		jtfNombre.setEditable(true);
		jtfId.setEditable(true);
		jtfGenero.setEditable(false);
		jtfCiudad.setEditable(true);
		jtfEdad.setEditable(false);
        jtfEstadocivil.setEditable(false);
        jtfNivelAcademico.setEditable(false);

		limpiar();

		if (accion == Constantes.INSERTAR) {
			btnInsertar.setVisible(true);
			calendario.setDate(Validation.isDate(LocalDate.now().toString(), "yyyy-MM-dd"));
		}

		if (accion == Constantes.MODIFICAR) {
			btnModificar.setVisible(true);
			jtfId.setEditable(false);
			selectIndexNA(usuario);
			selectIndexG(usuario);
			selectIndexEC(usuario); 
			mostrar(usuario); 
		}

		if (accion == Constantes.BORRAR) {
			btnBorrar.setVisible(true);
			jtfId.setEditable(false);
			jtfNombre.setEditable(false);
			jtfCiudad.setEditable(false);
            
			jtfGenero.setVisible(true);
			jtfEdad.setVisible(true);
			jtfEstadocivil.setVisible(true);
			jtfNivelAcademico.setVisible(true);
			calendario.setVisible(false);
			generoJComboBox.setVisible(false);
			estadoCivilJComboBox.setVisible(false);
			nivelAcademicoJComboBox.setVisible(false);
			mostrar(usuario);
		}

	}
	
	private void mostrar(Usuario usuario) {

		calendario.setDate(Validation.isDate(usuario.getFechaNacimiento().toString(), "yyyy-MM-dd"));
       
		jtfId.setText(usuario.getId());
		jtfNombre.setText(usuario.getNombre());
		jtfGenero.setText(usuario.getGenero().getGenero());
		jtfCiudad.setText(usuario.getCiudad());
		jtfEdad.setText(usuario.getFechaNacimiento().toString());
		jtfEstadocivil.setText(usuario.getEstadoCivil());
		jtfNivelAcademico.setText(usuario.getNivelAcademico());
	}

	private void limpiar() {
		jtfNombre.setText("");
		jtfId.setText("");
		jtfCiudad.setText("");

		lblErrorNombre.setText("");
		lblErrorId.setText("");
		lblErrorCiudad.setText("");
		lblErrorEdad.setText("");

	}

	private class Handler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == btnCancelar)
				coordinador.cancelarUsuario();

			if (event.getSource() == btnBorrar) {
				int resp = JOptionPane.showConfirmDialog(null, "Estás seguro de borrar este registro?", "Confirmar",
						JOptionPane.YES_NO_OPTION);
				if (JOptionPane.OK_OPTION == resp) {
					
					coordinador.borrarUsuario(new Usuario(jtfId.getText(), null, null, null, null, null, null));
					logger.info("Se borro el usuario:"+jtfId.getText()+" del frame");
				return;
				}
			}

			boolean valido = true;

			// validar Id
			String id = jtfId.getText();
			if (id.isEmpty()) {
				lblErrorId.setText("Campo obligatorio");
				valido = false;
			}

			// validar nombre
			String nombre = jtfNombre.getText().trim();
			if (nombre.isEmpty()) {
				lblErrorNombre.setText("Campo obligatorio");
				valido = false;
			} else if (nombre.matches("[A-Z][a-zA-Z]*") != true) {
				lblErrorNombre.setText("Solo letras. Primera con mayúscula");
				valido = false;
			}

			// validar genero
			Gender generoG=null;

				if (genero.contains(" "))
					generoG = Validation.isGenero(genero.substring(0, 2));
				else {
					generoG = Validation.isGenero(genero.substring(0, 1));
				}
			

			// validar ciudad
			String ciudad = jtfCiudad.getText();
			if (ciudad.isEmpty()) {
				lblErrorCiudad.setText("Ciudad no válido");
				valido = false;
			}

			// validar fecha de nacimiento
			LocalDate fechaNacimiento=null;
			if (calendario.getDate() == null) {
				lblErrorEdad.setText("Ingrese una fecha");
				valido = false;
			}
			else {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				fechaDeNac = sdf.format(calendario.getDate());
				fechaNacimiento = Validation.isDate(fechaDeNac);
			}

			if (!valido) {
				logger.error("Campos no válidos!");
				return;
			}
			Usuario usuario = new Usuario(id, nombre, generoG, ciudad, fechaNacimiento, estadoCivil, nivelAcademico);
			if (event.getSource() == btnInsertar) {
				if (coordinador.buscarUsuario(usuario) != null) {
					lblErrorId.setText("Este id ya existe");
					logger.error("Este usuario ya existe");

				} else {
					coordinador.insertarUsuario(usuario);
					logger.info("Se agrego el usuario:"+id+" al frame");
				}
			}
			if (event.getSource() == btnModificar) {
				coordinador.modificarUsuario(usuario);
				logger.info("Se modifico el usuario:"+usuario.getId()+" del frame");
			}
	

		}

	}
	private void selectIndexNA(Usuario u) {
		for(int i=0;i<nivelesAcademicos.length;i++)
			if(nivelesAcademicos[i].equalsIgnoreCase(u.getNivelAcademico())) {
				nivelAcademicoJComboBox.setSelectedIndex(i);
		            return;
			}
	}
	private void selectIndexG(Usuario u) {
		if(u.getGenero().toString().length()==2) {
			generoJComboBox.setSelectedIndex(2);
		    return;
		}
		for(int i=0;i<generos.length;i++)		
			if(generos[i].substring(0, 1).equalsIgnoreCase(u.getGenero().toString())) {
				generoJComboBox.setSelectedIndex(i);
		            return;
			}
	}
	private void selectIndexEC(Usuario u) {
		for(int i=0;i<estadosCiviles.length;i++)
			if(estadosCiviles[i].equalsIgnoreCase(u.getEstadoCivil())) {
				estadoCivilJComboBox.setSelectedIndex(i);
		            return;
			}
	}

	public void setCoordinador(Coordinador coordinador) {
		this.coordinador = coordinador;
	}

}
