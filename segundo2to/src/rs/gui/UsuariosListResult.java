package rs.gui;

import java.awt.Font;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;

import rs.controlador.Constantes;
import rs.controlador.Coordinador;
import rs.modelo.Usuario;

/**
 * Lista de usuarios que responden a la consulta hecha
 * @author Camacho, Cristian; Jaime, Cesar
 *
 */
public class UsuariosListResult extends JDialog {

	final static Logger logger = Logger.getLogger(UsuariosListResult.class);
	private Coordinador coordinador;
	private JPanel contentPane;
	private JScrollPane scrollPane;
	private JTable tableUsuariosMetodos;
	private JLabel lblUsuario;

	/**
	 * crea el marco.
	 */
	public UsuariosListResult() {
		logger.debug("Cargando lista de resultados de consultas");
	
		setBounds(300, 175, 756, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblUsuario = new JLabel("");
		lblUsuario.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblUsuario.setBounds(38, 20, 402, 14);
		contentPane.add(lblUsuario);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(38, 45, 673, 300);
		contentPane.add(scrollPane);

		tableUsuariosMetodos = new JTable();
		tableUsuariosMetodos.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "ID", "Nombre", "Género", "Ciudad", "fechaDeN.", "EstadoCiv.", "NivelAcad." }));

		scrollPane.setViewportView(tableUsuariosMetodos);

		tableUsuariosMetodos.setEnabled(false);
	}

	/**
	 * carga la tabla de usuarios
	 * @param usu usuario
	 * @param id1 identificador usuario 1
	 * @param id2 indentificador usuario 2
	 * @param opcion consulta elegida
	 */
	public void loadTable(Usuario usu, String id1, String id2, int opcion) {
		((DefaultTableModel) tableUsuariosMetodos.getModel()).setRowCount(0);
		switch (opcion) {
		case Constantes.MAS_INFLUYENTES:
			modeloDeTabla(Constantes.MAS_INFLUYENTES);

			for (Usuario u : coordinador.listaInfluyentes())
				addRow(u, Integer.toString(coordinador.mostrarCantidadAmigos(u)));

			break;
		case Constantes.CAMINO_MAS_NUEVO:
			modeloDeTabla(Constantes.CAMINO_MAS_NUEVO);
			lblUsuario.setText("El camino más nuevo entre "
					+ coordinador.buscarUsuario(new Usuario(id1, null, null, null, null, null, null)).getNombre()
					+ " y "
					+ coordinador.buscarUsuario(new Usuario(id2, null, null, null, null, null, null)).getNombre()
					+ " es ");
			List<Usuario> u1 = coordinador.listaCaminoMasNuevo(id1, id2);

			addRow(u1.get(0), "  ");

			for (int i = 1; i < u1.size(); i++)
				addRow(u1.get(i),
						Integer.toString(coordinador.mostrarRelacionDeAmistad(u1.get(i), u1.get(i - 1)).getTiempoAmistad())
								+ " años");
			break;
		case Constantes.AMIGOS_DE:
			lblUsuario.setText(
					"Cantidad de amigos de " + usu.getNombre() + " son " + coordinador.listaDeAmigos(usu).size());

			for (Usuario u : coordinador.listaDeAmigos(usu))
				addRow(u, null);

			break;
		case Constantes.DENSIDAD:
			modeloDeTabla(Constantes.DENSIDAD);
			lblUsuario.setText("Usuarios con mas tiempo de interacción de mayor a menor");

			for (Usuario u : coordinador.usuarioMasdensos())
				addRow(u, Integer.toString(coordinador.mostrarInteraccionTotal(u)));

			break;
		case Constantes.SUGERENCIA_AMISTAD:
			lblUsuario.setText("Sugerencias de amistad de " + usu.getNombre());

			for (Usuario u : coordinador.mostrarSugerenciasDeAmitad(usu))
				addRow(u, null);

			break;
		}

	}

	/**
	 * agrega fila de usuario a la tabla
	 * @param usu usuario
	 * @param t titulo de la consulta
	 */
	public void addRow(Usuario usu, String t) {
		Object[] row = new Object[tableUsuariosMetodos.getModel().getColumnCount()];

		row[0] = usu.getId();
		row[1] = usu.getNombre();
		row[2] = usu.getGenero();
		row[3] = usu.getCiudad();
		row[4] = usu.getFechaNacimiento();
		row[5] = usu.getEstadoCivil();
		row[6] = usu.getNivelAcademico();
		if (row.length >= 8)
			row[7] = t;

		((DefaultTableModel) tableUsuariosMetodos.getModel()).addRow(row);
	}

	/**
	 * 
	 * @param coordinador
	 */
	public void setCoordinador(Coordinador coordinador) {
		this.coordinador = coordinador;
	}

	/**
	 * modela la tabla dependiendo de la consulta elegida
	 * @param opcion
	 */
	private void modeloDeTabla(int opcion) {
		switch (opcion) {
		case Constantes.MAS_INFLUYENTES:
			lblUsuario.setText("Los más influyentes de mayor a menor");
			tableUsuariosMetodos.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "ID", "Nombre",
					"Género", "Ciudad", "fechaDeN.", "EstadoCiv.", "NivelAcad.", "Cant.Amigos" }));
			break;
		case Constantes.CAMINO_MAS_NUEVO:
			tableUsuariosMetodos.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "ID", "Nombre",
					"Género", "Ciudad", "fechaDeN.", "EstadoCiv.", "NivelAcad.", "T.Amistad" }));

			break;
		case Constantes.DENSIDAD:
			tableUsuariosMetodos.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "ID", "Nombre",
					"Género", "Ciudad", "fechaDeN.", "EstadoCiv.", "NivelAcad.", "T.Interacción" }));
			break;

		}
	}

}
