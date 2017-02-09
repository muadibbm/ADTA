package schema;

/* Represent the Project Schema */
public class Project {

	public Attribute <Integer> empId;
	public Attribute <String> projectId;
	public Attribute <Integer> projectDescription;
	
	public Project() {
		this.empId = new Attribute<Integer>(7);
		this.projectId = new Attribute<String>(5);
		this.projectDescription = new Attribute<Integer>(48);
	}
}