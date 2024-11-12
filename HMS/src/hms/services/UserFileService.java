package hms.services;

import java.util.ArrayList;

import hms.users.User;
import hms.utils.InputValidation;

public class UserFileService extends InputValidation {
    /**
     * Get all users by role
     * @return list of users
     */
    public static ArrayList<User> getUsersRoleSorted() {
        ArrayList<User> userArray = new ArrayList<User>();
        userArray.addAll(PatientFileService.getAllPatientData());
        userArray.addAll(StaffFileService.getStaffRoleSorted());
        return userArray;
    }
}
