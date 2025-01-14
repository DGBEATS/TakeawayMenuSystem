
import java.io.*;
import java.util.*;

public class Login {

    private static final String FILE_NAME = "src/credentials.txt";
    private static String username;
    private static String password;
    private static String userType;

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Would you like to access the takeaway menu system as an admin, customer, or remain as a guest?");
        userType = scanner.nextLine();

        if (userType.equalsIgnoreCase("customer") || userType.equalsIgnoreCase("admin")) {
            System.out.println("Do you have an account? Please enter y/n");

            if (scanner.nextLine().equalsIgnoreCase("y")) {
                login(userType);
            } else {
                createAccount(userType);
            }
        } else {
            System.out.println("Please continue to the takeaway menu system as a guest.");
            MenuSystem menuSystem = new MenuSystem();
            menuSystem.start();
        }
    }

    public static void login(String userType) {
        System.out.println("Enter your username:");
        username = scanner.nextLine();

        System.out.println("Enter your password:");
        password = scanner.nextLine();

        if (validateCredentials(username, password, userType)) {
            System.out.println("Welcome to the " + userType + " portal!");
            printCredentials(username); // Show account details
            if (userType.equalsIgnoreCase("customer")) {
                // Calls MenuSystem for customers after successful login
                MenuSystem menuSystem = new MenuSystem();
                menuSystem.start(); // Start the menu interaction
            } else {
                // Handle admin login (not implemented here as this is not my user story)
                System.out.println("You are logged in as an Admin.");
            }
        } else {
            System.out.println("Invalid username or password. Try again.");
        }
    }

    private static boolean validateCredentials(String username, String password, String userType) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] credentials = line.split(",");
                if (credentials.length >= 3 &&
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
        System.out.println("Enter a new username:");
        username = scanner.nextLine();

        while (usernameExists(username)) {
            System.out.println("Username already exists. Please choose another: ");
            username = scanner.nextLine();
        }

        System.out.println("Enter a new password (commas are not allowed):");
        password = scanner.nextLine();

        while (password.contains(",")) {
            System.out.println("Error: Password cannot contain commas.");
            password = scanner.nextLine();
        }

        if (addCredentials(username, password, userType)) {
            System.out.println("Account created successfully! You can now log in.");
            login(userType); // Fixed bug where program would stop after account creation
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

    private static boolean usernameExists(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] credentials = line.split(",");
                if (credentials[0].equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the credentials file: " + e.getMessage());
        }
        return false;
    }

    private static void printCredentials(String username) {
        System.out.println("Would you like to see your account details? (excluding password) (y/n)");
        String answer = scanner.nextLine();

        if (answer.equalsIgnoreCase("y")) {
            try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
                String line;
                boolean userFound = false;

                while ((line = reader.readLine()) != null) {
                    String[] details = line.split(",");
                    if (details[0].equals(username)) {
                        userFound = true;

                        System.out.println("Account Details:");
                        for (int i = 0; i < details.length; i++) {
                            if (i == 1) continue; // Skip password
                            System.out.println("Username: " + details[0]);
                            System.out.println("User Type: " + details[2]);
                        }
                        break;
                    }
                }

                if (!userFound) {
                    System.out.println("No account details found for username: " + username);
                }
            } catch (IOException e) {
                System.out.println("Error reading the credentials file: " + e.getMessage());
            }
        } else if (answer.equalsIgnoreCase("n")) {
            System.out.println("No problem. Please carry on (:");
        } else {
            System.out.println("Invalid input. Please try again.");
        }
    }
}
