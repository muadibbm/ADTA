package schema;

/* Represents the structure of an attribute */
public class Attribute <T> {
	public T value;
	public int length;
	
	public Attribute(int length) {
		this.length = length;
	}
}
