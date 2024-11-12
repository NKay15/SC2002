package hms.utils;

import hms.utils.MD5;

public class Password extends MD5 {
    /**
     * For now just a string for the password.
     */
    private String password;

    /**
     * Contructor of Password to set the password to the default password
     */
    public Password() {
        this.password = MD5.getMd5("password");
    }

    /**
     * Constructor of Password that has been changed
     */
    public Password(String password) {
        this.password = password;
    }

    /**
     * Method to change the password and nothing else.
     * @param newPassword the string of the new password
     */
    public void changePassword(String newPassword) {
        password = MD5.getMd5(newPassword);
    }

    /**
     * Return true if the input is the password.
     * @param check string to check the passowrd
     * @return true if the password is correct otherwise false
     */
    public boolean checkPassword(String check) {
        return password.equals(MD5.getMd5(check));
    }
}
