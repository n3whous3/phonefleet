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
	private int payment_value;
	
	
	public Payment() {}
	public Payment(Phone phone, Date validity_date, int payment_value)
	{
		this.phone = phone;
		this.validity_date = validity_date;
		this.payment_value = payment_value;
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
	public int getPayment_value()
	{
		return payment_value;
	}
	public void setPayment_value(int payment_value)
	{
		this.payment_value = payment_value;
	}
}
