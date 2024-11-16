package hms.users;

import java.util.ArrayList;

/**
 * Manage all created instance of user class and ensures one is instance is created per user.
 */
public class UserInstances {

    /**
     * Store all the user instances
     */
    private ArrayList<User> inst;

    /**
     * constructor
     */
    public UserInstances() {
        inst = new ArrayList<User>();
    }

    /**
     * check if a user instance is store in the list
     * @param ID ID of user
     * @return true if instance is present otherwise false
     */
    public boolean isIn(String ID) {
        for(User user : inst) {
            if(ID.equals(user.getID())) return true;
        }

        return false;
    }

    /**
     * add instance to the list
     * @param user user to be added
     * @return true if successfully added false otherwise
     */
    public boolean add(User user) {
        if(isIn(user.getID())) return false;

        inst.add(user);
        return true;
    }

    /**
     * remove instance from the list
     * @param user user to be removed
     * @return true if successfully removed otherwise false
     */
    public boolean remove(String ID) {
        for(int i = 0; i < inst.size(); i++) {
            if(inst.get(i).getID().equals(ID)) {
                inst.remove(i);
                return true;
            }
        }

        return false;
    }

    /**
     * get instance of user by ID
     * @param ID ID of user
     * @return Instance of user and null if not present
     */
    public User getInstance(String ID) {
        for(User user : inst) {
            if(ID.equals(user.getID())) return user;
        }

        return null;
    }


}

