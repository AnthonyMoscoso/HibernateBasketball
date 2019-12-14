package Ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import Hibernate.Equipos;
import Hibernate.Estadisticas;
import Hibernate.Jugadores;
import Singleton.HibernateUtil;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.SystemColor;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Dialog_Estadisticas extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private DefaultTableModel model;

	private String[] nombreColumnasporJugador = { "Temporada", "Asistencias", "Puntos", "Rebotes", "Tapones" };
	private String[] nombreColumnas = { "Jugador", "Temporada", "Asistencias", "Puntos", "Rebotes", "Tapones" };

	private ArrayList<String[]> arrayDatos = new ArrayList<String[]>();

	private boolean isOnlyPlayer = false;
	private Jugadores jugador;
	private JTextField txtBuscar;

	public Dialog_Estadisticas(JFrame parent, boolean mode) {
		super(parent, mode);
		setUndecorated(true);
		setBounds(100, 100, 861, 612);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(0, 102, 204));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel pnBusqueda = new JPanel();
		pnBusqueda.setBackground(new Color(0, 102, 204));
		contentPanel.add(pnBusqueda, BorderLayout.NORTH);
		pnBusqueda.setLayout(new BorderLayout(0, 0));
		
		JPanel pnBordeTop = new JPanel();
		pnBordeTop.setBackground(new Color(0, 102, 204));
		pnBusqueda.add(pnBordeTop, BorderLayout.NORTH);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(null);
		panel_2.setBackground(new Color(0, 102, 204));
		pnBusqueda.add(panel_2, BorderLayout.EAST);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[] {20, 96, 20, 0};
		gbl_panel_2.rowHeights = new int[]{20, 0};
		gbl_panel_2.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		JButton btnCancelarBusqueda = new JButton("X");
		JButton btnSearch = new JButton("");
		btnSearch.setIcon(new ImageIcon(Dialog_Estadisticas.class.getResource("/imagenes/search.png")));
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String code =txtBuscar.getText();
				if(!code.isEmpty()) {
					if(code.equals("")==false&&code.matches("[0-9]*")) {
						buscarJugador(Integer.parseInt(code));
						btnCancelarBusqueda.setVisible(true);
					}
					
				}
				
			}
		});
		btnSearch.setBackground(new Color(0, 102, 204));
		GridBagConstraints gbc_btnSearch = new GridBagConstraints();
		gbc_btnSearch.fill = GridBagConstraints.BOTH;
		gbc_btnSearch.insets = new Insets(0, 0, 0, 5);
		gbc_btnSearch.gridx = 0;
		gbc_btnSearch.gridy = 0;
		panel_2.add(btnSearch, gbc_btnSearch);
	
		btnCancelarBusqueda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarEstadisticas();
				txtBuscar.setText(null);
				btnCancelarBusqueda.setVisible(false);
			}
		});
		btnCancelarBusqueda.setVisible(false);
		txtBuscar = new JTextField();
		txtBuscar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String code =txtBuscar.getText();
				if(!code.isEmpty()) {
					btnCancelarBusqueda.setVisible(true);
					if(code.equals("")==false&&code.matches("[0-9]*")) {
						buscarJugadoresDinamicamente(Integer.parseInt(code));
					}
					
				}
				else {
					btnCancelarBusqueda.setVisible(false);
					mostrarEstadisticas();
				}
				
				
			}
		});
		txtBuscar.setBackground(new Color(0, 102, 204));
		txtBuscar.setForeground(Color.WHITE);
		txtBuscar.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		GridBagConstraints gbc_txtBuscar = new GridBagConstraints();
		gbc_txtBuscar.fill = GridBagConstraints.BOTH;
		gbc_txtBuscar.insets = new Insets(0, 0, 0, 5);
		gbc_txtBuscar.gridx = 1;
		gbc_txtBuscar.gridy = 0;
		panel_2.add(txtBuscar, gbc_txtBuscar);
		txtBuscar.setColumns(10);
		
		
		btnCancelarBusqueda.setForeground(Color.WHITE);
		btnCancelarBusqueda.setBackground(new Color(0, 102, 204));
		GridBagConstraints gbc_btnCancelarBusqueda = new GridBagConstraints();
		gbc_btnCancelarBusqueda.fill = GridBagConstraints.BOTH;
		gbc_btnCancelarBusqueda.gridx = 2;
		gbc_btnCancelarBusqueda.gridy = 0;
		panel_2.add(btnCancelarBusqueda, gbc_btnCancelarBusqueda);
		
		JPanel pnBorderDowm = new JPanel();
		pnBorderDowm.setBackground(new Color(0, 102, 204));
		pnBusqueda.add(pnBorderDowm, BorderLayout.SOUTH);

		JPanel pntable = new JPanel();
		contentPanel.add(pntable);
		pntable.setLayout(new BorderLayout(0, 0));

		JPanel pnDowm = new JPanel();
		pntable.add(pnDowm, BorderLayout.SOUTH);
		pnDowm.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		pntable.add(scrollPane);

		table = new JTable();
		table.setFont(new Font("Cambria", Font.BOLD, 14));
		table.setBackground(Color.WHITE);

		scrollPane.setViewportView(table);

		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JPanel buttonPane = new JPanel();
		buttonPane.setBackground(new Color(0, 0, 0));
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.NORTH);

		JButton btnBack = new JButton("X");
		btnBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnBack.setBackground(new Color(153,53,53));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnBack.setBackground(new Color(0,0,0));
			}
		});
		btnBack.setBackground(new Color(0, 0, 0));
		btnBack.setFont(new Font("Cambria", Font.BOLD, 14));
		btnBack.setForeground(new Color(255, 255, 255));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				getParent().setVisible(true);
				dispose();
			}
		});

		jugador = GetJugador();
		if (jugador != null) {
			isOnlyPlayer = true;
		}
		if (!isOnlyPlayer) {
			model = new DefaultTableModel(new Object[][] {}, nombreColumnas);
			table.setModel(new DefaultTableModel(new Object[][] {}, nombreColumnas));
			pnBusqueda.setVisible(true);
		} else {
			model = new DefaultTableModel(new Object[][] {}, nombreColumnasporJugador);
			table.setModel(new DefaultTableModel(new Object[][] {}, nombreColumnasporJugador));
			pnBusqueda.setVisible(false);
		}
		btnBack.setActionCommand("Cancel");
		buttonPane.add(btnBack);
		mostrarEstadisticas();

	}
	private void buscarJugador(int numero) {
		arrayDatos.clear();

		model.setRowCount(0);
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Jugador", "New column", "New column", "New column", "New column", "New column" }));

	
		

		table.setModel(model);
		SessionFactory sesion = HibernateUtil.getSessionFactory();// Abrimos la conexion
		Session session = sesion.openSession();
		
			Query query = session.createQuery("FROM Estadisticas where Jugador ="+numero );

			List<Estadisticas> Estadisticas = query.list();
			Iterator<Estadisticas> it = Estadisticas.iterator();
		
			while (it.hasNext()) {
				Estadisticas estadistica = it.next();
				String[] data = { estadistica.getJugadores().getNombre(), estadistica.getId().getTemporada(),
						estadistica.getAsistenciasPorPartido() + "", estadistica.getPuntosPorPartido() + "",
						estadistica.getRebotesPorPartido() + "", estadistica.getTaponesPorPartido() + "" };
				arrayDatos.add(data);
			}
			for (String[] data : arrayDatos) {
				model.addRow(data);
			}
	}
	public void buscarJugadoresDinamicamente(int numero) {
		arrayDatos.clear();

		model.setRowCount(0);
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Jugador", "New column", "New column", "New column", "New column", "New column" }));

	
		

		table.setModel(model);
		SessionFactory sesion = HibernateUtil.getSessionFactory();// Abrimos la conexion
		Session session = sesion.openSession();
		
			Query query = session.createQuery("FROM Estadisticas where Jugador Like '"+numero+"%'" );

			List<Estadisticas> Estadisticas = query.list();
			Iterator<Estadisticas> it = Estadisticas.iterator();
		
			while (it.hasNext()) {
				Estadisticas estadistica = it.next();
				String[] data = { estadistica.getJugadores().getNombre(), estadistica.getId().getTemporada(),
						estadistica.getAsistenciasPorPartido() + "", estadistica.getPuntosPorPartido() + "",
						estadistica.getRebotesPorPartido() + "", estadistica.getTaponesPorPartido() + "" };
				arrayDatos.add(data);
			}
			for (String[] data : arrayDatos) {
				model.addRow(data);
			}

	}
	
	
	public Jugadores GetJugador() {
		Dialog_Jugadores d=null;
		try {
			d = new Dialog_Jugadores(new JFrame (),false);
		} catch (HeadlessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Jugadores jugador= d.recogerJugadores();
		return jugador;
	}

	public void mostrarEstadisticas() {
		arrayDatos.clear();

		model.setRowCount(0);
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Jugador", "New column", "New column", "New column", "New column", "New column" }));

		leerEstadisticas();

		for (String[] data : arrayDatos) {
			model.addRow(data);
		}

		table.setModel(model);
	}
	
	public void leerEstadisticas() {
		if (jugador != null) {
			SessionFactory sesion = HibernateUtil.getSessionFactory();// Abrimos la conexion
			Session session = sesion.openSession();
			Query query = session.createQuery("FROM Estadisticas where Jugador =" + jugador.getCodigo());

			List<Estadisticas> Estadisticas = query.list();
			Iterator<Estadisticas> it = Estadisticas.iterator();
			// mediante un Iterator , cargamos los datos en nuestra tabla
			while (it.hasNext()) {
				Estadisticas estadistica = it.next();
				String[] data = { estadistica.getId().getTemporada(), estadistica.getAsistenciasPorPartido() + "",
						estadistica.getPuntosPorPartido() + "", estadistica.getRebotesPorPartido() + "",
						estadistica.getTaponesPorPartido() + "" };
				arrayDatos.add(data);
			}
		} else {
			SessionFactory sesion = HibernateUtil.getSessionFactory();// Abrimos la conexion
			Session session = sesion.openSession();
			Query query = session.createQuery("FROM Estadisticas");

			List<Estadisticas> Estadisticas = query.list();
			Iterator<Estadisticas> it = Estadisticas.iterator();
			// mediante un Iterator , cargamos los datos en nuestra tabla
			while (it.hasNext()) {
				Estadisticas estadistica = it.next();
				String[] data = { estadistica.getJugadores().getNombre(), estadistica.getId().getTemporada(),
						estadistica.getAsistenciasPorPartido() + "", estadistica.getPuntosPorPartido() + "",
						estadistica.getRebotesPorPartido() + "", estadistica.getTaponesPorPartido() + "" };
				arrayDatos.add(data);
			};
		}

	}
}
