package n3whous3.fleet.db.entities;

import java.sql.Date;
import javax.persistence.*;


@Entity
@Table(name="INVOICES", schema="FLEET")
public class Invoice {
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
	private int bill;
	
	@Column(nullable=false)
	private int membership_fee;
	
	
	public Invoice() {}
	public Invoice(Phone phone, Date validity_date, int bill, int membership_fee)
	{
		this.phone = phone;
		this.validity_date = validity_date;
		this.bill = bill;
		this.membership_fee = membership_fee;
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
	public int getBill()
	{
		return bill;
	}
	public void setBill(int bill)
	{
		this.bill = bill;
	}
	public int getMembership_fee()
	{
		return membership_fee;
	}
	public void setMembership_fee(int membership_fee)
	{
		this.membership_fee = membership_fee;
	}
}
