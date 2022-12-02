package rs.gui;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import rs.controlador.Coordinador;

/**
 * Marco
 * @author Camacho, Cristian; Jaime, Cesar
 *
 */
public class DesktopFrame extends JFrame {

	final static Logger logger = Logger.getLogger(DesktopFrame.class);

	private JComboBox metodosJComboBox;
	private String metodos[] = { "    ", "Grado Promedio", "Los más influyentes", "El camino más nuevo",
			"Tiempo de amistad", "Amigos de", "Densidad", "Sugerencia de Amistad", "El que más interactúa" };
	private Coordinador coordinador;
	private JMenuItem mntmNewMenuItem1;
	private JMenuItem mntmNewMenuItem2;

/**
 * constructor marco.
 * Despliega el menú, y el combobox con las funcionalidades
 */
	public DesktopFrame() {

		logger.debug("Cargando panel principal");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		Handler handler = new Handler();

		JMenu mnNewMenu = new JMenu("Ver");
		menuBar.add(mnNewMenu);

		mntmNewMenuItem1 = new JMenuItem("Usuarios");
		mntmNewMenuItem1.addActionListener(handler);

		mnNewMenu.add(mntmNewMenuItem1);

		mntmNewMenuItem2 = new JMenuItem("Relaciones");
		mntmNewMenuItem2.addActionListener(handler);

		mnNewMenu.add(mntmNewMenuItem2);

		Fondo fondo = new Fondo();
		setContentPane(fondo);

		setSize(600, 480);
		setTitle("Red Social");
		setLocationRelativeTo(null);
		setResizable(false);
		setLayout(null);

		JLabel lblFun = new JLabel("FUNCIONALIDADES");
		lblFun.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblFun.setBounds(175, 50, 250, 50);
		add(lblFun);

		metodosJComboBox = new JComboBox(metodos);
		metodosJComboBox.setMaximumRowCount(8);
		metodosJComboBox.setBounds(185, 100, 175, 30);
		metodosJComboBox.addItemListener(new ItemListener() {
			
			public void itemStateChanged(ItemEvent event) {

				if (event.getStateChange() == ItemEvent.SELECTED && metodosJComboBox.getSelectedIndex()!=0) {

				    coordinador.mostrarConsultasUsuarios(metodosJComboBox.getSelectedIndex(), metodos[metodosJComboBox.getSelectedIndex()]);

				    metodosJComboBox.setSelectedIndex(0);
				}
				
			}

		});

		add(metodosJComboBox);

	}

	/**
	 * manejador de eventos del menú del marco
	 * @author usuario
	 *
	 */
	private class Handler implements ActionListener {
		public void actionPerformed(ActionEvent event) {

			if (event.getSource() == mntmNewMenuItem1)
				coordinador.verUsuarios();

			if (event.getSource() == mntmNewMenuItem2)
				coordinador.verRelaciones();

		}
	}

	/**
	 * fondo del marco
	 * @author usuario
	 *
	 */
	private class Fondo extends JPanel {
		public void paint(Graphics g) {
			ImageIcon imagen = new ImageIcon(getClass().getResource("/rs/imagen/fondo.jpg"));
			g.drawImage(imagen.getImage(), 0, 0, getWidth(), getHeight(), null);
			setOpaque(false);
			super.paint(g);
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
