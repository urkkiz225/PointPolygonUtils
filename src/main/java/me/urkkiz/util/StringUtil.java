package me.urkkiz.util;

import java.util.Scanner;

public class StringUtil {
    private static Scanner scanner = new Scanner(System.in);

    public static String UserLineInput() {
        return UserLineInput().length() != 0 ? scanner.nextLine() : "0";
    }

    public static String UserCharInput() {
        return scanner.next();
    }

    public static int UserIntInput(){
    return scanner.nextInt();
    }
}
