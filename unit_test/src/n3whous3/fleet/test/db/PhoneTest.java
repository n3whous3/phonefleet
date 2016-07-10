package n3whous3.fleet.test.db;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import n3whous3.fleet.db.dao.PersonDAO;
import n3whous3.fleet.db.dao.PhoneDAO;
import n3whous3.fleet.db.entities.Person;
import n3whous3.fleet.db.entities.Phone;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PhoneTest {

	@BeforeClass
	public static void setup() throws Exception {
		List<Person> persons = new ArrayList<>();
		persons.add(new Person("1111", "Újházi Béla", "bela@valami.hu", "Test utca 1."));
		persons.add(new Person("2112", "Újházi-Szabó Marianna", "marica@valami.hu", "Test utca 1."));
		persons.add(new Person("3322", "Test János", null, null));
		persons.add(new Person(null, "Edd Meg József", "eddmeg@mailbox.hu", null));
		for(Person p : persons) {
			PersonDAO.addPerson(p);
		}
		Assert.assertEquals(persons.size(), PersonDAO.getAllPersons().size());
	}
	
	@Test
	public void test001_phoneInsert() throws Exception {
		int num = 10_000;
		List<Person> persons = PersonDAO.getAllPersons();
		Assert.assertTrue(!persons.isEmpty());
		for(Person aPerson : persons) {
			Phone newPhone = new Phone(String.valueOf(num), aPerson);
			num += 100;
			PhoneDAO.addPhone(newPhone);
		}
		Assert.assertEquals(persons.size(), PhoneDAO.getAllPhones().size());
	}
	
	@Test
	public void test002_phoneQuery() throws Exception {
		Person existing = PersonDAO.getAllPersons().get(0);
		Assert.assertEquals(existing, PhoneDAO.getPhoneByPerson(existing).getPerson());
		Assert.assertNull(PhoneDAO.getPhonesByPhoneNum("1", 5));
		Assert.assertEquals(0, PhoneDAO.getPhonesByPhoneNum("10101", 1).size());
		Assert.assertEquals(1, PhoneDAO.getPhonesByPhoneNum("10100", 1).size());
	}
	
	@Test
	public void test003_phoneRemoves() throws Exception {
		List<Phone> allPhones = PhoneDAO.getAllPhones();
		for(Phone phone : allPhones) {
			PhoneDAO.removePhone(phone);
		}
		Assert.assertEquals(0, PhoneDAO.getAllPhones().size());
	}
	
	@AfterClass
	public static void tearDown() throws Exception {
		for(Person p : PersonDAO.getAllPersons()) {
			PersonDAO.removePerson(p);
		}
	}
	
}
