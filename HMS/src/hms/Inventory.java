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
     * List of medicine that have been requested to be restocked
     */
    private ArrayList<Medicine> requests;

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
        requests = new ArrayList<Medicine>();
        lowlevel = new ArrayList<Medicine>();
    }

    /**
     * Adding new medicine to the inventory
     */
    public void addNewMedicine() {
        Scanner sc = GlobalData.getInstance().sc;
        boolean alreadyExists = true;
        System.out.print("Enter Name of New Medicine: ");
        String name = sc.next(); sc.nextLine();

        while(alreadyExists) {
            alreadyExists = false;
            for (int i = 0; i < catalog.size(); i++) {
                if (name.equals(catalog.get(i).name())) {
                    System.out.println("Medicine Already Exists in the Catalog!");
                    System.out.println("Enter Name of New Medicine: ");
                    name = sc.next(); sc.nextLine();
                    alreadyExists = true;
                }
            }
        }

        int confirmAdding;
        System.out.println("Continue to Add New Medicine \"" + name + "\"?");
        System.out.println("Enter 1 to Continue; or Enter any other number to Cancel.");
        System.out.print("Enter your choice: ");
        confirmAdding = sc.nextInt(); sc.nextLine();
        if (confirmAdding != 1) {
            System.out.println("Operation Cancelled.");
            return;
        }
        System.out.print("Enter Initial Amount: ");
        int amount = sc.nextInt(); sc.nextLine();
        System.out.print("Enter Low Level Alert: ");
        int level = sc.nextInt(); sc.nextLine();

        // Confirm before proceeding. confirmAdding currently 1
        while (true) {
            if (confirmAdding >= 1 && confirmAdding <= 4) {
                System.out.println("Please ensure that all fields below are correct before confirming:\n");
                System.out.println("Name: " + name);
                System.out.println("Initial Amount: " + amount);
                System.out.println("Low Level Alert: " + level);
                System.out.println("\nConfirm to Add New Medicine?");
                System.out.println("Enter 1 to Confirm; 2 to Cancel; " +
                        "3 to Edit Initial Amount; or 4 to Edit Low Level.");
                System.out.print("Enter your choice: ");
            }
            confirmAdding = sc.nextInt(); sc.nextLine();

            switch (confirmAdding) {
                case 1:
                    catalog.add(new Medicine(name, amount));
                    lowlevel.add(new Medicine(name, amount));
                    System.out.println("New Medicine \"" + name + "\" Successfully Added to Inventory!");
                    return;

                case 2:
                    System.out.println("Operation Cancelled.");
                    return;

                case 3:
                    System.out.print("Enter Initial Amount: ");
                    amount = sc.nextInt(); sc.nextLine();
                    System.out.println("Initial Amount has been Modified.");
                    break;

                case 4:
                    System.out.print("Enter Low Level Alert: ");
                    level = sc.nextInt(); sc.nextLine();
                    System.out.println("Low Level Alert has been Modified.");
                    break;

                default:
                    System.out.print("Invalid choice! Try again: ");
                    break;
            }
        }
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
        lowlevel.add(new Medicine(name, level));
        return true;
    }

    /**
     * Used by administrator to add quantity of medication
     */
    public void updateStockLevelMenu() {
        Scanner sc = GlobalData.getInstance().sc;
        printCurrentInventory();
        System.out.print("Enter Index of Medicine: ");
        int med = sc.nextInt(); sc.nextLine();
        while(med < 0 || med > catalog.size()) {
            System.out.print("Invalid input! Try again: ");
            med = sc.nextInt(); sc.nextLine();
        }
        System.out.print("Enter Quantity of Restock: ");
        int quantity = sc.nextInt(); sc.nextLine();
        while(quantity < 0) {
            System.out.print("Invalid input! Try again: ");
            quantity = sc.nextInt(); sc.nextLine();
        }
        System.out.println("\nConfirm to Update Stock Level?");
        System.out.println("Enter 1 to Confirm; or 2 to Cancel.");
        System.out.print("Enter your choice: ");
        int confirmAdding;

        while (true) {
            confirmAdding = sc.nextInt(); sc.nextLine();
            switch (confirmAdding) {
                case 1:
                    catalog.get(med-1).restock(quantity);
                    System.out.print("Stock Level Successfully Restocked!\n");
                    break;

                case 2:
                    System.out.println("Operation Cancelled.\n");
                    break;

                default:
                    System.out.print("Invalid choice! Try again: ");
                    continue;
            }
            return;
        }
    }

    /**
     * Used by pharmacist to dispense medication
     * @param aop AppointmentOutcomeRecord of the appointment
     * @return true if successful false otherwise
     */
    public boolean dispense(AppointmentOutcomeRecord aop) {
        if(aop.isDispensed()) return false;

        for(Medicine n : aop.getprescription()) {
            int idx = findIndex(n);
            if(catalog.get(idx).amount() < n.amount()){
                return false;
            }
        }

        for(Medicine n : aop.getprescription()) {
            int idx = findIndex(n);
            catalog.get(idx).prescribe(n.amount());

            if(catalog.get(idx).amount() <= lowlevel.get(idx).amount())
                System.out.println(catalog.get(idx).name() + "is at Low amount of "
                        + catalog.get(idx).amount() + ". Please Restock!");
        }

        aop.dispense();
        return true;
    }

    /**
     * Used by pharmacist to request a restock
     */
    public void createRequest() {
        Scanner sc = GlobalData.getInstance().sc;
        System.out.println("Current Inventory:");
        GlobalData.getInstance().inventory.printCurrentInventory();
        System.out.print("Enter Index of Medicine (0 to Cancel): ");
        int med = sc.nextInt(); sc.nextLine();
        while(med < 1 || med > catalog.size()) {
            System.out.print("Invalid input! Try again: ");
            med = sc.nextInt(); sc.nextLine();
        }
        System.out.print("Enter Quantity of Medicine to Request: ");
        int quantity = sc.nextInt(); sc.nextLine();
        while(quantity < 0) {
            System.out.print("Invalid input! Try again: ");
            quantity = sc.nextInt(); sc.nextLine();
        }

        System.out.println("Confirm to Submit Restock Request?");
        System.out.println("Enter 1 to Confirm; or 2 to Cancel.");
        System.out.print("Enter your choice: ");
        int confirmAdding;

        while (true) {
            confirmAdding = sc.nextInt(); sc.nextLine();
            switch (confirmAdding) {
                case 1:
                    requests.add(catalog.get(med-1).copy(quantity));
                    System.out.println("Restock Request Successfully Created!\n");
                    break;

                case 2:
                    System.out.println("Operation Cancelled.\n");
                    break;

                default:
                    System.out.print("Invalid choice! Try again: ");
                    continue;
            }
            return;
        }
    }

    public Medicine[] generatePrescription(){
        System.out.println("\nCurrent Inventory, for your reference:");
        printCurrentInventory();
        ArrayList<Medicine> ret = new ArrayList<Medicine>();
        int med = 1;
        int quantity;
        
        while(med > 0 && med <= catalog.size()) {
            System.out.print("Enter Index of Medicine to Prescribe (0 to Exit): ");
            Scanner sc = GlobalData.getInstance().sc;
            med = sc.nextInt(); sc.nextLine();
            if(med == 0) break;
            while(med < 0 || med > catalog.size()) {
                System.out.print("Invalid input! Try again: ");
                med = sc.nextInt(); sc.nextLine();
            }
            if(ret.isEmpty()) {
                System.out.print("Enter Quantity (0 to Go Back): ");
                quantity = sc.nextInt(); sc.nextLine();
                if(quantity <= 0) continue;

                ret.add(new Medicine(catalog.get(med-1).name(), quantity));
                continue;
            }
            for(int i = 0; i < ret.size(); i++) {
                if (ret.get(i).name().equals(catalog.get(med-1).name())) {
                    System.out.println("Medicine Has Already Been Prescribed!");
                    break;
                }
                System.out.print("Enter Quantity (0 to Go Back): ");
                quantity = sc.nextInt();
                if(quantity <= 0) break;

                ret.add(new Medicine(catalog.get(med-1).name(), quantity));
                break;
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
        Scanner sc = GlobalData.getInstance().sc;
        printCurrentInventory();
        System.out.print("Enter Index of Medicine (0 to cancel): ");
        int med = sc.nextInt(); sc.nextLine();
        if (med == 0) {
            System.out.println("Operation Cancelled. Returning to Menu...");
            return;
        }
        while(med < 0 || med > catalog.size()) {
            System.out.print("Invalid input! Try again: ");
            med = sc.nextInt(); sc.nextLine();
        }
        System.out.print("Enter new Low Level (0 to cancel): ");
        int quantity = sc.nextInt(); sc.nextLine();
        if (quantity == 0) {
            System.out.println("Operation Cancelled. Returning to Menu...");
            return;
        }
        while(quantity < 0) {
            System.out.print("Invalid input! Try again: ");
            quantity = sc.nextInt(); sc.nextLine();
        }
        lowlevel.get(med-1).prescribe(lowlevel.get(med-1).amount());
        lowlevel.get(med-1).restock(quantity);
        System.out.print("Low Level Alert Successfully Updated!");
    }

    /**
     * Used by ... to manage restock
     */
    public void manageRestockRequests() {
        if (requests.isEmpty()) {
            System.out.println("No Pending Restock Requests!\n");
            return;
        }
        printRestockRequest();
        System.out.print("Enter Index of Request: ");
        Scanner sc = GlobalData.getInstance().sc;
        int req = sc.nextInt(); sc.nextLine();
        while(req < 1 || req > requests.size()) {
            System.out.print("Invalid input! Try again: ");
            req = sc.nextInt();
        }
        int idx = findIndex(requests.get(req-1));
        System.out.print("Enter 1 to Exit; 2 to Approve; 3 to Reject.\nEnter your choice: ");
        int choice;
        while (true) {
            choice = sc.nextInt(); sc.nextLine();
            switch (choice) {
                case 1:
                    return;
                case 2:
                    catalog.get(idx).restock(requests.get(req - 1).amount());
                    requests.remove(req - 1);
                    System.out.println("Request Successfully Approved!\n");
                    return;
                case 3:
                    requests.remove(req - 1);
                    System.out.println("Request Successfully Rejected!\n");
                    return;
                default:
                    System.out.print("Invalid choice! Try again: ");
                    break;
            }
        }
    }

    /**
     * Print current inventory
     */
    public void printCurrentInventory() {
        System.out.println("---------------------");
        for(int i = 0; i < catalog.size(); i++) {
            System.out.print((i+1) + ". " + catalog.get(i).name() + ": " + catalog.get(i).amount());
            if(catalog.get(i).amount() <= lowlevel.get(i).amount())
                System.out.println(" **LOW, PLEASE RESTOCK!**");
            else System.out.println();
        }
        System.out.println("---------------------");
    }

    /**
     * Print restock request
     */
    public void printRestockRequest() {
        for(int i = 0; i < requests.size(); i++) {
            System.out.println((i+1) + ". " + requests.get(i).name() + ": " + requests.get(i).amount());
        }
        System.out.println();
    }
}
