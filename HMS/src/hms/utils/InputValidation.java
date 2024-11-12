package hms.utils;

import java.util.Scanner;

import hms.GlobalData;

public class InputValidation {
    private static boolean checkString(String check){
        for(int i = 0; i < check.length(); i++) {
            if(check.charAt(i) == ',') return false;
        }

        return true;
    }

    /**
     * Use this input function to ensure no comma
     * @return checked input
     */
    public static String next(){
        Scanner sc = GlobalData.getInstance().sc;
        String check = sc.next();

        while(!checkString(check)) {
            System.out.print("This input is not allow to have ','. Enter again :");
            check = sc.next();
        }

        return check;
    } 

    /**
     * Use this input function to ensure no comma
     * @return checked input
     */
    public static String nextLine(){
        Scanner sc = GlobalData.getInstance().sc;
        String check = sc.nextLine();

        while(!checkString(check)) {
            System.out.print("This input is not allow to have ','. Enter again :");
            check = sc.nextLine();
        }

        return check;
    } 
}
