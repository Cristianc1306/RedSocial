package rs.gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultCellEditor;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import org.apache.log4j.Logger;

import rs.controlador.Constantes;
import rs.controlador.Coordinador;
import rs.modelo.Usuario;

public class UsuariosList extends JDialog {
	
	final static Logger logger = Logger.getLogger(UsuariosList.class);
	private Coordinador coordinador;
	private int accion;
	private JPanel contentPane;
	private JButton btnInsertar;
	private JScrollPane scrollPane;
	private JTable tableUsuarios;
	private Usuario usuario;

	/**
	 * Create the frame.
	 */
	public UsuariosList() {
		logger.debug("Cargando lista de usuarios");
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(250, 100, 900, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("Usuarios");
		
		btnInsertar = new JButton("Insertar");
		btnInsertar.setBounds(38, 395, 114, 32);
		contentPane.add(btnInsertar);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(38, 25, 800, 350);
		contentPane.add(scrollPane);

		tableUsuarios = new JTable();
		tableUsuarios.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "ID", "Nombre", "Género",
				"Ciudad", "fechaDeN.", "EstadoCiv.", "NivelAcad.", "Modificar", "Borrar" }) {
			boolean[] columnEditables = new boolean[] { false, false, false, false, false, false, false, true, true };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableUsuarios.getColumn("Modificar").setCellRenderer(new ButtonRenderer());
		tableUsuarios.getColumn("Modificar").setCellEditor(new ButtonEditor(new JCheckBox()));
		tableUsuarios.getColumn("Borrar").setCellRenderer(new ButtonRenderer());
		tableUsuarios.getColumn("Borrar").setCellEditor(new ButtonEditor(new JCheckBox()));

		scrollPane.setViewportView(tableUsuarios);
		Handler handler = new Handler();
		btnInsertar.addActionListener(handler);
		setModal(false);
	}
	private class Handler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
		
			if (event.getSource() == btnInsertar)
				coordinador.mostrarInsertarUsuario();
		}
	}

	public void loadTable() {
		((DefaultTableModel) tableUsuarios.getModel()).setRowCount(0);
		for (Usuario u : coordinador.listaUsuarios())
			addRow(u);
	}

	public void addRow(Usuario usu) {
		Object[] row = new Object[tableUsuarios.getModel().getColumnCount()];

		row[0] = usu.getId();
		row[1] = usu.getNombre();
		row[2] = usu.getGenero();
		row[3] = usu.getCiudad();
		row[4] = usu.getFechaNacimiento();
		row[5] = usu.getEstadoCivil();
		row[6] = usu.getNivelAcademico();
		row[7] = "edit";
		row[8] = "drop";
		((DefaultTableModel) tableUsuarios.getModel()).addRow(row);
	}

	private void updateRow(int row) {

		tableUsuarios.setValueAt(usuario.getId(), row, 0);
		tableUsuarios.setValueAt(usuario.getNombre(), row, 1);
		tableUsuarios.setValueAt(usuario.getGenero().toString(), row, 2);
		tableUsuarios.setValueAt(usuario.getCiudad(), row, 3);
		tableUsuarios.setValueAt(usuario.getFechaNacimiento().toString(), row, 4);
		tableUsuarios.setValueAt(usuario.getEstadoCivil(), row, 5);
		tableUsuarios.setValueAt(usuario.getNivelAcademico(), row, 6);
	}

	class ButtonRenderer extends JButton implements TableCellRenderer {

		public ButtonRenderer() {
			setOpaque(true);
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			if (isSelected) {
				setForeground(table.getSelectionForeground());
				setBackground(table.getSelectionBackground());
			} else {
				setForeground(table.getForeground());
				setBackground(UIManager.getColor("Button.background"));
			}
			// setText((value == null) ? "" : value.toString());
			Icon icon = null;
			if (value.toString().equals("edit"))
				icon = new ImageIcon(getClass().getResource("/rs/imagen/b_edit.png"));
			if (value.toString().equals("drop"))
				icon = new ImageIcon(getClass().getResource("/rs/imagen/b_drop.png"));
			setIcon(icon);
			return this;
		}
	}

	class ButtonEditor extends DefaultCellEditor {

		protected JButton button;
		private String label;
		private boolean isPushed;
		private JTable table;
		private boolean isDeleteRow = false;
		private boolean isUpdateRow = false;

		public ButtonEditor(JCheckBox checkBox) {
			super(checkBox);
			button = new JButton();
			button.setOpaque(true);
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					fireEditingStopped();
				}
			});
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {

			if (isSelected) {
				button.setForeground(table.getSelectionForeground());
				button.setBackground(table.getSelectionBackground());
			} else {
				button.setForeground(table.getForeground());
				button.setBackground(table.getBackground());
			}

			label = (value == null) ? "" : value.toString();
			// button.setText(label);
			Icon icon = null;
			if (value.toString().equals("edit"))
				icon = new ImageIcon(getClass().getResource("/rs/imagen/b_edit.png"));
			if (value.toString().equals("drop"))
				icon = new ImageIcon(getClass().getResource("/rs/imagen/b_drop.png"));
			button.setIcon(icon);
			isPushed = true;
			this.table = table;
			isDeleteRow = false;
			isUpdateRow = false;
			
			return button;
		}

		@Override
		public Object getCellEditorValue() {
			if (isPushed) {
				String id = tableUsuarios.getValueAt(tableUsuarios.getSelectedRow(), 0).toString();
				
				Usuario usu = coordinador.buscarUsuario(new Usuario(id,null,null,null,null,null,null));
				if (label.equals("edit"))
					coordinador.mostrarModificarUsuario(usu);
				else
					coordinador.mostrarBorrarUsuario(usu);
			}
			if (accion == Constantes.BORRAR)
				isDeleteRow = true;
			if (accion == Constantes.MODIFICAR)
				isUpdateRow = true;
			if (accion == Constantes.CANCELAR) {
				isDeleteRow=false;
				isUpdateRow=false;
			}
			isPushed = false;
			return new String(label);
		}

		@Override
		public boolean stopCellEditing() {
			isPushed = false;
			return super.stopCellEditing();
		}

		protected void fireEditingStopped() {
			super.fireEditingStopped();

			DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
			if (isDeleteRow)
				tableModel.removeRow(table.getSelectedRow());

			if (isUpdateRow) {
				updateRow(table.getSelectedRow());
			}

		}
	}

	public void setCoordinador(Coordinador coordinador) {
		this.coordinador = coordinador;
	}

	public void setAccion(int accion) {
		this.accion = accion;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
