package hms;

public class Password {
    /**
     * For now just a string for the password.
     */
    private String password;

    /**
     * Contructor of Password to set the password to the default password
     */
    public Password() {
        password = "password";
    }

    /**
     * Method to change the password and nothing else.
     * @param newPassword the string of the new password
     */
    public void changePassword(String newPassword) {
        password = newPassword;
    }

    /**
     * Return true if the input is the password.
     * @param check string to check the passowrd
     * @return true if the password is correct otherwise false
     */
    public boolean checkPassword(String check) {
        return password.equals(check);
    }
}
