package hms;

import java.util.ArrayList;
import hms.UserList;
import hms.Inventory;

public class GlobalData {
    /**
     * Singleton for the global data
     */
    private static GlobalData single = null;

    /**
     * Global UserList
     */
    public UserList userList;

    /**
     * Global inventory
     */
    public Inventory inventory;

    /**
     * Constructor for the global data
     */
    private GlobalData() {
        userList = null;
        inventory = null;
    }

    /**
     * Get Instance for the global data
     * @return GlobalData to excess data
     */
    public static GlobalData getInstance(){
        if(single = null){
            single = new GlobalData();
        }

        return single;
    }
}
