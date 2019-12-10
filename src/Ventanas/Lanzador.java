package Ventanas;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import Hibernate.Equipos;
import Hibernate.Jugadores;
import Jasper.Jasper;
import Singleton.HibernateUtil;

import javax.swing.JButton;
import java.awt.FlowLayout;
import javax.swing.JTable;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.awt.Canvas;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.UIManager;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Window.Type;
import java.awt.Dialog.ModalExclusionType;
import javax.swing.border.MatteBorder;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Lanzador {

	private JPanel contentPane;
	private JTable table;
	private String [] nombreColumnas= {"Nombre","Ciudad","Division","Conferencia","Jugadores"};
	private ArrayList<String[]>arrayDatos=new ArrayList<String[]>();
	private DefaultTableModel  model;
	private JLabel lbimg	,lbVentanLogo,lbDrch;
	private JFrame frame;
	private ImageIcon LOGO_BASKET=new ImageIcon(Lanzador.class.getResource("/imagenes/logo_basket.png"));
	private ImageIcon BALON=new ImageIcon(Lanzador.class.getResource("/imagenes/balon.png"));
	private ImageIcon FONDO=new ImageIcon(Lanzador.class.getResource("/imagenes/b.jpg"));
	public static  String equipo_name;
	private JTextField txtCiudad;
	private JTextField txtNombre;
	private JTextField txtDivision;
	private JTextField txtConferencia;
	private Color orange= new Color(255, 102, 51);
	private Color blue = new Color(0,102,204);
	/**
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Lanzador  window = new Lanzador();
					 window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public Lanzador() {
		initialize();
	
	}
	/**
	 * Create the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Lanzador.class.getResource("/imagenes/balon.png")));
		frame.setResizable(false);
		frame.setType(Type.POPUP);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 1273, 602);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(51, 102, 204));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel pnListTeams = new JPanel();
		pnListTeams.setBackground(new Color(255, 102, 51));
		pnListTeams.setBounds(238, 0, 744, 572);
		contentPane.add(pnListTeams);
		pnListTeams.setLayout(null);
		
		JPanel pnTop = new JPanel();
		pnTop.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		pnTop.setBackground(new Color(255, 102, 51));
		pnTop.setBounds(10, 11, 725, 54);
		pnListTeams.add(pnTop);
		pnTop.setLayout(null);
		
		JLabel lbTitulo = new JLabel("LISTA  DE EQUIPOS");
		lbTitulo.setHorizontalAlignment(SwingConstants.LEFT);
		lbTitulo.setFont(new Font("Cambria", Font.BOLD, 17));
		lbTitulo.setBackground(new Color(255, 165, 0));
		lbTitulo.setForeground(Color.WHITE);
		lbTitulo.setBounds(284, 11, 166, 32);
		pnTop.add(lbTitulo);
		
		lbimg = new JLabel("");
		lbimg.setBounds(10, 0, 53, 54);
		pnTop.add(lbimg);
		lbimg.setIcon(null);
		
		JPanel pnTable = new JPanel();
		pnTable.setBounds(10, 76, 725, 485);
		pnListTeams.add(pnTable);
		pnTable.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		pnTable.add(scrollPane);
		
		table = new JTable();
		table.setFont(new Font("Calibri", Font.PLAIN, 16));
		table.setBackground(new Color(255, 255, 255));
		scrollPane.setViewportView(table);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		model =new DefaultTableModel(
				new Object[][] {
				},
				 nombreColumnas
			);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null},
			},
			new String[] {
				"Nombre", "Ciudad", "Division", "Conferencia", "Jugadores"
			}
		));
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		pnTable.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eliminarEquipo();
			}
		});
		panel.add(btnEliminar, BorderLayout.EAST);
		
		
		JButton btnCrudJg = new JButton("<html><center>Mostrar todo<br> los jugadores</html>");
		btnCrudJg.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnCrudJg.setBackground(blue);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnCrudJg.setBackground(orange);
			}
		});
		btnCrudJg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Dialog_Jugadores dialog;
				try {
					equipo_name=null;
					dialog = new Dialog_Jugadores(frame,true);
					dialog.setVisible(true);
					mostrarEquipos();
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
		});
	
		btnCrudJg.setForeground(new Color(0, 0, 0));
		btnCrudJg.setBackground(new Color(255, 99, 71));
		btnCrudJg.setFont(new Font("Cambria", Font.PLAIN, 14));
		btnCrudJg.setBounds(39, 214, 162, 57);
		contentPane.add(btnCrudJg);
		
		JButton btnInforme = new JButton("Realizar Informe");
		btnInforme.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnInforme.setBackground(blue);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnInforme.setBackground(orange);
			}
		});
		btnInforme.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Jasper jasper = new Jasper();
				jasper.crearJasper();
			}
		});
		
		btnInforme.setBackground(new Color(255, 99, 71));
		btnInforme.setForeground(new Color(0, 0, 0));
		btnInforme.setFont(new Font("Cambria", Font.PLAIN, 14));
		btnInforme.setBounds(38, 282, 163, 57);
		contentPane.add(btnInforme);
		
		JButton btnMostrarjg = new JButton("<html><center>Mostrar Jugadores <br>por equipo</html>");
		btnMostrarjg.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnMostrarjg.setBackground(blue);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnMostrarjg.setBackground(orange);
			}
		});
		btnMostrarjg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					mostrarJugadores() ;
					mostrarEquipos();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnMostrarjg.setForeground(new Color(0, 0, 0));
		btnMostrarjg.setBackground(new Color(255, 99, 71));
		btnMostrarjg.setFont(new Font("Cambria", Font.PLAIN, 14));
		btnMostrarjg.setBounds(39, 137, 162, 66);
		contentPane.add(btnMostrarjg);
		
		lbVentanLogo = new JLabel("");
		lbVentanLogo.setBounds(10, 11, 218, 115);
		contentPane.add(lbVentanLogo);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(0, 0, 0)));
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setBounds(983, 0, 284, 572);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JButton btnADD = new JButton("ADD");
		btnADD.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnADD.setBackground(blue);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnADD.setBackground(orange);
			}
		});
		
		JButton btnUpdate = new JButton("UPDATE");
		btnUpdate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnUpdate.setBackground(blue);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnUpdate.setBackground(orange);
			}
		});
		
		JLabel lbTitlePnael = new JLabel("Gestion Equipos");
		lbTitlePnael.setFont(new Font("Vogue", Font.BOLD, 16));
		lbTitlePnael.setHorizontalAlignment(SwingConstants.CENTER);
		lbTitlePnael.setBounds(40, 21, 217, 38);
		panel_1.add(lbTitlePnael);
		btnUpdate.setBackground(new Color(255, 102, 0));
		btnUpdate.setBounds(38, 376, 89, 23);
		panel_1.add(btnUpdate);
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actualizarEquipo();
			}
		});
		btnADD.setBackground(new Color(255, 102, 0));
		btnADD.setBounds(156, 376, 89, 23);
		panel_1.add(btnADD);
		btnADD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				crearEquipo();
			}
		});
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new MatteBorder(2, 1, 3, 1, (Color) new Color(0, 0, 0)));
		panel_2.setBackground(new Color(255, 204, 0));
		panel_2.setBounds(40, 165, 217, 95);
		panel_1.add(panel_2);
		panel_2.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(255, 204, 51));
		panel_2.add(panel_3);
		panel_3.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblNewLabel_2 = new JLabel("Nombre");
		lblNewLabel_2.setBackground(new Color(255, 204, 51));
		lblNewLabel_2.setFont(new Font("Cambria", Font.BOLD, 13));
		panel_3.add(lblNewLabel_2);
		
		txtNombre = new JTextField();
		txtNombre.setBackground(new Color(255, 204, 0));
		panel_3.add(txtNombre);
		txtNombre.setColumns(10);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(new Color(255, 204, 51));
		panel_2.add(panel_4);
		panel_4.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblNewLabel_1 = new JLabel("Ciudad");
		lblNewLabel_1.setBackground(new Color(255, 204, 51));
		lblNewLabel_1.setFont(new Font("Cambria", Font.BOLD, 13));
		panel_4.add(lblNewLabel_1);
		
		txtCiudad = new JTextField();
		txtCiudad.setBackground(new Color(255, 204, 0));
		panel_4.add(txtCiudad);
		txtCiudad.setColumns(10);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBackground(new Color(255, 204, 51));
		panel_2.add(panel_6);
		panel_6.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblNewLabel_3 = new JLabel("Division");
		lblNewLabel_3.setBackground(new Color(255, 204, 51));
		lblNewLabel_3.setFont(new Font("Cambria", Font.BOLD, 13));
		panel_6.add(lblNewLabel_3);
		
		txtDivision = new JTextField();
		txtDivision.setBackground(new Color(255, 204, 0));
		panel_6.add(txtDivision);
		txtDivision.setColumns(10);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBackground(new Color(255, 204, 51));
		panel_2.add(panel_5);
		panel_5.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblNewLabel_4 = new JLabel("Conferencia");
		lblNewLabel_4.setBackground(new Color(255, 204, 51));
		lblNewLabel_4.setFont(new Font("Cambria", Font.BOLD, 13));
		panel_5.add(lblNewLabel_4);
		
		txtConferencia = new JTextField();
		txtConferencia.setBackground(new Color(255, 204, 51));
		panel_5.add(txtConferencia);
		txtConferencia.setColumns(10);
		
		lbDrch = new JLabel("");
		lbDrch.setBounds(2, 0, 280, 570);
		panel_1.add(lbDrch);
		ImagenLogo(lbVentanLogo,BALON);
		ImagenLogo(lbDrch,FONDO);
		ImagenLogo(lbimg,LOGO_BASKET);
		
	
		
		
		//mostrarEquipos();
	}
	private void actualizarEquipo() {
		if (table.getSelectedRow() >= 0) {
			String ciudad,conferencia,division;
			SessionFactory sesion = HibernateUtil.getSessionFactory();
			Session session = sesion.openSession();
			Transaction tx = session.beginTransaction();
			Equipos equipo = new Equipos ();
			equipo.setNombre((String) model.getValueAt(table.getSelectedRow(), 0));
			//ciudad
			if(txtCiudad.getText().replace(" ", "").length()>0) {
				ciudad=txtCiudad.getText();
			}
			else {
				ciudad=(String) model.getValueAt(table.getSelectedRow(), 1);
			}
			
			//division
		
			if(txtDivision.getText().replace(" ", "").length()>0) {
				division=txtDivision.getText();
			}
			else {
				division=(String) model.getValueAt(table.getSelectedRow(), 2);
			}
			if(txtConferencia.getText().replace(" ", "").length()>0) {
				conferencia=txtConferencia.getText();
			}
			else {
				conferencia=(String) model.getValueAt(table.getSelectedRow(), 3);
			}
			equipo.setCiudad(ciudad);
			equipo.setConferencia(conferencia);
			equipo.setDivision(division);
			session.update(equipo);
			try {
				tx.commit();
			} catch (ConstraintViolationException e) {
				JOptionPane.showMessageDialog(null,
						e.getMessage() + "\n" + e.getErrorCode() + "\n" + e.getSQLException().getMessage());
			}
			mostrarEquipos();
			session.close();
		} else {
			JOptionPane.showMessageDialog(null, "Debe seleccionar un Jugador primero");
		}
	}
	private void eliminarEquipo() {
		if (table.getSelectedRow() >= 0) {
			SessionFactory sesion = HibernateUtil.getSessionFactory();
			Session session = sesion.openSession();
			Transaction tx = session.beginTransaction();
			Equipos equipo = new Equipos ();
			equipo.setNombre((String) model.getValueAt(table.getSelectedRow(), 0));
			session.delete(equipo);
			try {
				tx.commit();
			} catch (ConstraintViolationException e) {
				JOptionPane.showMessageDialog(null,
						e.getMessage() + "\n" + e.getErrorCode() + "\n" + e.getSQLException().getMessage());
			}
			mostrarEquipos();
			session.close();
		} else {
			JOptionPane.showMessageDialog(null, "Debe seleccionar un Jugador primero");
		}
	}
	private void crearEquipo() {
		String nombre,ciudad,conferencia,division;
		nombre=txtNombre.getText().replace(" ", "");
		ciudad=txtCiudad.getText().replace(" ", "");
		conferencia=txtConferencia.getText().replace(" ", "");
		division=txtDivision.getText().replace(" ", "");
		
		if(!division.isEmpty() && !nombre.isEmpty() && !conferencia.isEmpty() && !ciudad.isEmpty()) {
			Equipos equipo = new Equipos(nombre,ciudad,conferencia,division,null);
			SessionFactory sesion = HibernateUtil.getSessionFactory();
			Session session = sesion.openSession();
			Transaction tx = session.beginTransaction();
			session.save(equipo);
			try {
				tx.commit();
				mostrarEquipos();
			} catch (ConstraintViolationException e) {
				System.out.println("DEPARTAMENTO DUPLICADO");
				System.out.printf("MENSAJE: %s%n", e.getMessage());
				System.out.printf("COD ERROR: %d%n", e.getErrorCode());
				System.out.printf("ERROR SQL: %s%n", e.getSQLException().getMessage());
			}
			session.close();
		}
		else {
			
		} 
	}
	private void mostrarJugadores() throws IOException {
		if (table.getSelectedRow() >= 0) {
			int numeroDeJugadores=Integer.parseInt((String) model.getValueAt(table.getSelectedRow(), 4));
			if(numeroDeJugadores>0) {
				equipo_name=(String) model.getValueAt(table.getSelectedRow(), 0);
				
				Dialog_Jugadores dialog=new Dialog_Jugadores(frame,true);
				dialog.setVisible(true);
			}
			else {
				JOptionPane.showMessageDialog(null, "El equipo no tiene jugadores");
			}
		}
		else {
			JOptionPane.showMessageDialog(null, "Debe seleccionar primero un equipo de la tabla para visualizar sus jugadores");
		}
	}
	
	private void ImagenLogo(JLabel label,ImageIcon img) {
		Icon icon = new ImageIcon(img.getImage().getScaledInstance(label.getWidth(), label.getHeight(),
				Image.SCALE_DEFAULT));
		label.setIcon(icon);
	}
	
	public Equipos recogerEquipo() {
		
		SessionFactory sesion = HibernateUtil.getSessionFactory();
		Session session = sesion.openSession();
		Equipos equipo ;

		Query query = session.createQuery("FROM Equipos WHERE Nombre="+"'"+equipo_name+"'");
		
		equipo= (Equipos) query.uniqueResult();				
		
		session.close();
		return equipo;
		
	}

	
	private void mostrarEquipos() {
		arrayDatos.clear();
		
		model.setRowCount(0);
		table.setModel(model);

		
		leerEquipos();
				
		for(String[] data :arrayDatos) {
			model.addRow(data);
		}
		
		table.setModel(model);
	}
	public void leerEquipos() {
		
			SessionFactory sesion = HibernateUtil.getSessionFactory();
			Session session = sesion.openSession();
			
			Query query = session.createQuery("FROM Equipos");
			List<Equipos> listEquipos =  query.list();
			Iterator<Equipos >it=listEquipos.iterator();
			while(it.hasNext()) {
				Equipos  team=it.next();
				String[] data= {
						team.getNombre(),team.getCiudad(),team.getDivision(),team.getConferencia(),team.getJugadoreses().size()+""
				};
				arrayDatos.add(data);
				
			}
		
	}
}
