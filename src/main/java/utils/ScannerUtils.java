package utils;

import java.util.Scanner;

public class ScannerUtils {
    static Scanner scanner = new Scanner(System.in);
    public static int getValidInt(String prompt){      //Exiting will return 0
        while (true){
            System.out.println(prompt);
            String input = scanner.nextLine().trim();
            if(input.equalsIgnoreCase("exit")){return -1;}
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e){
                System.out.println("Invalid input. Enter a whole integer, or exit");
            }
        }
    }

    public static int getValidPositiveInt(String prompt){
        while (true){
            System.out.println(prompt);
            String input = scanner.nextLine().trim();
            if(input.equalsIgnoreCase("exit")){return -1;}
            try {
                int result = Integer.parseInt(input);
                if(result >= 0){return result;} else {
                    System.out.println("Number must be 0 or greater");
                }
            } catch (NumberFormatException e){
                System.out.println("Invalid input. Enter a whole integer, or exit");
            }
        }
    }

    public static double getValidDouble(String prompt) {
        while (true) {
            System.out.println(prompt);
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("exit")) {
                return -1.00;
            }
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a whole integer, or exit");
            }
        }
    }

    public static double getValidPositiveDouble(String prompt){
            while (true){
                System.out.println(prompt);
                String input = scanner.nextLine().trim();
                if(input.equalsIgnoreCase("exit")){return -1.00;}
                try {
                    double result = Double.parseDouble(input);
                    if(result >= 0){return result;} else {
                        System.out.println("Number must be 0 or greater");
                    }
                } catch (NumberFormatException e){
                    System.out.println("Invalid input. Enter a whole integer, or exit");
                }
            }
    }
}
