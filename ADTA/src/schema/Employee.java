package schema;

/* Represent the Employee Schema */
public class Employee {

	public Attribute <Integer> primaryKey;
	public Attribute <String> name;
	public Attribute <Integer> departementId;
	public Attribute <Integer> socialInsuranceNumber;
	public Attribute <String> address;
	
	public Employee() {
		this.primaryKey = new Attribute<Integer>(7);
		this.name = new Attribute<String>(12);
		this.departementId = new Attribute<Integer>(2);
		this.socialInsuranceNumber = new Attribute<Integer>(9);
		this.address = new Attribute<String>(70);
	}
	
	public String toString() {
		return this.primaryKey.value.toString() + "-" + 
				this.name.value.toString() + "-" + 
				this.departementId.value.toString() + "-" + 
				this.socialInsuranceNumber.value.toString() + "-" + 
				this.address.value.toString();
	}
}