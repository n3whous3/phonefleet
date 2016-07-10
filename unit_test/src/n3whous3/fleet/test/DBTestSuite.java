package n3whous3.fleet.test;

import org.junit.AfterClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import n3whous3.fleet.db.util.HibernateUtil;
import n3whous3.fleet.test.db.CombinedCalculationTest;
import n3whous3.fleet.test.db.InvoiceTest;
import n3whous3.fleet.test.db.PaymentTest;
import n3whous3.fleet.test.db.PersonTest;
import n3whous3.fleet.test.db.PhoneTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	PersonTest.class,
	PhoneTest.class,
	InvoiceTest.class,
	PaymentTest.class,
	CombinedCalculationTest.class
})
public class DBTestSuite {
	@AfterClass
	public static void tearDown() {
		HibernateUtil.deinitialize();
	}
}
