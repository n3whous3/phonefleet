package n3whous3.fleet.db.dao;

import java.sql.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import n3whous3.fleet.db.entities.Invoice;
import n3whous3.fleet.db.entities.Phone;

public class InvoiceDAO extends BaseDAO {

	public static void addInvoice(Invoice invoice) throws Exception {
		persistObject(invoice);
	}
	
	public static void removeInvoice(Invoice invoice) throws Exception {
		removeObject(invoice);
	}
	
	public static void removeAllInvoices(Phone phone) throws Exception {
		removeObjects(getAllInvoices(phone));
	}
	
	public static List<Invoice> getAllInvoices() throws Exception {
		return singleTableQuery(Invoice.class);
	}
	
	public static List<Invoice> getAllInvoices(Date untilDate) throws Exception {
		return singleTableQuery(Invoice.class, new SingleTableQueryCondition<Invoice>() {
			public void body(CriteriaBuilder critBuilder, CriteriaQuery<Invoice> criteria, Root<Invoice> root) {
				criteria.where(critBuilder.lessThanOrEqualTo(root.get("validity_date"), untilDate));
			}
		});
	}
	
	public static List<Invoice> getAllInvoices(Phone phone) throws Exception {
		return singleTableQuery(Invoice.class, new SingleTableQueryCondition<Invoice>() {
			public void body(CriteriaBuilder critBuilder, CriteriaQuery<Invoice> criteria, Root<Invoice> root) {
				criteria.where(root.get("phone").in(phone));
			}
		});
	}
	
	public static List<Invoice> getAllInvoices(Phone phone, Date untilDate) throws Exception {
		return singleTableQuery(Invoice.class, new SingleTableQueryCondition<Invoice>() {
			public void body(CriteriaBuilder critBuilder, CriteriaQuery<Invoice> criteria, Root<Invoice> root) {
				criteria.where(root.get("phone").in(phone), critBuilder.lessThanOrEqualTo(root.get("validity_date"), untilDate));
			}
		});
	}
	
}
