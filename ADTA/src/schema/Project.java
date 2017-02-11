package schema;

/* Represent the Project Schema */
public class Project {

	public Attribute <Integer> empId;
	public Attribute <Integer> projectId;
	public Attribute <String> projectDescription;
	
	public Project() {
		this.empId = new Attribute<Integer>(7);
		this.projectId = new Attribute<Integer>(5);
		this.projectDescription = new Attribute<String>(48);
	}
	
	public String toString() {
		return this.empId.value.toString() + "-" + 
				this.projectId.value.toString() + "-" + 
				this.projectDescription.value.toString();
	}
}