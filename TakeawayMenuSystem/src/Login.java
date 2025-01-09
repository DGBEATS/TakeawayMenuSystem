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

            logOut(userType);
        } else {
            System.out.println("Invalid username or password. Try again.");
        }
        scanner.close();
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
    
    private static boolean usernameExists(String username) {
    	try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))){
    		String line;
    		while ((line = reader.readLine()) != null) {
    			String[] credentials = line.split(",");
    			if (credentials.length > 0 && credentials[0].equals(username)) {
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
        
        while (usernameExists(username)) {
        	System.out.println("Username already exists. Please choose another: ");
        	username = scanner.nextLine();
        }
        
        System.out.println("Enter a new password (commas are not allowed):");
        String password = scanner.nextLine();
        
        while (password.contains(",")) {
        	System.out.println("Error: Password cannot contains commas.");
        	System.out.println("Enter a new password:");
        	password = scanner.nextLine();
        }

//        System.out.println("Enter a new password:");
//        String password = scanner.nextLine();
        
        //PHONE NUMBER + VALIDATION
        System.out.println("Enter your phone number (numbers only): ");
        String phone = scanner.nextLine();
        while (!phone.matches("\\d+")) {
        	System.out.println("Invalid phone number. Please enter numbers only:");
        	phone = scanner.nextLine();
        }
        
        //EMAIL + VALIDATION
        System.out.println("Enter your email address: ");
        String email = scanner.nextLine();
        while (!email.contains("@") || !email.contains(".")) {
        	System.out.println("Invalid email format. Please enter a valid email address: ");
        	email = scanner.nextLine();
        }
        
        //ADDRESS + VALIDATION
        System.out.println("Enter your address (no commmas allowed): ");
        String address = scanner.nextLine();
        while (address.contains(",")) {
        	System.out.println("Address cannot contains commas. Please enter again: ");
        	address = scanner.nextLine();
        }
        
        //BANK DETAILS + VALIDATION
        System.out.println("Enter your card number (16 digits): ");
        String cardNumber = scanner.nextLine();
        while (!cardNumber.matches("\\d{16}")) {
        	System.out.println("Invalid card number. Please enter 16 digits: ");
        	cardNumber = scanner.nextLine();
        }

        if (addCredentials(username, password, userType, phone, email, address, cardNumber)) {
            System.out.println("Account created successfully! You can now log in.");
        } else {
            System.out.println("Error creating account. Please try again.");
        }
        scanner.close();
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

    private static void logOut(String userType) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Would you like to log out? (y/n)");
        String answer = scanner.nextLine();
        if (answer.equalsIgnoreCase("y")) {
            System.out.println("You have been logged out. Returning to guest status.");
            main(new String[]{});
        } else if (answer.equalsIgnoreCase("n")) {
            System.out.println("You are still logged in as a " + userType + ".");
        } else {
            System.out.println("Invalid input. Please type 'y' or 'n'.");
            logOut(userType);
        }
        scanner.close();
    }
}
