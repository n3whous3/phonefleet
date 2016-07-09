package n3whous3.fleet.db.entities;

import java.sql.Date;
import javax.persistence.*;


@Entity
@Table(name="STATIC_INFO", schema="FLEET")
public class StaticInfo {
	@Id
	@Column(unique=true, nullable=false)
	private Date validity_date;
	
	@Column(nullable=false)
	private int default_membership_fee;
	
	@Column(nullable=false)
	private int membership_fee_threshold;
	
	public StaticInfo() {}
	public StaticInfo(Date validity_date, int default_membership_fee, int membership_fee_threshold)
	{
		this.validity_date = validity_date;
		this.default_membership_fee = default_membership_fee;
		this.membership_fee_threshold = membership_fee_threshold;
	}
	public Date getValidity_date() {
		return validity_date;
	}
	public void setValidity_date(Date validity_date) {
		this.validity_date = validity_date;
	}
	public int getDefault_membership_fee() {
		return default_membership_fee;
	}
	public void setDefault_membership_fee(int default_membership_fee) {
		this.default_membership_fee = default_membership_fee;
	}
	public int getMembership_fee_threshold()
	{
		return membership_fee_threshold;
	}
	public void setMembership_fee_threshold(int membership_fee_threshold)
	{
		this.membership_fee_threshold = membership_fee_threshold;
	}
}
