package tp6103022330089.tubesdpbo;

import java.util.Scanner;

public class ScannerUtil {
    public static String scanString(Scanner scan) {
        return scan.nextLine().trim();
    }

    public static int scanInt(Scanner scan) {
        while (true) {
            try {
                return Integer.parseInt(scan.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Input tidak valid, silakan masukkan angka: ");
            }
        }
    }

    public static char scanChar(Scanner scan) {
        while (true) {
            String userInput = scan.nextLine().trim().toUpperCase();
            if (userInput.equals("Y") || userInput.equals("N")) {
                return userInput.charAt(0);
            }
            System.out.print("Input tidak valid, masukkan 'Y' atau 'N': ");
        }
    }

    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        String regex = "^[\\w.-]+@([\\w-]+\\.)+[\\w-]{2,}$";
        return email.trim().matches(regex);
    }
}
