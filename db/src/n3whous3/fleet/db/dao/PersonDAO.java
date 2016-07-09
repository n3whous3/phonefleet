package n3whous3.fleet.db.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import n3whous3.fleet.db.entities.Person;
import n3whous3.fleet.db.entities.Phone;

public class PersonDAO extends BaseDAO
{
	
	public static void addPerson(Person person) throws Exception {
		persistObject(person);
	}
	
	public static void updatePerson(Person person) throws Exception {
		updateObject(person);
	}
	
	public static void removePerson(Person person) throws Exception {
		removeObject(person);
	}
	
	public static List<Person> getAllPersons() throws Exception {
		return singleTableQuery(Person.class);
	}
	
	public static List<Person> getPersonsByNamePart(String namePart, int minLength) throws Exception {
		List<Person> ret = null;
		if(namePart.length() >= minLength) {
			ret = singleTableQuery(Person.class, new SingleTableQueryCondition<Person>() {
				public void body(CriteriaBuilder critBuilder, CriteriaQuery<Person> criteria, Root<Person> root) {
					criteria.where(critBuilder.like(root.get("name"), "%"+namePart+"%"));
				}
			});
		}
		return ret;
	}
	
	public static List<Person> getPersonsByCardNumPart(String cardNumPart, int minLength) throws Exception {
		List<Person> ret = null;
		if(cardNumPart.length() >= minLength) {
			ret = singleTableQuery(Person.class, new SingleTableQueryCondition<Person>() {
				public void body(CriteriaBuilder critBuilder, CriteriaQuery<Person> criteria, Root<Person> root) {
					criteria.where(critBuilder.like(root.get("card_num"), "%"+cardNumPart+"%"));
				}
			});
		}
		return ret;
	}
	
	public static List<Person> getPersonsByPhoneNum(String phoneNumPart, int minLength) throws Exception {
		List<Person> ret = null;
		if(phoneNumPart.length() >= minLength) {
			List<Phone> phonesWithNumPartFound = PhoneDAO.getPhonesByPhoneNum(phoneNumPart, minLength);
			// TODO : can be the result nullptr?
			ret = new ArrayList<Person>(phonesWithNumPartFound.size());
			for(Phone phoneFound : phonesWithNumPartFound) {
				ret.add(phoneFound.getPerson());
			}
		}
		return ret;
	}
	
}
