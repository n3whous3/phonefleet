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
	
	public static void addPerson(Person person) throws Exception
	{
		try {
			persistObject(person);
		} catch (Exception e) {
			// TODO logging
			throw e;
		}
	}
	
	public static void updatePerson(Person person) throws Exception
	{
		try {
			updateObject(person);
		} catch (Exception e) {
			// TODO logging
			throw e;
		}
	}
	
	public static void deletePerson(Person person) throws Exception
	{
		try {
			removeObject(person);
		} catch (Exception e) {
			// TODO logging
			throw e;
		}
	}
	
	public static List<Person> getAllPersons() throws Exception
	{
		try {
			return singleTableQuery(Person.class, new SingleTableQueryCondition<Person>() {
				public void body(CriteriaBuilder critBuilder, CriteriaQuery<Person> criteria, Root<Person> root) {
					// no extra restriction required for basic query
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		}
	}
	
	public static List<Person> getPersonsByNamePart(String namePart, int minLength) throws Exception
	{
		List<Person> ret = null;
		if(namePart.length() >= minLength) {
			try {
				ret = singleTableQuery(Person.class, new SingleTableQueryCondition<Person>() {
					public void body(CriteriaBuilder critBuilder, CriteriaQuery<Person> criteria, Root<Person> root) {
						criteria.where(critBuilder.like(root.get("name"), "%"+namePart+"%"));
					}
				});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				throw e;
			}
		}
		return ret;
	}
	
	public static List<Person> getPersonsByCardNumPart(String cardNumPart, int minLength) throws Exception
	{
		List<Person> ret = null;
		if(cardNumPart.length() >= minLength) {
			try {
				ret = singleTableQuery(Person.class, new SingleTableQueryCondition<Person>() {
					public void body(CriteriaBuilder critBuilder, CriteriaQuery<Person> criteria, Root<Person> root) {
						criteria.where(critBuilder.like(root.get("card_num"), "%"+cardNumPart+"%"));
					}
				});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				throw e;
			}
		}
		return ret;
	}
	
	public static List<Person> getPersonsByPhoneNum(String phoneNumPart, int minLength) throws Exception
	{
		List<Person> ret = null;
		if(phoneNumPart.length() >= minLength) {
			try {
				List<Phone> phonesWithNumPartFound = PhoneDAO.getPhonesByPhoneNum(phoneNumPart, minLength);
				// TODO : can be the result nullptr?
				ret = new ArrayList<Person>(phonesWithNumPartFound.size());
				for(Phone phoneFound : phonesWithNumPartFound) {
					ret.add(phoneFound.getPerson());
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				throw e;
			}
		}
		return ret;
	}
	
}
