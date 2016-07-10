package n3whous3.fleet.db.dao;

import java.sql.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import n3whous3.fleet.db.entities.Payment;
import n3whous3.fleet.db.entities.Phone;

public class PaymentDAO extends BaseDAO {
	
	public static void addPayment(Payment payment) throws Exception {
		persistObject(payment);
	}
	
	public static void removePayment(Payment payment) throws Exception {
		removeObject(payment);
	}
	
	public static void removeAllPayments(Phone phone) throws Exception {
		removeObjects(getAllPayments(phone));
	}
	
	public static List<Payment> getAllPayments() throws Exception {
		return singleTableQuery(Payment.class);
	}
	
	public static List<Payment> getAllPayments(Date untilDate) throws Exception {
		return singleTableQuery(Payment.class, new SingleTableQueryCondition<Payment>() {
			public void body(CriteriaBuilder critBuilder, CriteriaQuery<Payment> criteria, Root<Payment> root) {
				criteria.where(critBuilder.lessThanOrEqualTo(root.get("validity_date"), untilDate));
			}
		});
	}
	
	public static List<Payment> getAllPayments(Phone phone) throws Exception {
		return singleTableQuery(Payment.class, new SingleTableQueryCondition<Payment>() {
			public void body(CriteriaBuilder critBuilder, CriteriaQuery<Payment> criteria, Root<Payment> root) {
				criteria.where(root.get("phone").in(phone));
			}
		});
	}
	
	public static List<Payment> getAllPayments(Phone phone, Date untilDate) throws Exception {
		return singleTableQuery(Payment.class, new SingleTableQueryCondition<Payment>() {
			public void body(CriteriaBuilder critBuilder, CriteriaQuery<Payment> criteria, Root<Payment> root) {
				criteria.where(root.get("phone").in(phone), critBuilder.lessThanOrEqualTo(root.get("validity_date"), untilDate));
			}
		});
	}
	
}
