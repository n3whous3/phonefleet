package n3whous3.fleet.test;

import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;

import n3whous3.fleet.db.dao.PersonDAO;
import n3whous3.fleet.db.entities.Person;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PersonTest {
	
	@Test
	public void test001_personsInsert() throws Exception {
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
	public void test002_personSearch() throws Exception {
		Assert.assertNull(PersonDAO.getPersonsByCardNumPart("a", 2));
		Assert.assertEquals(1, PersonDAO.getPersonsByCardNumPart("1111", 0).size());
		Assert.assertEquals("Újházi Béla", PersonDAO.getPersonsByCardNumPart("1111", 0).get(0).getName());
		Assert.assertEquals(2, PersonDAO.getPersonsByCardNumPart("11", 0).size());
		Assert.assertEquals(2, PersonDAO.getPersonsByNamePart("jházi", 0).size());
	}
	
	@Test
	public void test003_personUpdate() throws Exception {
		Person perNew = new Person("1234","New person", "address@mail.hu", "My address");
		PersonDAO.addPerson(perNew);
		String oldAddress = perNew.getAddress();
		String newAddress = "New utca 1.";
		perNew.setAddress(newAddress);
		PersonDAO.updatePerson(perNew);
		Person perAgain = PersonDAO.getPersonsByCardNumPart(perNew.getCard_num(), 0).get(0);
		Assert.assertNotEquals(oldAddress, perAgain.getAddress());
		Assert.assertEquals(newAddress, perAgain.getAddress());
		PersonDAO.removePerson(perNew);
	}
	
	@Test
	public void test004_personRemove() throws Exception {
		List<Person> personsInDB = PersonDAO.getAllPersons();
		for(Person p : personsInDB) {
			PersonDAO.removePerson(p);
		}
		Assert.assertEquals(0, PersonDAO.getAllPersons().size());
	}
	
}
