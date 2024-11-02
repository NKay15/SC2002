package hms.users;

public class Pharmacist extends Staff {
	
	/**
	 * Constructor
	 */
	public Pharmacist(String ID, String name, int gender, int age) {
		super(ID, name, 3, gender, age);
	}
}
