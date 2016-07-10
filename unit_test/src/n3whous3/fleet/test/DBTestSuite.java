package n3whous3.fleet.test;

import org.junit.AfterClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import n3whous3.fleet.db.util.HibernateUtil;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	PersonTest.class,
	PhoneTest.class
})
public class DBTestSuite {
	@AfterClass
	public static void tearDown() {
		HibernateUtil.deinitialize();
	}
}
