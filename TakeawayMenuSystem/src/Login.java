import java.io.*;
import java.util.*;

public class Login {

    private static final String FILE_NAME = "src/credentials.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Would you like to access the takeaway menu system as an admin, customer, or remain as a guest?");
        String userType = scanner.nextLine();

        if (userType.equalsIgnoreCase("customer") || userType.equalsIgnoreCase("admin")) {
            System.out.println("Do you have an account? Please enter y/n");

            if (scanner.nextLine().equalsIgnoreCase("y")) {
                login(userType);
            } else {
                createAccount(userType);
            }
        } else {
            System.out.println("Please continue to the takeaway menu system as a guest.");
        }

        scanner.close();
    }

    private static void login(String userType) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your username:");
        String username = scanner.nextLine();

        System.out.println("Enter your password:");
        String password = scanner.nextLine();

        if (validateCredentials(username, password, userType)) {
            System.out.println("Welcome to the " + userType + " portal!");
        } else {
            System.out.println("Invalid username or password. Try again.");
        }
    }

    private static boolean validateCredentials(String username, String password, String userType) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] credentials = line.split(",");
                if (credentials.length == 3 &&
                        credentials[0].equals(username) &&
                        credentials[1].equals(password) &&
                        credentials[2].equalsIgnoreCase(userType)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the credentials file: " + e.getMessage());
        }
        return false;
    }

    private static void createAccount(String userType) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a new username:");
        String username = scanner.nextLine();

        System.out.println("Enter a new password:");
        String password = scanner.nextLine();

        if (addCredentials(username, password, userType)) {
            System.out.println("Account created successfully! You can now log in.");
        } else {
            System.out.println("Error creating account. Please try again.");
        }
    }

    private static boolean addCredentials(String username, String password, String userType) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(username + "," + password + "," + userType);
            writer.newLine();
            return true;
        } catch (IOException e) {
            System.out.println("Error writing to the credentials file: " + e.getMessage());
        }
        return false;
    }
}
