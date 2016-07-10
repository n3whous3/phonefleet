package n3whous3.fleet.db.logic;

import java.sql.Date;

import n3whous3.fleet.db.dao.InvoiceDAO;
import n3whous3.fleet.db.dao.PaymentDAO;
import n3whous3.fleet.db.dao.StaticInfoDAO;
import n3whous3.fleet.db.entities.Invoice;
import n3whous3.fleet.db.entities.Payment;
import n3whous3.fleet.db.entities.Phone;
import n3whous3.fleet.db.entities.StaticInfo;

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
	
	/**
	 * Calculates the required payment at the given date. The required payment goes the following:
	 * - This calculates the balance first. Negative balance is ignored in any case of Values structure (no payment required)
	 * - Monthly membership fee: If the person has positive balance, the required payment is not simply the minimum monthly fee,
	 *   but the positive balance value and at most, StaticInfo's threshold value.
	 *   So if the monthly fee is 300 per user and the user has 1400 monthly fee balance, and the monthly membership
	 *   fee threshold is 1000, then he/she has to pay 1000. If he/she has "only" 500, then 500.
	 * @param untilDate Calculated only until this date
	 * @return The fees has to be payed (membership and phone debt)
	 * @throws Exception
	 */
	public Values calculateRequiredPayment(Date untilDate) throws Exception {
		Values balance = calculateBalance(untilDate);
		if(balance.phoneDebt < 0) {
			balance.phoneDebt = 0;
		}
		if(balance.monthlyFee < 0) {
			balance.monthlyFee = 0;
		}
		StaticInfo si = StaticInfoDAO.getLastStaticInfo(untilDate);
		if(balance.monthlyFee > 0 && balance.monthlyFee > si.getMembership_fee_threshold()) {
				balance.monthlyFee = si.getMembership_fee_threshold();
		}
		return balance;
	}
	
	private Phone phone;
	
}
