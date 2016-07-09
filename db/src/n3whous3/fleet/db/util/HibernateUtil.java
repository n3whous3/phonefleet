package n3whous3.fleet.db.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static final SessionFactory sessionFactory = buildSessionFactory();
	private static final EntityManagerFactory entityManagerFactory = buildEntityManagerFactory();
	
	private static EntityManagerFactory buildEntityManagerFactory() throws ExceptionInInitializerError
	{
		try
		{
			return Persistence.createEntityManagerFactory("fleet_manager");
		}
		catch(Throwable th)
		{
			System.out.println("EntityManagerFactory could not be created: " + th.toString());
			throw new ExceptionInInitializerError(th);
		}
	}
	private static SessionFactory buildSessionFactory() throws ExceptionInInitializerError {
		try {
			// Create the SessionFactory from hibernate.cfg.xml
			return new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			// TODO message or something somewhere
			System.out.println("Ajjajj: " + ex.toString());
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public static EntityManagerFactory getEntityManagerFactory()
	{
		return entityManagerFactory;
	}
}
