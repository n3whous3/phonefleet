package n3whous3.fleet.db.dao;

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
	
	public static void removeAllPaymentsByPhone(Phone phone) throws Exception {
		removeObjects(getPaymentsByPhone(phone));
	}
	
	public static List<Payment> getAllPayments() throws Exception {
		return singleTableQuery(Payment.class);
	}
	
	public static List<Payment> getPaymentsByPhone(Phone phone) throws Exception {
		return singleTableQuery(Payment.class, new SingleTableQueryCondition<Payment>() {
			public void body(CriteriaBuilder critBuilder, CriteriaQuery<Payment> criteria, Root<Payment> root) {
				criteria.where(root.get("phone").in(phone));
			}
		});
	}
	
}
