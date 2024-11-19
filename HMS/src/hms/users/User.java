package hms.users;

import hms.GlobalData;
import hms.utils.Password;
import hms.utils.Role;
import java.util.Scanner;

/**
 * Base class of user with the basic functionality
 */
public class User {
    /**
     * A Password class to store the password.
     */
    private Password password;

    /**
     * Enum of role
     */
    private Role role;

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

    /**
     * method to change password
     */
    protected void changePassword(){
        Scanner sc = GlobalData.getInstance().sc;
        System.out.print("Enter Old Password (0 to Cancel): ");
        String password = sc.next(); sc.nextLine();
        if (password.equals("0")){
            System.out.println("Operation Cancelled. Returning to Menu...");
            return;
        }
        if(!this.password.checkPassword(password)) {
            System.out.print("Incorrect Password or Token! Returning to Menu...");
            return;
        }
        System.out.print("Enter New Password: ");
        String newPassword = sc.next(); sc.nextLine();
        while (newPassword.equals("0") || this.password.checkPassword(newPassword)){
            System.out.print("Invalid Password! Enter New Password: ");
            newPassword = sc.next(); sc.nextLine();
        }
        this.password.changePassword(newPassword);
        System.out.println("Password Successfully Changed! Returning to Menu...");
    }

    /**
     * Constructor for user
     * @param ID id
     * @param name name
     * @param role role
     * @param gender gender number
     * @param password password
     */
    public User(String ID, String name, Role role, int gender, Password password) {
    	this.ID = ID;
    	this.name = name;
    	this.role = role;
    	this.gender = gender;
        if (password == null) {
            this.password = new Password();
        } else {
            this.password = password;
        }
    }

    /**
     * Update the user data
     * @param ID ID
     * @param name name
     * @param role role
     * @param gender gender number
     * @param password password
     * @return true if a data is changed otherwise false
     */
    public boolean update(String ID, String name, Role role, int gender, Password password) {
        boolean change = false;

        if(this.ID.equals(ID)) {
            this.ID = ID;
            change = true;
        }

        if(!this.name.equals(name)){
    	    this.name = name;
            change = true;
        }

        if(!this.role.equals(role)) {
    	    this.role = role;
            change = true;
        }

        if(this.gender != gender){
    	    this.gender = gender;
            change = true;
        }

        if (password == null) {
            if(!this.password.checkPassword("password")) {
                this.password = new Password();
                change = true;
            }
        } 
        else if(!this.password.getPassword().equals(password.getPassword())) {
            this.password = password;
            change = true;
         }

        return change;
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
     * @return role
     */
    public Role getRole() {
        return role;
    }

    /**
     * Set role
     * @param role
     */
    public void setRole(Role role) {
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
     * Accessor of gender in string
     * @return gender in string
     */
    public String getGenderString() {
        switch (gender) {
            case 0:
                return "Unknown";
            case 1:
                return "Male";
            case 2:
                return "Female";
            default:
                return null;
        }
    }

    /**
     * Get password
     * @return password
     */
    public Password getPassword() {
        return password;
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
    public Role login() {
        Scanner sc = GlobalData.getInstance().sc;
        System.out.print("Enter password: ");
        String value = sc.nextLine();
        if (!password.checkPassword(value)) return null;
        if (password.has2FA()) {
            System.out.print("Enter 2FA code: ");
            int code = sc.nextInt();
            if (!password.check2FA(code)) return null;
        }
        return role;
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
        System.out.println((i++) + ". Manage 2FA");
        System.out.println((i++) + ". Log out");
    }

    /**
     * Process options for user choice
     * @param choice number chosen in the user menu and need to be offset by the number of options in supclass
     * @return if false log out
     */
    protected boolean userOptions(int choice){
        Scanner sc = GlobalData.getInstance().sc;
        switch(choice) {
            case 1 : changePassword();
            break;
            case 2 : password.menu2FA();
            break;
            case 3 : 
                System.out.print("Confirm Log Out? Enter 1 to Log Out; " +
                "or Enter anything else to Return to Menu.\nEnter your choice: ");
                String confirmLogOut = sc.next(); sc.nextLine();
                if (confirmLogOut.equals("1")) {
                    System.out.println("Logging out...\n");
                    return false;
                } else {
                    System.out.println("Returning to Menu...");
                    break;
                }
            default :
                System.out.print("Invalid choice! Try again: ");
            }
        return true;
    }

    /**
     * method to print the role of user
     */
    public void printRole() {
        System.out.print("Unknown");
    }
}
