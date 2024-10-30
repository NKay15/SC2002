package hms;

import hms.medicalRecords.AppointmentOutcomeRecord;

import java.util.ArrayList;
import java.util.Scanner;

public class Inventory {

    /**
     * List of medicine in storage
     */
    private ArrayList<Medicine> catalog;

    /**
     * Low level stock level
     */

    private ArrayList<Medicine> lowlevel;

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
        System.out.print("Enter low level alert : ");
        int level = sc.nextInt();
        lowlevel.add(new Medicine(name, amount));
        System.out.println(amount + " of " + name + " has been added. Low level warning at " + level);
    }

    /**
     * Adding new medicine to the inventory when data is available to pass in
     * @param name name of medicine
     * @param amount amount of medicine
     * @param level low level alert
     * @return true if successfully added. false when it is already present in the inventory and not added in
     */
    public boolean addNewMedicine(String name, int amount, int level) {
        for(int i = 0; i < catalog.size(); i++) {
            if(name.equals(catalog.get(i).name())) {
                return false;
            }
        }

        catalog.add(new Medicine(name, amount));
        lowlevel.add(new Medicine(name, amount));
        return true;
    }

    /**
     * Used by pharmacist to dispense medication
     * @param aop AppointmentOutcomeRecord of the appointmet
     * @return true if successful false otherwise
     */
    public boolean dispense(AppointmentOutcomeRecord aop) {
        if(aop.isDispensed()) return false;

        for(Medicine n : aop.getprescription()) {
            int idx = findIndex(n);
            if(catalog.get(idx).amount() < n.amount()) return false;
        }

        for(Medicine n : aop.getprescription()) {
            int idx = findIndex(n);
            catalog.get(idx).prescribe(n.amount());

            if(catalog.get(idx).amount() <= lowlevel.get(idx).amount())
                System.out.println(catalog.get(idx).name() + "is at low amount of " + catalog.get(idx).amount() + ". Please restock");
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

    public Medicine[] generatePrescription(){
        printCurrentInvetory();
        ArrayList<Medicine> ret = new ArrayList<Medicine>();
        int med = 1;
        int amount;
        
        while(med < 0 || med >= catalog.size()) {
            System.out.print("Enter index of medicine to prescribe (0 to exit) : ");
            Scanner sc = new Scanner(System.in);
            med = sc.nextInt();
            while(med < 0 || med >= catalog.size()) {
                System.out.print("Invaild input. Try again : ");
                med = sc.nextInt();
            }
            for(int i = 0; i < ret.size(); i++) {
                if (ret.get(i).name().equals(catalog.get(med-1).name())) {
                    System.out.print("Medicine is already prescribe");
                    continue;
                }
                System.out.print("Enter amount (0 to back) : ");
                amount = sc.nextInt();
                if(amount <= 0) continue;

                ret.add(new Medicine(catalog.get(med-1).name(), amount));
            }
        }

        Medicine[] arr = new Medicine[ret.size()];
        for(int i = 0; i < ret.size(); i++) {
            arr[i] = ret.get(i);
        }

        return arr;
    }

    /**
     * Change the low level amount of a medicine
     */
    public void setNewLowLevel() {
        printCurrentInvetory();
        System.out.print("Enter index of medicine (0 to exit) : ");
        Scanner sc = new Scanner(System.in);
        int med = sc.nextInt();
        while(med < 0 || med > catalog.size()) {
            System.out.print("Invaild input. Try again : ");
            med = sc.nextInt();
        }
        System.out.print("Enter new low level: ");
        int quantity = sc.nextInt();
        while(quantity < 0 ) {
            System.out.print("Invaild input. Try again : ");
            quantity = sc.nextInt();
        }
        lowlevel.get(med-1).prescribe(lowlevel.get(med-1).amount());
        lowlevel.get(med-1).restock(quantity);
        System.out.print("Low level amount alert updated");
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
            System.out.print((i+1) + ". " + catalog.get(i).name() + " : " + catalog.get(i).amount());
            if(catalog.get(i).amount() <= lowlevel.get(i).amount())
                System.out.println(" **LOW PLEASE RESTOCK**");
            else System.out.println();
        }
    }

    /**
     * Print restock request
     */
    public void printRestockRequest() {
        for(int i = 0; i < request.size(); i++) {
            System.out.println((i+1) + ". " + request.get(i).name() + " : " + request.get(i).amount());
        }
    }
}
