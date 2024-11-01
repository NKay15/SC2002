package hms.users;

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
     * 0 - Unknow
     * 1 - Male
     * 2 - Female
     */
    private int gender;

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
     * A function to login. It will ask for password.
     * @return role if login is successful otherwise -1
     */
    public int login() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter password : ");
        String value = sc.next();
        if (password.checkPassword(value)) return role;
        else return -1;
    }

    /**
     * Menu method for user. To be overiden by subclass for their specific menu.
     */
    public void menu(){
        System.out.println("User has no menu");
    }
}
