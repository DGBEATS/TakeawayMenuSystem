import java.util.Scanner;

public class Login {

    public static void main(String[] args) {
        String adminuser = "admin";
        String adminpass = "pass1";

        String customer = "customer";
        String customerpass = "pass1";

        Scanner scanner = new Scanner(System.in);

        System.out.println("Would you like to access the takeaway menu system as an admin, customer, or remain as a guest?");
        String userType = scanner.nextLine();

        if (userType.equalsIgnoreCase("admin")) {
            System.out.println("Enter your admin username to access the admin portal:");
            String adminusercheck = scanner.nextLine();

            System.out.println("Enter your admin password to access the admin portal:");
            String adminpasscheck = scanner.nextLine();

            if (adminusercheck.equals(adminuser) && adminpasscheck.equals(adminpass)) {
                System.out.println("Welcome to the admin portal!");
            } else {
                System.out.println("Invalid username or password. Try again.");
            }

        } else if (userType.equalsIgnoreCase("customer")) {
            System.out.println("Enter your customer username to access the customer portal:");
            String customerusercheck = scanner.nextLine();

            System.out.println("Enter your customer password to access the customer portal:");
            String customerpasscheck = scanner.nextLine();

            if (customerusercheck.equals(customer) && customerpasscheck.equals(customerpass)) {
                System.out.println("Welcome to the customer portal!");
            } else {
                System.out.println("Invalid username or password. Try again.");
            }

        } else {
            System.out.println("Please continue to the takeaway menu system as a guest.");
        }

        scanner.close();
    }
}
