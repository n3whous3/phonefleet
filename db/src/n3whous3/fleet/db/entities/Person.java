package n3whous3.fleet.db.entities;

import javax.persistence.*;


@Entity
@Table(name="PERSONS", schema="FLEET")
public class Person {
	// TODO: use Bordány-kártya as ID as soon as available, until that...
	@Id
	@Column(unique=true, nullable=false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(unique=true)
	private String card_num;
	
	@Column(nullable=false, length=255)
	private String name;
	
	@Column(length=255)
	private String email;
	
	@Column(length=512)
	private String address;
	
	@OneToOne(mappedBy="person")
	private Phone phone;
	
	public Person() {}
	public Person(String card_num, String name, String email, String address)
	{
		this.card_num = card_num;
		this.name = name;
		this.email = email;
		this.address = address;
	}
	public long getId()
	{
		return id;
	}
	public void setId(long id)
	{
		this.id = id;
	}
	public String getCard_num()
	{
		return card_num;
	}
	public void setCard_num(String card_num)
	{
		this.card_num = card_num;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Phone getPhone() {
		return phone;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Person) {
			Person personOther = (Person) obj;
			return id == personOther.id
						&& card_num.equals(personOther.card_num)
						&& name.equals(personOther.name)
						&& email.equals(personOther.email)
						&& address.equals(personOther.address);
		}
		return false;
	}
}
