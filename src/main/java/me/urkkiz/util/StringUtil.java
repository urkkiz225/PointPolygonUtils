package me.urkkiz.util;

import java.util.Scanner;

public class StringUtil {
    public static Scanner scanner = new Scanner(System.in);

    public static String UserLineInput() {
        String input=scanner.nextLine();
        return input.length() != 0 ? input : "0";
    }

    public static String UserCharInput() {
        return scanner.next();
    }

    public static int UserIntInput(){
    return scanner.nextInt();
    }
}
