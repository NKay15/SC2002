package hms.users;

import hms.GlobalData;
import hms.Password;
import java.util.Scanner;

public class User {
    /**
     * A Password class to store the password.
     */
    private Password password;

    /**
     * An integer to store the role. 
     * 0 - no role 
     * 1 - Patient 
     * 2 - Doctor 
     * 3 - Pharmacist 
     * 4 - Administrator
     */
    private int role;

    /**
     * ID of user
     */
    private String ID;

    /**
     * String that contains the name of the user
     */
    private String name;

    /**
     * An integer to store the gender
     * 0 - Unknown
     * 1 - Male
     * 2 - Female
     */
    private int gender;

    private void changePassword(Scanner sc){
        System.out.print("Enter Old Password (0 to Cancel): ");
        String oldPassword = sc.next(); sc.nextLine();
        if (oldPassword.equals("0")){
            System.out.println("Operation Cancelled. Returning to Menu...\n");
            return;
        }
        if(!password.checkPassword(oldPassword)) {
            System.out.print("Incorrect Password! Returning to Menu...\n");
            return;
        }
        System.out.print("Enter New Password: ");
        oldPassword = sc.next(); sc.nextLine();
        password.changePassword(oldPassword);
        System.out.print("Password Successfully Changed! Returning to Menu...\n ");
    }

    /**
     * Constructor for user
     * @param ID id
     * @param name name
     * @param role role number
     * @param gender gender number
     */
    public User(String ID, String name, int role, int gender) {
    	this.ID = ID;
    	this.name = name;
    	this.role = role;
    	this.gender = gender;
    	this.password = new Password();
    }
    
    /**
     * Accessors method of ID
     * @return
     */
    public String getID() {
        return ID;
    }

    /**
     * Accessor of role
     * @return role number
     */
    public int getRole() {
        return role;
    }

    /**
     * Set role
     * @param role
     */
    public void setRole(int role) {
        this.role = role;
    }

    /**
     * Get name of staff
     * @return name
     */
    public String getName() {
    	return name;
    }

    /**
     * Set name of staff
     * @param name
     */
	public void setName(String name)  {
		this.name = name;
	}

    /**
     * Get gender of staff
     * @return gender
     */
    public int getGender() {
    	return gender;
    }

    /**
     * Set gender of staff
     * @param gender
     */
	public void setGender(int gender) {
		this.gender = gender;
	}

    public int getAge(){
        if (this instanceof Staff) return ((Staff) this).getAge();
        return 0;
    }

    /**
     * A function to login. It will ask for password.
     * @return role if login is successful otherwise -1
     */
    public int login() {
        Scanner sc = GlobalData.getInstance().sc;
        System.out.print("Enter password: ");
        String value = sc.next();
        if (password.checkPassword(value)) return role;
        else return -1;
    }

    public boolean checkPassword(String password) {
        return this.password.checkPassword(password);
    }

    /**
     * Method to call menu. To be override by
     */
    public void menu() {
        menu(1);
    }

    /**
     * Menu method to print user menu
     * @param i to start
     */
    public void menu(int i) {
        System.out.println((i++) + ". Change Password");
        System.out.println((i++) + ". Log out");
    }

    /**
     * Process options for user choice
     * @param choice number chosen in the user menu and need to be ofset by the number of options in supclass
     * @return if false log out
     */
    public boolean useroptions(int choice){
        Scanner sc = GlobalData.getInstance().sc;
        switch(choice) {
            case 1 : changePassword(sc);
            break;
            default : return false;
        }
        return true;
    }
}
