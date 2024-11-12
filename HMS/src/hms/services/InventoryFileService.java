package hms.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

import hms.pharmacy.Inventory;
import hms.utils.InputValidation;

public class InventoryFileService extends InputValidation {
    /**
	 * Read medicine from file and generate initial inventory
	 * @return initial inventory
	 */
    public static Inventory getInventory() {
        Inventory setup = new Inventory();

        try {
            File myObj = new File("HMS/src/data/Medicine_List.txt");
            Scanner myReader = new Scanner(myObj);
            myReader.nextLine(); // Remove header line

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] dataList = data.split(",");

                setup.addNewMedicine(dataList[0], Integer.valueOf(dataList[1]), Integer.valueOf(dataList[2]));
            }
            myReader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            return new Inventory();
        }

        try {
            File myObj = new File("HMS/src/data/Restock_List.txt");
            Scanner myReader = new Scanner(myObj);
            myReader.nextLine(); // Remove header line

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] dataList = data.split(",");

                setup.addRequest(dataList[0], Integer.valueOf(dataList[1]));
            }

            myReader.close();

            return setup;
        }

        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            return setup;
        }

    }

    /**
     * write inventory
     * @param inventory inventory to be written
     */
    public static void writeInventory(Inventory inventory) {
        try {
            FileWriter fw = new FileWriter("HMS/src/data/Medicine_List.txt");
            fw.write("Medicine Name,Initial Stock,Low Stock Level Alert\n");
            for(int i = 0; i < inventory.getSize(); i++) {
                fw.write(inventory.getName(i) + "," + inventory.getAmount(i) + "," + inventory.getLowLevel(i) +"\n");
            }
            fw.close();
            
        }
        catch (Exception e){
            System.out.println("An error occurred. Cannot write inventory");
        }

        try {
            FileWriter fw = new FileWriter("HMS/src/data/Restock_List.txt");
            fw.write("Medicine Name,Request Amount\n");
            for(int i = 0; i < inventory.getRequestSize(); i++) {
                fw.write(inventory.getRequestName(i) + "," + inventory.getRequestAmount(i) +"\n");
            }
            fw.close();
        }
        catch (Exception e){
            System.out.println("An error occurred. Cannot write restock");
        }
    }
}
