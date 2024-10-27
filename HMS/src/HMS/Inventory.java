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

    /**
     * Return the index of a medicine
     * @param check medicine to find
     * @return index of medicine in catalog -1 if not present
     */
    private int findIndex(Medicine check) {
        for(int i = 0; i < catalog.size(); i++) {
            if(catalog.get(i).name().equals(check.name())) return i;
        }

        return -1;
    }

    /**
     * Constructor for Inventory
     */
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

    /**
     * Used by pharmacist to dispense medication
     * @param aop AppointmentOutcomeRecord of the appointmet
     * @return true if successful false otherwise
     */
    public Boolean dispense(AppointmentOutcomeRecord aop) {
        if(aop.isDispensed()) return false;

        for(Medicine n : aop.getprescription()) {
            int idx = findIndex(n);
            if(catalog.get(idx).amount() < n.amount()) return false;
        }

        for(Medicine n : aop.getprescription()) {
            int idx = findIndex(n);
            catalog.get(idx).prescribe(n.amount());
        }

        aop.dispense();

        return true;
    }

    /**
     * Used by pharamacist to request a restock
     */
    public void createRequest() {
        printCurrentInvetory();
        System.out.print("Enter index of medicine (0 to exit) : ");
        Scanner sc = new Scanner(System.in);
        int med = sc.nextInt();
        while(med < 0 || med > catalog.size()) {
            System.out.print("Invaild input. Try again : ");
            med = sc.nextInt();
        }
        System.out.print("Enter amoount of medicine : ");
        int quantity = sc.nextInt();
        while(quantity < 0 ) {
            System.out.print("Invaild input. Try again : ");
            quantity = sc.nextInt();
        }
        request.add(catalog.get(med-1).copy(quantity));
        System.out.print("Restock Request has been created : ");
    }

    /**
     * Used by ... to manage restock
     */
    public void manageRestock() {
        printRestockRequest();
        System.out.print("Enter index of request (0 to exit) : ");
        Scanner sc = new Scanner(System.in);
        int req = sc.nextInt();
        while(req < 0 || req > request.size()) {
            System.out.print("Invaild input. Try again : ");
            req = sc.nextInt();
        }
        int idx = findIndex(request.get(req-1));
        System.out.print("Enter 0 to exit, 1 to approve and 2 to reject : ");
        int choice = sc.nextInt();
        if (choice == 1) {
            catalog.get(idx).restock(request.get(req-1).amount());
            request.remove(req-1);
            System.out.println("Request approved");
        }
        else if (choice == 2) {
            request.remove(req-1);
            System.out.println("Request rejected");
        }
        else System.out.println("Exiting");
    }

    /**
     * Print current inventory
     */
    public void printCurrentInvetory() {
        for(int i = 0; i < catalog.size(); i++) {
            System.err.println((i+1) + ". " + catalog.get(i).name() + " : " + catalog.get(i).amount());
        }
    }

    /**
     * Print restock request
     */
    public void printRestockRequest() {
        for(int i = 0; i < request.size(); i++) {
            System.err.println((i+1) + ". " + request.get(i).name() + " : " + request.get(i).amount());
        }
    }
}
