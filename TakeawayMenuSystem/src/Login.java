import java.io.*;
import java.util.*;

public class Login {

    private static final String FILE_NAME = "src/credentials.txt";
    private static String username;
    private static String password;
    private static String phone;
    private static String email;
    private static String cardNumber;
    private static String userType;

    private static final Scanner scanner = new Scanner(System.in); // Single Scanner instance

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
            logOut(userType); // Logout flow
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

        System.out.println("Enter your phone number (numbers only): ");
        phone = scanner.nextLine();
        while (!phone.matches("\\d+")) {
            System.out.println("Invalid phone number. Please enter numbers only:");
            phone = scanner.nextLine();
        }

        System.out.println("Enter your email address: ");
        email = scanner.nextLine();
        while (!email.contains("@") || !email.contains(".")) {
            System.out.println("Invalid email format. Please enter a valid email address:");
            email = scanner.nextLine();
        }

        System.out.println("Enter your address (no commas allowed): ");
        String address = scanner.nextLine();
        while (address.contains(",")) {
            System.out.println("Address cannot contain commas. Please enter again:");
            address = scanner.nextLine();
        }

        System.out.println("Enter your card number (16 digits): ");
        cardNumber = scanner.nextLine();
        while (!cardNumber.matches("\\d{16}")) {
            System.out.println("Invalid card number. Please enter 16 digits:");
            cardNumber = scanner.nextLine();
        }

        if (addCredentials(username, password, userType, phone, email, address, cardNumber)) {
            System.out.println("Account created successfully! You can now log in.");
            login(userType); //fixed bug where program would stop after acc creation
        } else {
            System.out.println("Error creating account. Please try again.");
        }
    }

    private static boolean addCredentials(String username, String password, String userType, String phone, String email, String address, String cardNumber) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(username + "," + password + "," + userType + "," + phone + "," + email + "," + address + "," + cardNumber);
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
                            // If card credentials dont want to be printed insert similiar line here
                            switch (i) {
                                case 0 -> System.out.println("Username: " + details[i]);
                                case 2 -> System.out.println("User Type: " + details[i]);
                                case 3 -> System.out.println("Phone: " + details[i]);
                                case 4 -> System.out.println("Email: " + details[i]);
                                case 5 -> System.out.println("Address: " + details[i]);
                                case 6 -> System.out.println("Card Number: " + details[i]);
                                default -> System.out.println("Additional Info: " + details[i]);
                            }
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

    private static void logOut(String userType) {
        System.out.println("Would you like to log out? (y/n)");
        String answer = scanner.nextLine();
        if (answer.equalsIgnoreCase("y")) {
            System.out.println("You have been logged out. Returning to guest status.");
            main(new String[]{}); // Restart the program
        } else if (answer.equalsIgnoreCase("n")) {
            System.out.println("You are still logged in as a " + userType + ".");
        } else {
            System.out.println("Invalid input. Please type 'y' or 'n'.");
            logOut(userType);
        }
    }
}
