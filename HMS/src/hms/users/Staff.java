/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package hms.users;

import hms.utils.Password;

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

    public Staff(String ID, String name, int role, int gender, int age, Password password) {
        super(ID, name, role, gender, password);
        this.age = age;
    }

    public void printRole() {
        System.out.print("Staff");
    }
}
