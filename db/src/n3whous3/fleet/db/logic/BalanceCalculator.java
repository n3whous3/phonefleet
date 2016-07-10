package n3whous3.fleet.db.logic;

import java.sql.Date;

import n3whous3.fleet.db.dao.InvoiceDAO;
import n3whous3.fleet.db.dao.PaymentDAO;
import n3whous3.fleet.db.entities.Invoice;
import n3whous3.fleet.db.entities.Payment;
import n3whous3.fleet.db.entities.Phone;

public class BalanceCalculator {
	
	public BalanceCalculator(Phone phone) {
		this.phone = phone;
	}
	
	public Values calculateBalance(Date untilDate) throws Exception {
		// Add up invoices and substract the payments
		Values ret = new Values();
		for(Invoice inv : InvoiceDAO.getAllInvoices(phone, untilDate)) {
			ret.phoneDebt += inv.getBill();
			ret.monthlyFee += inv.getMembership_fee();
		}
		
		for(Payment paym : PaymentDAO.getAllPayments(phone, untilDate)) {
			ret.phoneDebt -= paym.getPayment_bill();
			ret.monthlyFee -= paym.getPayment_monthly_fee();
		}
		return ret;
	}
	
	private Phone phone;
	
}
