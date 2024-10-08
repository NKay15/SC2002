package hms;

public class Password {
    private String password;

    public void changePassword(String newPassword) {
        password = newPassword;
    }

    public boolean checkPassword(String check) {
        return password.equals(check);
    }
}
