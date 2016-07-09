package n3whous3.fleet.db.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
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
	
	public static EntityManagerFactory getEntityManagerFactory()
	{
		return entityManagerFactory;
	}

	public static void deinitialize() {
		entityManagerFactory.close();
	}
}
