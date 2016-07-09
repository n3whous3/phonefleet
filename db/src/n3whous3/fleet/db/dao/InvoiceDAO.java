package n3whous3.fleet.db.dao;

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
	
	public static void removeAllInvoicesByPhone(Phone phone) throws Exception {
		removeObjects(getInvoicesByPhone(phone));
	}
	
	public static List<Invoice> getAllInvoices() throws Exception {
		return singleTableQuery(Invoice.class);
	}
	
	public static List<Invoice> getInvoicesByPhone(Phone phone) throws Exception {
		return singleTableQuery(Invoice.class, new SingleTableQueryCondition<Invoice>() {
			public void body(CriteriaBuilder critBuilder, CriteriaQuery<Invoice> criteria, Root<Invoice> root) {
				criteria.where(root.get("phone").in(phone));
			}
		});
	}
	
}
