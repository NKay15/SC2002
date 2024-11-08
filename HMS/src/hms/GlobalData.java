package hms;

import hms.UserList;
import hms.Inventory;

import java.util.Scanner;

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
     * Global Scanner
     */
    public Scanner sc;

    /**
     * Constructor for the global data
     */
    private GlobalData() {
        userList = null;
        inventory = null;
        sc = new Scanner(System.in);
    }

    /**
     * Get Instance for the global data
     * @return GlobalData to excess data
     */
    public static GlobalData getInstance(){
        if(single == null){
            single = new GlobalData();
        }

        return single;
    }
}
