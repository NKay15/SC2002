package hms;

import hms.pharmacy.Inventory;
import hms.users.UserInstances;

import java.util.Scanner;

/**
 * Singleton for the global data
 */
public class GlobalData {
    /**
     * The single instance of this class
     */
    private static GlobalData single = null;


    /**
     * Global inventory
     */
    public Inventory inventory;

    /**
     * Global user instances
     */
    public UserInstances userInstances;

    /**
     * Global Scanner
     */
    public Scanner sc;

    /**
     * Constructor for the global data
     */
    private GlobalData() {
        inventory = null;
        userInstances = new UserInstances();
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
