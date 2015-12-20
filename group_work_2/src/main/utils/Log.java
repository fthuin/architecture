package utils;

public class Log {

    public static boolean DEBUG = true;

    public static void print(String s) {
        if (DEBUG) {
            System.out.println(s);
            System.out.flush();
        }
    }

    public static void error(String s) {
        if (DEBUG) {
            System.err.println("Error : " +s);
            System.err.flush();
        }
    }

}