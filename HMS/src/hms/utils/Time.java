package hms.utils;

public class Time {
    static int startTime = 800;
    static int endTime = 1800;
    static int breakStart = 1200;
    static int breakEnd = 1330;

    public static boolean checkTime(int time) {
        if (time % 100 != 0 || time % 100 != 30) {
            System.out.println("Only 00/30 is allowed for minute.");
            return false;
        }
        if (time >= breakStart && time <breakEnd) {
            System.out.println("Doctor at break!");
            return false;
        }
        if (time <startTime || time >breakEnd) {
            System.out.println("Doctor not working!");
            return false;
        }
        return true;
    }

}
