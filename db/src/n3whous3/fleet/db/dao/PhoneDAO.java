package n3whous3.fleet.db.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import n3whous3.fleet.db.entities.Person;
import n3whous3.fleet.db.entities.Phone;

public class PhoneDAO extends BaseDAO {
	
	public static void addPhone(Phone phone) throws Exception {
		persistObject(phone);
	}
	
	public static void removePhone(Phone phone) throws Exception {
		removeObject(phone);
	}
	
	public static List<Phone> getAllPhones() throws Exception {
		return singleTableQuery(Phone.class);
	}
	
	public static List<Phone> getPhonesByPhoneNum(String phoneNumPart, int minLength) throws Exception {
		List<Phone> ret = null;
		if(phoneNumPart.length() >= minLength) {
			ret = singleTableQuery(Phone.class, new SingleTableQueryCondition<Phone>() {
				public void body(CriteriaBuilder critBuilder, CriteriaQuery<Phone> criteria, Root<Phone> root) {
					criteria.where(critBuilder.like(root.get("phone"), "%"+phoneNumPart+"%"));
				}
			});
		}
		return ret;
	}
	
	public static Phone getPhoneByPerson(Person person) throws Exception {
		List<Phone> phones = singleTableQuery(Phone.class, new SingleTableQueryCondition<Phone>() {
			public void body(CriteriaBuilder critBuilder, CriteriaQuery<Phone> criteria, Root<Phone> root) {
				criteria.where(root.get("person").in(person));
			}
		});
		assert phones.size() < 2;
		return phones.isEmpty() ? null : phones.get(0);
	}

}
