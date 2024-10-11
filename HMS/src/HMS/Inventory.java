package hms;

import java.util.ArrayList;
import java.util.Scanner;

public class Inventory {

    /**
     * List of medicine in storage
     */
    private ArrayList<Medicine> catalog;

    /**
     * List of medicine that have been request to be restock
     */
    private ArrayList<Medicine> request;

    public Inventory() {
        catalog = new ArrayList<Medicine>();
        request = new ArrayList<Medicine>();
    }

    /**
     * Adding new medicine to the inventory
     */
    public void addNewMedicine() {
        System.out.print("Enter name of medicine : ");
        Scanner sc = new Scanner(System.in);
        String name = sc.next();
        for(int i = 0; i < catalog.size(); i++) {
            if(name.equals(catalog.get(i).name())) {
                System.out.println("Medicine is already in the catalog");
                return;
            }
        }

        System.out.print("Enter initial amount : ");
        int amount = sc.nextInt();
        catalog.add(new Medicine(name, amount));
        System.out.println(amount + " of " + name + " has been added");
    }

    public void printCurrentInvetory() {
        for(int i = 0; i < catalog.size(); i++) {
            System.err.println((i+1) + ". " + catalog.get(i).name() + " : " + catalog.get(i).amount());
        }
    }
}
