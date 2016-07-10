package n3whous3.fleet.db.entities;

import java.sql.Date;
import javax.persistence.*;


@Entity
@Table(name="PAYMENTS", schema="FLEET")
public class Payment {
	@Id
	@Column(nullable=false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade={CascadeType.MERGE,CascadeType.REFRESH,CascadeType.DETACH})
	@JoinColumn(name="phone")
	private Phone phone;
	
	@Column(nullable=false)
	private Date validity_date;
	
	@Column(nullable=false)
	private int payment_bill;
	
	@Column(nullable=false)
	private int payment_monthly_fee;
	
	
	public Payment() {}
	public Payment(Phone phone, Date validity_date, int payment_bill, int payment_monthly_fee)
	{
		this.phone = phone;
		this.validity_date = validity_date;
		this.payment_bill = payment_bill;
		this.payment_monthly_fee = payment_monthly_fee;
	}
	public long getId()
	{
		return id;
	}
	public void setId(long id)
	{
		this.id = id;
	}
	public Phone getPhone()
	{
		return phone;
	}
	public void setPhones(Phone phone)
	{
		this.phone = phone;
	}
	public Date getValidity_date()
	{
		return validity_date;
	}
	public void setValidity_date(Date validity_date)
	{
		this.validity_date = validity_date;
	}
	public int getPayment_bill()
	{
		return payment_bill;
	}
	public void setPayment_bill(int payment_bill)
	{
		this.payment_bill = payment_bill;
	}
	public int getPayment_monthly_fee()
	{
		return payment_monthly_fee;
	}
	public void setPayment_monthly_fee(int payment_monthly_fee)
	{
		this.payment_monthly_fee = payment_monthly_fee;
	}
}
