package me.urkkiz.util;

import java.util.Scanner;

public class StringUtil {
    public static Scanner scanner = new Scanner(System.in);

    public static String UserLineInput(String message) {
        if(message.length()!=0)System.out.println(message);
        String input=scanner.nextLine();
        return input.length() != 0 ? input : "0";
    }
}