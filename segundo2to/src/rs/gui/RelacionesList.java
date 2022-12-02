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
import rs.modelo.Relacion;
import rs.modelo.Usuario;

/**
 * Clase listado de relaciones
 * @author Jaime, Cesar; Camacho, Cristian
 *
 */
public class RelacionesList extends JDialog {
	
	final static Logger logger = Logger.getLogger(RelacionesList .class);
	private Coordinador coordinador;
	private JPanel contentPane;
	private JScrollPane scrollPane;
	private JButton btnInsertar;
	private JTable tableRelaciones;
	private int accion;
	private Relacion relacion;

	/**
	 * Crea el marco.
	 */
	public RelacionesList() {
        logger.debug("Cargando lista de relaciones");
        
		setBounds(275, 125, 850, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("Relaciones");
		
		btnInsertar = new JButton("Insertar");
		btnInsertar.setBounds(38, 400, 114, 32);
		contentPane.add(btnInsertar);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(38, 25, 750, 350);
		contentPane.add(scrollPane);

		tableRelaciones = new JTable();
		tableRelaciones.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Nombre", "Id1", "Nombre",
				"Id2", "Interacción", "Likes", "FechaDeA.", "Modificar", "Borrar" }) {
			boolean[] columnEditables = new boolean[] { false, false, false, false, false, false, false, true, true };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableRelaciones.getColumn("Modificar").setCellRenderer(new ButtonRenderer());
		tableRelaciones.getColumn("Modificar").setCellEditor(new ButtonEditor(new JCheckBox()));
		tableRelaciones.getColumn("Borrar").setCellRenderer(new ButtonRenderer());
		tableRelaciones.getColumn("Borrar").setCellEditor(new ButtonEditor(new JCheckBox()));

		scrollPane.setViewportView(tableRelaciones);
		Handler handler = new Handler();
		btnInsertar.addActionListener(handler);
		setModal(false);

	}
	
	/**
	 * manejador de eventos
	 * @author usuario
	 *
	 */
	private class Handler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
		
			if (event.getSource() == btnInsertar)
				coordinador.mostrarInsertarRelacion();
		}
	}

	/**
	 * carga la tabla
	 */
	public void loadTable() {
		((DefaultTableModel) tableRelaciones.getModel()).setRowCount(0);
		for (Relacion r : coordinador.listaRelaciones())
			addRow(r);
	}

	/**
	 * agrega una fila a la tabla con los datos de la relacion
	 * @param relacion
	 */
	public void addRow(Relacion relacion) {
		Object[] row = new Object[tableRelaciones.getModel().getColumnCount()];

		row[0] = relacion.getUsuario1().getNombre();
		row[1] = relacion.getUsuario1().getId();
		row[2] = relacion.getUsuario2().getNombre();
		row[3] = relacion.getUsuario2().getId();
		row[4] = relacion.getInteraccion();
		row[5] = relacion.getLikes();
		row[6] = relacion.getFechaAmistad();
		row[7] = "edit";
		row[8] = "drop";
		((DefaultTableModel) tableRelaciones.getModel()).addRow(row);
	}

	/**
	 * actualiza la fila de la tabla de relaciones
	 * @param row
	 */
	private void updateRow(int row) {

		tableRelaciones.setValueAt(relacion.getUsuario1().getNombre(), row, 0);
		tableRelaciones.setValueAt(relacion.getUsuario1().getId(), row, 1);
		tableRelaciones.setValueAt(relacion.getUsuario2().getNombre(), row, 2);
		tableRelaciones.setValueAt(relacion.getUsuario2().getId(), row, 3);
		tableRelaciones.setValueAt(Integer.toString(relacion.getInteraccion()), row, 4);
		tableRelaciones.setValueAt(Integer.toString(relacion.getLikes()), row, 5);
		tableRelaciones.setValueAt(relacion.getFechaAmistad().toString(), row, 6);

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
				String id1 = tableRelaciones.getValueAt(tableRelaciones.getSelectedRow(), 1).toString();
				String id2 = tableRelaciones.getValueAt(tableRelaciones.getSelectedRow(), 3).toString();
				Relacion rel = coordinador
						.buscarRelacion(new Relacion(new Usuario(id1, null, null, null, null, null, null),
								new Usuario(id2, null, null, null, null, null, null), 0, 0, null));
				if (label.equals("edit"))
					coordinador.mostrarModifcarRelacion(rel);
				else
					coordinador.mostrarBorrarRelacion(rel);
			}
			if (accion == Constantes.BORRAR)
				isDeleteRow = true;
			if (accion == Constantes.MODIFICAR)
				isUpdateRow = true;
			if (accion == Constantes.CANCELAR) {
				isDeleteRow = false;
				isUpdateRow = false;
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

	/**
	 * establece accion
	 * @param accion
	 */
	public void setAccion(int accion) {
		this.accion = accion;
	}

	/**
	 * establece relacion
	 * @param relacion
	 */
	public void setRelacion(Relacion relacion) {
		this.relacion = relacion;
	}

	/**
	 * establece coordinador
	 * @param coordinador
	 */
	public void setCoordinador(Coordinador coordinador) {
		this.coordinador = coordinador;
	}

}
