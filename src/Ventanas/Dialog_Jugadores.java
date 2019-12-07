package Ventanas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import Hibernate.Equipos;
import Hibernate.Jugadores;
import Singleton.HibernateUtil;

import java.awt.Dialog.ModalExclusionType;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.awt.Dialog.ModalityType;
import java.awt.Window.Type;
import java.awt.Toolkit;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;

public class Dialog_Jugadores extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;

	private DefaultTableModel model;
	private ImageIcon NBA = new ImageIcon(Lanzador.class.getResource("/imagenes/NBA.png"));
	private ImageIcon Logo = new ImageIcon(Lanzador.class.getResource("/imagenes/prueba.png"));
	private JLabel lbImg, lb_logo, lbNombreEquipo, lbDivisionEquipo, lbCiudadEquipo, lbConferenciaEquipo;
	private JLabel lbNombreTeam, lbDivision, lbCiudad, lbConferencia, lbAlert;
	private String[] nombreColumnas = { "Codigo", "Nombre", "Procedencia", "Altura", "Peso", "Posicion" };
	private ArrayList<String> comboEquipos = new ArrayList<String>();
	private ArrayList<String[]> arrayDatos = new ArrayList<String[]>();
	private JTextField txtCodigo;
	private JTextField txtNombre;
	private JTextField txtProcedencia;
	private JTextField txtAltura;
	private JTextField txtPeso;
	private JTextField txtPosicion;
	JComboBox comboBox;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the dialog.
	 * 
	 * @throws IOException
	 */
	public Dialog_Jugadores(JFrame parent, boolean mode) throws IOException {
		super(parent, mode);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Dialog_Jugadores.class.getResource("/imagenes/balon.png")));
		setType(Type.POPUP);
		setModalityType(ModalityType.APPLICATION_MODAL);
		getContentPane().setBackground(new Color(0, 0, 0));
		setUndecorated(true);
		setResizable(false);
		setLocationRelativeTo(parent);
		setBounds(100, 100, 1008, 576);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(51, 102, 204));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JPanel pnTable = new JPanel();
		pnTable.setBounds(5, 139, 711, 398);
		contentPanel.add(pnTable);
		pnTable.setLayout(new BorderLayout(0, 0));

		JPanel panel_5 = new JPanel();
		pnTable.add(panel_5, BorderLayout.SOUTH);
		panel_5.setLayout(new BorderLayout(0, 0));

		JButton btnEliminar = new JButton("Eliminar");
		panel_5.add(btnEliminar, BorderLayout.EAST);
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					eliminarJugador();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane();
		pnTable.add(scrollPane);

		table = new JTable();
		table.setBackground(new Color(255, 51, 153));
		table.setModel(new DefaultTableModel(new Object[][] {

		}, new String[] { "Codigo", "Nombre", "Procedencia", "Altura", "Peso", "Posicion" }));
		scrollPane.setViewportView(table);

		lbImg = new JLabel();

		lbImg.setBounds(24, 1, 248, 125);
		contentPanel.add(lbImg);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(305, 23, 288, 90);
		contentPanel.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 2, 0, 0));

		JPanel panel = new JPanel();
		panel.setBackground(new Color(51, 102, 204));
		panel_1.add(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));

		lbNombreTeam = new JLabel("NOMBRE");
		lbNombreTeam.setBackground(Color.BLACK);
		panel.add(lbNombreTeam);
		lbNombreTeam.setForeground(Color.WHITE);

		lbCiudad = new JLabel("CIUDAD");
		lbCiudad.setBackground(Color.BLACK);
		panel.add(lbCiudad);
		lbCiudad.setForeground(Color.WHITE);

		lbDivision = new JLabel("DIVISION");
		lbDivision.setBackground(Color.BLACK);
		panel.add(lbDivision);
		lbDivision.setForeground(Color.WHITE);

		lbConferencia = new JLabel("CONFERENCIA");
		lbConferencia.setBackground(Color.BLACK);
		panel.add(lbConferencia);
		lbConferencia.setForeground(Color.WHITE);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(51, 102, 204));
		panel_1.add(panel_2);
		panel_2.setLayout(new GridLayout(0, 1, 0, 0));

		lbNombreEquipo = new JLabel("");
		lbNombreEquipo.setForeground(Color.WHITE);
		lbNombreEquipo.setBackground(Color.BLACK);
		panel_2.add(lbNombreEquipo);

		lbCiudadEquipo = new JLabel("");
		lbCiudadEquipo.setForeground(Color.WHITE);
		lbCiudadEquipo.setBackground(Color.BLACK);
		panel_2.add(lbCiudadEquipo);

		lbDivisionEquipo = new JLabel("");
		lbDivisionEquipo.setForeground(Color.WHITE);
		lbDivisionEquipo.setBackground(Color.BLACK);
		panel_2.add(lbDivisionEquipo);

		lbConferenciaEquipo = new JLabel("");
		lbConferenciaEquipo.setForeground(Color.WHITE);
		lbConferenciaEquipo.setBackground(Color.BLACK);
		panel_2.add(lbConferenciaEquipo);

		JPanel pnTop = new JPanel();
		pnTop.setBackground(new Color(0, 0, 0));
		getContentPane().add(pnTop, BorderLayout.NORTH);
		pnTop.setLayout(new BorderLayout(0, 0));

		JButton cancelButton = new JButton("x");
		cancelButton.setBackground(new Color(255, 0, 102));
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getParent().setVisible(true);
				dispose();
			}
		});
		pnTop.add(cancelButton, BorderLayout.EAST);
		cancelButton.setActionCommand("Cancel");
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		model = new DefaultTableModel(new Object[][] {}, nombreColumnas);
		table.setModel(new DefaultTableModel(new Object[][] {}, nombreColumnas));

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(204, 51, 102));
		panel_3.setBorder(new MatteBorder(3, 1, 3, 1, (Color) new Color(0, 0, 0)));
		panel_3.setBounds(722, 139, 283, 398);
		contentPanel.add(panel_3);
		panel_3.setLayout(null);

		lb_logo = new JLabel("");
		lb_logo.setBackground(new Color(255, 102, 102));
		lb_logo.setBounds(46, 13, 195, 58);
		panel_3.add(lb_logo);

		JLabel lblNewLabel_1 = new JLabel("Codigo");
		lblNewLabel_1.setBounds(35, 97, 48, 14);
		panel_3.add(lblNewLabel_1);

		txtCodigo = new JTextField();
		txtCodigo.setBounds(113, 94, 150, 20);
		panel_3.add(txtCodigo);
		txtCodigo.setColumns(10);

		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(35, 125, 48, 14);
		panel_3.add(lblNombre);

		txtNombre = new JTextField();
		txtNombre.setColumns(10);
		txtNombre.setBounds(113, 122, 150, 20);
		panel_3.add(txtNombre);

		JLabel lblProcedencia = new JLabel("Procedencia");
		lblProcedencia.setBounds(35, 153, 81, 14);
		panel_3.add(lblProcedencia);

		txtProcedencia = new JTextField();
		txtProcedencia.setColumns(10);
		txtProcedencia.setBounds(113, 150, 150, 20);
		panel_3.add(txtProcedencia);

		JLabel lblAltura = new JLabel("Altura");
		lblAltura.setBounds(35, 181, 48, 14);
		panel_3.add(lblAltura);

		txtAltura = new JTextField();
		txtAltura.setColumns(10);
		txtAltura.setBounds(113, 178, 150, 20);
		panel_3.add(txtAltura);

		JLabel lblPeso = new JLabel("Peso");
		lblPeso.setBounds(35, 209, 48, 14);
		panel_3.add(lblPeso);

		txtPeso = new JTextField();
		txtPeso.setColumns(10);
		txtPeso.setBounds(113, 206, 150, 20);
		panel_3.add(txtPeso);

		JLabel lblPosicion = new JLabel("Posicion");
		lblPosicion.setBounds(35, 237, 48, 14);
		panel_3.add(lblPosicion);

		txtPosicion = new JTextField();
		txtPosicion.setColumns(10);
		txtPosicion.setBounds(113, 234, 150, 20);
		panel_3.add(txtPosicion);

		JButton btnAdd = new JButton("ADD");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				añadirJugadores();
			}
		});
		btnAdd.setBackground(new Color(0, 153, 204));
		btnAdd.setBounds(155, 352, 108, 33);
		panel_3.add(btnAdd);

		JButton btnActualizar = new JButton("Actualizar");
		btnActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actualizarJugador();
			}
		});
		btnActualizar.setBackground(new Color(0, 153, 204));
		btnActualizar.setBounds(35, 352, 108, 33);
		panel_3.add(btnActualizar);

		JLabel lblEquipo = new JLabel("Equipo");
		lblEquipo.setBounds(35, 265, 48, 14);
		panel_3.add(lblEquipo);

		JPanel panel_4 = new JPanel();
		panel_4.setBackground(UIManager.getColor("infoText"));
		getContentPane().add(panel_4, BorderLayout.SOUTH);
		ImagenIn(lb_logo, Logo);
		ImagenIn(lbImg, NBA);

		comboBox = new JComboBox();
		comboBox.setBounds(113, 261, 150, 22);
		panel_3.add(comboBox);

		lbAlert = new JLabel("");
		lbAlert.setVerticalAlignment(SwingConstants.TOP);
		lbAlert.setForeground(new Color(255, 0, 51));
		lbAlert.setBounds(35, 290, 238, 49);
		panel_3.add(lbAlert);
		cargarComboBox();
		mostrarJugadores();

	}

	private void actualizarJugador() {
		if (table.getSelectedRow() >= 0) {
			
			String nombre, procedencia ,altura,peso,posicion;
			
			Jugadores jugador = new Jugadores();
			int num = Integer.parseInt((String) model.getValueAt(table.getSelectedRow(), 0));
			jugador.setCodigo(num);
			try {
				if(txtNombre.getText().isEmpty()) {
					nombre=(String) model.getValueAt(table.getSelectedRow(), 1);
				}
				else {
					nombre= txtNombre.getText().toUpperCase();
				}
				if( txtProcedencia.getText().isEmpty()) {
					procedencia = (String) model.getValueAt(table.getSelectedRow(), 2);
				}
				else {
					procedencia = txtProcedencia.getText().replace(" ", "").toUpperCase();
				}
				
				if(txtAltura.getText().isEmpty()) {
					altura=(String) model.getValueAt(table.getSelectedRow(), 3);
				}
				else {
					altura = txtAltura.getText().replace(" ", "").toUpperCase();
				}
				if(txtPeso.getText().isEmpty()) {
					peso=(String) model.getValueAt(table.getSelectedRow(), 4);
				}
				else {
					peso = txtPeso.getText().replace(" ", "").toUpperCase();
				}
				if(txtPosicion.getText().isEmpty()) {
					posicion=(String) model.getValueAt(table.getSelectedRow(), 5);
				}
				else {
					posicion = txtPosicion.getText().replace(" ", "").toUpperCase();
				}
				String equipo = comboBox.getSelectedItem().toString();
				jugador.setNombre(nombre);
				jugador.setProcedencia(procedencia);
				jugador.setAltura(altura);
				jugador.setPeso(Integer.parseInt(peso));
				jugador.setPosicion(posicion);
				jugador.setEquipos(GetEquipo(equipo));
				SessionFactory sesion = HibernateUtil.getSessionFactory();
				Session session = sesion.openSession();
				Transaction tx = session.beginTransaction();
				
				session.update(jugador);
				try {
					tx.commit();
					mostrarJugadores();
				} catch (ConstraintViolationException e) {
					JOptionPane.showMessageDialog(null,
							e.getMessage() + "\n" + e.getErrorCode() + "\n" + e.getSQLException().getMessage());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

				}
				session.close();
			}
			catch(NumberFormatException ex) {
				
			}
	
			
		} else {
			JOptionPane.showMessageDialog(null, "Debe seleccionar un Jugador primero");
		}
	}

	private void eliminarJugador() throws IOException {
		if (table.getSelectedRow() >= 0) {
			SessionFactory sesion = HibernateUtil.getSessionFactory();
			Session session = sesion.openSession();
			Transaction tx = session.beginTransaction();
			Jugadores jugador = new Jugadores();
			int num = Integer.parseInt((String) model.getValueAt(table.getSelectedRow(), 0));
			jugador.setCodigo(num);
			session.delete(jugador);
			try {
				tx.commit();
			} catch (ConstraintViolationException e) {
				JOptionPane.showMessageDialog(null,
						e.getMessage() + "\n" + e.getErrorCode() + "\n" + e.getSQLException().getMessage());
			}
			mostrarJugadores();
			session.close();
		} else {
			JOptionPane.showMessageDialog(null, "Debe seleccionar un Jugador primero");
		}
	}

	private void añadirJugadores() {
		String codigo = txtCodigo.getText().replace(" ", "").toUpperCase();
		String nombre = txtNombre.getText().toUpperCase();
		String procedencia = txtProcedencia.getText().replace(" ", "").toUpperCase();
		String altura = txtAltura.getText().replace(" ", "").toUpperCase();
		String peso = txtPeso.getText().replace(" ", "").toUpperCase();
		String posicion = txtPosicion.getText().replace(" ", "").toUpperCase();

		String equipo = comboBox.getSelectedItem().toString();
		String alert = "";
		if (!codigo.isEmpty() && !nombre.isEmpty() && !procedencia.isEmpty() && !altura.isEmpty() && !peso.isEmpty()
				&& !posicion.isEmpty() && !equipo.isEmpty()) {
			Jugadores jugador;
			boolean isValid = true;
			int code = 0, libras = 0;
			try {
				code = Integer.parseInt(codigo);

			} catch (NumberFormatException ex) {
				alert = alert + "El valor codigo es incorrecto";
				isValid = false;

			}
			try {

			} catch (NumberFormatException ex) {
				alert = alert + "El valor de la altura es incorrecto";
				isValid = false;

			}
			try {
				libras = Integer.parseInt(peso);
			} catch (NumberFormatException ex) {
				isValid = false;
				alert = alert + "El valor de el peso es incorrecto";

			}
			if (isValid) {
				jugador = new Jugadores(code, GetEquipo(equipo), nombre, procedencia, altura, libras, posicion);
				insertarJugador(jugador);
			} else {
				lbAlert.setText(alert);
			}

		} else {
			JOptionPane.showMessageDialog(null, "No puede dejar ningun cuadro vacio");
		}
	}

	private void insertarJugador(Jugadores jugador) {
		SessionFactory sesion = HibernateUtil.getSessionFactory();
		Session session = sesion.openSession();
		Transaction tx = session.beginTransaction();
		session.save(jugador);
		try {
			tx.commit();
		} catch (ConstraintViolationException e) {
			System.out.println("DEPARTAMENTO DUPLICADO");
			System.out.printf("MENSAJE: %s%n", e.getMessage());
			System.out.printf("COD ERROR: %d%n", e.getErrorCode());
			System.out.printf("ERROR SQL: %s%n", e.getSQLException().getMessage());
		}
		try {
			mostrarJugadores();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
	}

	private Equipos GetEquipo(String name) {
		SessionFactory sesion = HibernateUtil.getSessionFactory();
		Session session = sesion.openSession();
		Equipos equipo;

		Query query = session.createQuery("FROM Equipos WHERE Nombre=" + "'" + name + "'");

		equipo = (Equipos) query.uniqueResult();

		session.close();
		return equipo;
	}

	private void ImagenIn(JLabel label, ImageIcon img) {
		Icon icon = new ImageIcon(
				img.getImage().getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_DEFAULT));
		label.setIcon(icon);
	}

	private void mostrarJugadores() throws IOException {
		arrayDatos.clear();

		model.setRowCount(0);
		table.setModel(model);

		obtenerEquipo();

		for (String[] data : arrayDatos) {
			model.addRow(data);
		}

		table.setModel(model);
	}

	public void cargarComboBox() {
		SessionFactory sesion = HibernateUtil.getSessionFactory();
		Session session = sesion.openSession();
		Query query = session.createQuery("SELECT E.nombre FROM Equipos E");
		List<String> names_equipos = query.list();
		Iterator<String> it = names_equipos.iterator();
		while (it.hasNext()) {
			String name = it.next();
			comboEquipos.add(name);

		}
		for (String n : comboEquipos) {
			comboBox.addItem(n);
		}

	}

	public int getIndice(String team) {
		int indice = 0;
		for (int i = 0; i < comboEquipos.size(); i++) {
			if (team.equals(comboBox.getSelectedItem().toString())) {
				indice = i;
				break;
			}
		}
		return indice;
	}

	public void obtenerEquipo() throws IOException {
		Lanzador l = new Lanzador();
		Equipos equipo = l.recogerEquipo();

		SessionFactory sesion = HibernateUtil.getSessionFactory();
		Session session = sesion.openSession();
		if (equipo != null) {
			comboBox.setSelectedItem(equipo.getNombre());
			// comboBox.setSelectedIndex(getIndice(equipo.getNombre()));
			comboBox.setEnabled(false);
			lbNombreEquipo.setText(equipo.getNombre());
			lbCiudadEquipo.setText(equipo.getCiudad());
			lbConferenciaEquipo.setText(equipo.getConferencia());
			lbDivisionEquipo.setText(equipo.getDivision());

			Query query = session.createQuery("FROM Jugadores where equipos =" + "'" + equipo.getNombre() + "'");

			List<Jugadores> deparamentos = query.list();
			Iterator<Jugadores> it = deparamentos.iterator();
			while (it.hasNext()) {
				Jugadores jugador = it.next();
				String[] data = { jugador.getCodigo() + "", jugador.getNombre(), jugador.getProcedencia(),
						jugador.getAltura(), jugador.getPeso() + "", jugador.getPosicion() };
				arrayDatos.add(data);
			}

			/*
			 * mediante esta opcion los tendriamos sin tener que entras nuevamenten en la
			 * base de datos pero tendriamops que programar que nos los ordene de manera
			 * aumentativa
			 * 
			 * for (Jugadores j : equipo.getJugadoreses()) {
			 * System.out.println(j.toString()); }
			 */
		} else {
			lbNombreTeam.setVisible(false);
			lbDivision.setVisible(false);
			lbCiudad.setVisible(false);
			lbConferencia.setVisible(false);
			comboBox.setEnabled(true);
			Query query = session.createQuery("FROM Jugadores");

			List<Jugadores> deparamentos = query.list();
			Iterator<Jugadores> it = deparamentos.iterator();
			while (it.hasNext()) {
				Jugadores jugador = it.next();
				String[] data = { jugador.getCodigo() + "", jugador.getNombre(), jugador.getProcedencia(),
						jugador.getAltura(), jugador.getPeso() + "", jugador.getPosicion() };
				arrayDatos.add(data);
			}
		}

		session.close();
	}
}
