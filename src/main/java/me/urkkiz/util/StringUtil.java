package me.urkkiz.util;

import java.util.Locale;
import java.util.Scanner;

public class StringUtil {
    public static Scanner scanner = new Scanner(System.in);

    public static String UserLineInput(String message) {
        if(!message.isEmpty())System.out.println(message);
        String input=scanner.nextLine();
        return !input.isEmpty() ? input : "0";
    }
    public static boolean UserYNInput(String message) {
        if(!message.isEmpty())System.out.println(message);
        String input=scanner.nextLine();
        return !input.isEmpty() && (input.toUpperCase(Locale.ROOT).equals("Y") || input.toUpperCase(Locale.ROOT).equals("YES"));
    }
}