package Ventanas;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import Hibernate.Jugadores;
import Singleton.HibernateUtil;

public class Principal {
	public static void leerJugadores() {
		SessionFactory sesion = HibernateUtil.getSessionFactory();
		Session session = sesion.openSession();
		
		Query query = session.createQuery("FROM Jugadores");
		List<Jugadores> jugadores =  query.list();
		
		for (Jugadores jg : jugadores) {
			System.out.println(jg.getCodigo()+"  " +jg.getNombre()+"   "+jg.getProcedencia());
		}
		//Iterator<Jugadores>it 
		
		
	}
public static void main(String args[]) {
	leerJugadores();
}

}
