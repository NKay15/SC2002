/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package hms.users;

import hms.services.StaffFileService;
import hms.utils.Password;
import hms.utils.Role;
import java.util.Scanner;

/**
 * Staff class
 * @author rin
 */
public class Staff extends User {

    /**
     * Age of Staff
     */
    private int age;
    
    /**
     * Get age of staff
     * @return age
     */
    public int getAge() {
    	return age;
    }

    /**
     * Set age of staff
     * @param age
     */
	public void setAge(int age) {
		this.age = age;
	}

    /**
     * Constructor of Staff
     * @param ID ID
     * @param name Name
     * @param role role
     * @param gender gender number
     * @param age age
     * @param password password
     */
    public Staff(String ID, String name, Role role, int gender, int age, Password password) {
        super(ID, name, role, gender, password);
        this.age = age;
    }

    /**
     * Update staff data
     * @param ID ID
     * @param name name
     * @param role role
     * @param gender gender number
     * @param age age
     * @param password password
     * @return true if a data is changed otherwise false
     */
    public boolean update(String ID, String name, Role role, int gender, int age, Password password) {
        boolean change = super.update(ID, name, role, gender, password);

        if(this.age != age) {
            this.age = age;
            change = true;
        }

        return change;
    }

    public void printRole() {
        System.out.print("Staff");
    }

    @Override
    protected void changePassword() {
        super.changePassword();
        StaffFileService.updateStaff(this);
    }
}
