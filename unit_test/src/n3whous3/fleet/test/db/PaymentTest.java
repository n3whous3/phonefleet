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

import n3whous3.fleet.db.dao.PaymentDAO;
import n3whous3.fleet.db.dao.PersonDAO;
import n3whous3.fleet.db.dao.PhoneDAO;
import n3whous3.fleet.db.entities.Payment;
import n3whous3.fleet.db.entities.Person;
import n3whous3.fleet.db.entities.Phone;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PaymentTest {
	
	private SimpleDateFormat sdf;
	private static Phone phone1ToUseElsewhere;
	private static Phone phone2ToUseElsewhere;
	
	public PaymentTest() {
		sdf = new SimpleDateFormat("dd/MM/yyyy");
	}
	
	private Date getDate(String strDate) throws ParseException {
		return new Date(sdf.parse(strDate).getTime());
	}
	
	@BeforeClass
	public static void setup() throws Exception {
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
	}
	
	@Test
	public void test001_AddInvoices() throws Exception {
		List<Payment> payments = new ArrayList<>();
		payments.add(new Payment(phone1ToUseElsewhere, getDate("03/01/2016"), 2000, 200));
		payments.add(new Payment(phone1ToUseElsewhere, getDate("05/02/2016"), 2400, 200));
		payments.add(new Payment(phone1ToUseElsewhere, getDate("06/03/2016"), 2100, 300));
		payments.add(new Payment(phone1ToUseElsewhere, getDate("04/04/2016"), 2600, 300));
		
		payments.add(new Payment(phone2ToUseElsewhere, getDate("03/01/2016"), 1900, 200));
		payments.add(new Payment(phone2ToUseElsewhere, getDate("04/02/2016"), 1900, 200));
		payments.add(new Payment(phone2ToUseElsewhere, getDate("06/03/2016"), 1700, 300));
		payments.add(new Payment(phone2ToUseElsewhere, getDate("04/04/2016"), 1800, 300));
		
		for(Payment aPayment : payments) {
			PaymentDAO.addPayment(aPayment);
		}
		Assert.assertEquals(payments.size(), PaymentDAO.getAllPayments().size());
	}
	
	@Test
	public void test002_RemoveInvoice() throws Exception {
		Phone phone = PhoneDAO.getAllPhones().get(0);
		Payment paym = new Payment(phone, getDate("05/10/2016"), 1, 1);
		PaymentDAO.addPayment(paym);
		int numInvoices = PaymentDAO.getAllPayments().size();
		PaymentDAO.removePayment(paym);
		Assert.assertEquals(numInvoices-1, PaymentDAO.getAllPayments().size());
	}
	
	@Test
	public void test003_InvoiceSearchAndDeleteAllForAPhone() throws Exception {
		Phone phone = PhoneDAO.getAllPhones().get(2);
		List<Payment> payments = new ArrayList<>();
		payments.add(new Payment(phone, getDate("06/10/2016"), 1, 1));
		payments.add(new Payment(phone, getDate("07/11/2016"), 2, 2));
		payments.add(new Payment(phone, getDate("08/12/2016"), 3, 3));
		for(Payment paym : payments) {
			PaymentDAO.addPayment(paym);
		}
		Assert.assertEquals(payments.size(), PaymentDAO.getAllPayments(phone).size());
		PaymentDAO.removeAllPayments(phone);
		Assert.assertEquals(0, PaymentDAO.getAllPayments(phone).size());
	}
	
	@Test
	public void test004_InvoiceSearchByDate() throws Exception {
		Assert.assertEquals(4, PaymentDAO.getAllPayments(getDate("12/02/2016")).size());
		Assert.assertEquals(2, PaymentDAO.getAllPayments(phone1ToUseElsewhere, getDate("12/02/2016")).size());
	}
	
	@AfterClass
	public static void tearDown() throws Exception {
		for(Phone phone : PhoneDAO.getAllPhones()) {
			PaymentDAO.removeAllPayments(phone);
		}
		for(Phone phone : PhoneDAO.getAllPhones()) {
			PhoneDAO.removePhone(phone);
		}
		for(Person person : PersonDAO.getAllPersons()) {
			PersonDAO.removePerson(person);
		}
	}
	
}
