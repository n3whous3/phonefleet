package n3whous3.fleet.test.db;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import n3whous3.fleet.db.dao.InvoiceDAO;
import n3whous3.fleet.db.dao.PaymentDAO;
import n3whous3.fleet.db.dao.PersonDAO;
import n3whous3.fleet.db.dao.PhoneDAO;
import n3whous3.fleet.db.dao.StaticInfoDAO;
import n3whous3.fleet.db.entities.Invoice;
import n3whous3.fleet.db.entities.Payment;
import n3whous3.fleet.db.entities.Person;
import n3whous3.fleet.db.entities.Phone;
import n3whous3.fleet.db.entities.StaticInfo;
import n3whous3.fleet.db.logic.BalanceCalculator;
import n3whous3.fleet.db.logic.Values;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CombinedCalculationTest {
	
	private static SimpleDateFormat sdf;
	private static Phone phone1ToUseElsewhere;
	private static Phone phone2ToUseElsewhere;
	
	private static Date getDate(String strDate) throws ParseException {
		return new Date(sdf.parse(strDate).getTime());
	}
	
	@BeforeClass
	public static void setup() throws Exception {
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		List<Person> persons = new ArrayList<>();
		Person personToUseElsewhere = new Person("1111", "Újházi Béla", "bela@valami.hu", "Test utca 1.");
		persons.add(personToUseElsewhere);
		Person person2ToUseElsewhere = new Person("2112", "Újházi-Szabó Marianna", "marica@valami.hu", "Test utca 1.");
		persons.add(person2ToUseElsewhere);
		persons.add(new Person("3322", "Test János", null, null));
		persons.add(new Person(null, "Edd Meg József", "eddmeg@mailbox.hu", null));
		int num = 70_100_2020;
		for(Person p : persons) {
			PersonDAO.addPerson(p);
			Phone newPhone = new Phone(String.valueOf(num), p);
			num += 100;
			PhoneDAO.addPhone(newPhone);
		}
		Assert.assertEquals(persons.size(), PersonDAO.getAllPersons().size());
		Assert.assertEquals(persons.size(), PhoneDAO.getAllPhones().size());
		phone1ToUseElsewhere = PhoneDAO.getPhoneByPerson(personToUseElsewhere);
		Assert.assertNotNull(phone1ToUseElsewhere);
		phone2ToUseElsewhere = PhoneDAO.getPhoneByPerson(person2ToUseElsewhere);
		Assert.assertNotNull(phone2ToUseElsewhere);
		
		// invoices
		List<Invoice> invoices = new ArrayList<>();
		invoices.add(new Invoice(phone1ToUseElsewhere, getDate("01/01/2016"), 2000, 200));
		invoices.add(new Invoice(phone1ToUseElsewhere, getDate("02/02/2016"), 2400, 200));
		invoices.add(new Invoice(phone1ToUseElsewhere, getDate("04/03/2016"), 2100, 300));
		invoices.add(new Invoice(phone1ToUseElsewhere, getDate("02/04/2016"), 2600, 300));
		
		invoices.add(new Invoice(phone2ToUseElsewhere, getDate("01/01/2016"), 1900, 200));
		invoices.add(new Invoice(phone2ToUseElsewhere, getDate("02/02/2016"), 1900, 200));
		invoices.add(new Invoice(phone2ToUseElsewhere, getDate("04/03/2016"), 1700, 300));
		invoices.add(new Invoice(phone2ToUseElsewhere, getDate("02/04/2016"), 1800, 300));
		invoices.add(new Invoice(phone2ToUseElsewhere, getDate("03/05/2016"), 1900, 300));
		
		for(Invoice anInvoice : invoices) {
			InvoiceDAO.addInvoice(anInvoice);
		}
		Assert.assertEquals(invoices.size(), InvoiceDAO.getAllInvoices().size());
		
		// payments
		List<Payment> payments = new ArrayList<>();
		payments.add(new Payment(phone1ToUseElsewhere, getDate("03/01/2016"), 2000, 200));
		payments.add(new Payment(phone1ToUseElsewhere, getDate("05/02/2016"), 2400, 200));
		payments.add(new Payment(phone1ToUseElsewhere, getDate("06/03/2016"), 2100, 300));
		payments.add(new Payment(phone1ToUseElsewhere, getDate("04/04/2016"), 2600, 0));
		
		payments.add(new Payment(phone2ToUseElsewhere, getDate("03/01/2016"), 1900, 400));
		payments.add(new Payment(phone2ToUseElsewhere, getDate("04/02/2016"), 1900, 0));
		payments.add(new Payment(phone2ToUseElsewhere, getDate("06/03/2016"), 1700, 0));
		payments.add(new Payment(phone2ToUseElsewhere, getDate("04/04/2016"), 1800, 0));
		payments.add(new Payment(phone2ToUseElsewhere, getDate("05/05/2016"), 1900, 500));
		
		for(Payment aPayment : payments) {
			PaymentDAO.addPayment(aPayment);
		}
		Assert.assertEquals(payments.size(), PaymentDAO.getAllPayments().size());
		
		StaticInfo s1 = new StaticInfo(getDate("01/10/2015"), 200, 400);
		StaticInfo s2 = new StaticInfo(getDate("01/03/2016"), 300, 500);
		StaticInfoDAO.addStaticInfo(s1);
		StaticInfoDAO.addStaticInfo(s2);
	}
	
	@Test
	public void test001_Balance() throws Exception {
		BalanceCalculator bc = new BalanceCalculator(phone1ToUseElsewhere);
		Values balance1 = bc.calculateBalance(getDate("03/04/2016"));
		Values balance2 = bc.calculateBalance(getDate("04/04/2016"));
		Assert.assertEquals(2600, balance1.phoneDebt);
		Assert.assertEquals(300, balance1.monthlyFee);
		Assert.assertEquals(0, balance2.phoneDebt);
		Assert.assertEquals(300, balance2.monthlyFee);
	}
	
	@Test
	public void test002_RequiredPayment() throws Exception {
		// 0 monthly fee, because overdue
		BalanceCalculator bc = new BalanceCalculator(phone2ToUseElsewhere);
		Values val = bc.calculateRequiredPayment(getDate("02/02/2016"));
		Assert.assertEquals(1900, val.phoneDebt);
		Assert.assertEquals(0, val.monthlyFee);
		
		// everything is ok
		val = bc.calculateRequiredPayment(getDate("05/02/2016"));
		Assert.assertEquals(0, val.phoneDebt);
		Assert.assertEquals(0, val.monthlyFee);
		
		// 600 debt in monthly fee + 500 threshold -> 500 required monthly fee payment
		// and the current phone invoice debt of course
		val = bc.calculateRequiredPayment(getDate("03/05/2016"));
		Assert.assertEquals(1900, val.phoneDebt);
		Assert.assertEquals(500, val.monthlyFee);
		
		// 400 debt in monthly fee + 500 threshold -> 400 required payment
		val = bc.calculateRequiredPayment(getDate("05/05/2016"));
		Assert.assertEquals(0, val.phoneDebt);
		Assert.assertEquals(400, val.monthlyFee);
	}
	
	@AfterClass
	public static void tearDown() throws Exception {
		for(Phone phone : PhoneDAO.getAllPhones()) {
			PaymentDAO.removeAllPayments(phone);
			InvoiceDAO.removeAllInvoices(phone);
		}
		for(Phone phone : PhoneDAO.getAllPhones()) {
			PhoneDAO.removePhone(phone);
		}
		for(Person person : PersonDAO.getAllPersons()) {
			PersonDAO.removePerson(person);
		}
	}
	
}
