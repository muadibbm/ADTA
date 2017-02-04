package schema;

/* Represent the Employee Schema */
public class Employee {

	public Attribute <Integer> primaryKey;
	public Attribute <char[]> name;
	public Attribute <Integer> departementId;
	public Attribute <Integer> socialInsuranceNumber;
	public Attribute <char[]> address;
	
	public Employee() {
		this.primaryKey = new Attribute<Integer>(7);
		this.name = new Attribute<char[]>(12);
		this.departementId = new Attribute<Integer>(2);
		this.socialInsuranceNumber = new Attribute<Integer>(9);
		this.address = new Attribute<char[]>(70);
	}
}