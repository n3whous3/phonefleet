package n3whous3.fleet.db.entities;

import javax.persistence.*;


// TODO validation
@Entity
@Table(name="PHONES", schema="FLEET")
public class Phone
{
	// TODO: invoice in this
	
	@Id
	@Column(unique=true, nullable=false, length=9)
	private String phone;
	
	@OneToOne
	@JoinColumn(name="person", nullable=false)
	private Person person;
	
	
	public Phone() {}
	public Phone(String phone, Person person)
	{
		this.phone = phone;
		this.person = person;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Phone) {
			Phone phoneOther = (Phone) obj;
			return person.equals(phoneOther.person) && phone.equals(phoneOther.phone);
		}
		return false;
	}
}
