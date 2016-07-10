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
import n3whous3.fleet.db.dao.PersonDAO;
import n3whous3.fleet.db.dao.PhoneDAO;
import n3whous3.fleet.db.entities.Invoice;
import n3whous3.fleet.db.entities.Person;
import n3whous3.fleet.db.entities.Phone;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InvoiceTest {
	
	private SimpleDateFormat sdf;
	private static Phone phone1ToUseElsewhere;
	private static Phone phone2ToUseElsewhere;
	
	public InvoiceTest() {
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
		List<Invoice> invoices = new ArrayList<>();
		invoices.add(new Invoice(phone1ToUseElsewhere, getDate("01/01/2016"), 2000, 200));
		invoices.add(new Invoice(phone1ToUseElsewhere, getDate("02/02/2016"), 2400, 200));
		invoices.add(new Invoice(phone1ToUseElsewhere, getDate("04/03/2016"), 2100, 300));
		invoices.add(new Invoice(phone1ToUseElsewhere, getDate("02/04/2016"), 2600, 300));
		
		invoices.add(new Invoice(phone2ToUseElsewhere, getDate("01/01/2016"), 1900, 200));
		invoices.add(new Invoice(phone2ToUseElsewhere, getDate("02/02/2016"), 1900, 200));
		invoices.add(new Invoice(phone2ToUseElsewhere, getDate("04/03/2016"), 1700, 300));
		invoices.add(new Invoice(phone2ToUseElsewhere, getDate("02/04/2016"), 1800, 300));
		
		for(Invoice anInvoice : invoices) {
			InvoiceDAO.addInvoice(anInvoice);
		}
		Assert.assertEquals(invoices.size(), InvoiceDAO.getAllInvoices().size());
	}
	
	@Test
	public void test002_RemoveInvoice() throws Exception {
		Phone phone = PhoneDAO.getAllPhones().get(0);
		Invoice inv = new Invoice(phone, getDate("04/10/2016"), 1, 1);
		InvoiceDAO.addInvoice(inv);
		int numInvoices = InvoiceDAO.getAllInvoices().size();
		InvoiceDAO.removeInvoice(inv);
		Assert.assertEquals(numInvoices-1, InvoiceDAO.getAllInvoices().size());
	}
	
	@Test
	public void test003_InvoiceSearchAndDeleteAllForAPhone() throws Exception {
		Phone phone = PhoneDAO.getAllPhones().get(2);
		List<Invoice> invoices = new ArrayList<>();
		invoices.add(new Invoice(phone, getDate("04/10/2016"), 1, 1));
		invoices.add(new Invoice(phone, getDate("05/11/2016"), 2, 2));
		invoices.add(new Invoice(phone, getDate("06/12/2016"), 3, 3));
		for(Invoice inv : invoices) {
			InvoiceDAO.addInvoice(inv);
		}
		Assert.assertEquals(invoices.size(), InvoiceDAO.getAllInvoices(phone).size());
		InvoiceDAO.removeAllInvoices(phone);
		Assert.assertEquals(0, InvoiceDAO.getAllInvoices(phone).size());
	}
	
	@Test
	public void test004_InvoiceSearchByDate() throws Exception {
		Assert.assertEquals(4, InvoiceDAO.getAllInvoices(getDate("10/02/2016")).size());
		Assert.assertEquals(2, InvoiceDAO.getAllInvoices(phone1ToUseElsewhere, getDate("10/02/2016")).size());
	}
	
	@AfterClass
	public static void tearDown() throws Exception {
		for(Phone phone : PhoneDAO.getAllPhones()) {
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
