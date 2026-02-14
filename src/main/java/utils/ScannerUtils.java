package utils;

import java.util.Scanner;

public class ScannerUtils {
    static Scanner scanner = new Scanner(System.in);
    public static int getValidInt(String prompt){
        System.out.println(prompt);
        while (!scanner.hasNextInt()){
            System.out.println("Please enter a whole number");
            scanner.next();
        }
        int entry = scanner.nextInt();
        scanner.nextLine();
        return entry;
    }

    public static int getValidPositiveInt(String prompt){}

    public static double getValidDouble(String prompt){
        System.out.println(prompt);
        while (!scanner.hasNextDouble()){
            System.out.println("Please enter number with up to two decimal places");
            scanner.next();
        }
        Double entry = scanner.nextDouble();
        scanner.nextLine();
        return entry;
    }

    public static double getValidPositiveDouble(String prompt){}
}
