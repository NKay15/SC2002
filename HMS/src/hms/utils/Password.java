package hms.utils;

import java.util.Scanner;

import hms.GlobalData;

public class Password extends MD5 {
    /**
     * For now just a string for the password.
     */
    private String password;

    /**
     * For 2FA
     */
    private TOTP totp;

    private void setUp2FA() {
        totp = new TOTP();
        Scanner sc = GlobalData.getInstance().sc;
        System.out.println("Enter this code in your authenticator app : " + totp.getKey());
        System.out.print("Enter the 6 digit code that shows on your phone :");
        int code = sc.nextInt();
        if(totp.validateTOTP(code)) {
            System.out.println("Your 2FA has been set up");
        }
        else {
            System.out.println("The code is wrong. 2FA has not been set up.");
            totp = null;
        }
    }

    /**
     * Contructor of Password to set the password to the default password
     */
    public Password() {
        this.password = MD5.getMd5("password");
        totp = null;
    }

    /**
     * Constructor of Password that has been changed
     */
    public Password(String password) {
        this.password = password;
        totp = null;
    }

    /**
     * Method to change the password and nothing else.
     * @param newPassword the string of the new password
     */
    public void changePassword(String newPassword) {
        this.password = MD5.getMd5(newPassword);
    }

    /**
     * Return true if the input is the password.
     * @param check string to check the passowrd
     * @return true if the password is correct otherwise false
     */
    public boolean checkPassword(String check) {
        return password.equals(MD5.getMd5(check));
    }

    /**
     * Accessor of password
     * @return password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Check if user has 2FA
     * @return true is 2FA is present
     */
    public boolean has2FA() {
        return totp != null;
    }

    /**
     * Write 2FA key
     * @param key key
     */
    public void set2FA(String key) {
        totp = new TOTP(key);
    }

    /**
     * Accessor of 2FA key
     * @return
     */
    public String get2FAKey() {
        return totp.getKey();
    }

    /**
     * Check if 2FA key is correct
     * @param key 6 digit number
     * @return true if correct
     */
    public boolean check2FA(int key) {
        return totp.validateTOTP(key);
    }

    /**
     * Remove 2FA used by aministrator
     */
    public void remove2FA() {
        totp = null;
    }

    /**
     * 2FA menu
     */
    public void menu2FA() {
        Scanner sc = GlobalData.getInstance().sc;
        System.out.println("Manage 2FA");
        int choice;
        if(totp == null) {
            System.out.println("1. Set up 2FA");
            System.out.println("2. Exit");
            choice = sc.nextInt();
            if(choice == 1) {
                setUp2FA();
            } 
        }
        else {
            System.out.println("1. Check key");
            System.out.println("2. Remove 2FA");
            System.out.println("3. Exit");
            choice = sc.nextInt();
            switch (choice) {
                case 1 :
                    System.out.println("Key : " + totp.getKey());
                break;
                case 2 : 
                    System.out.println("2FA removed");
                    totp = null;
                break;
            }
        }
    }
}
