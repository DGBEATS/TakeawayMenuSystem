import java.util.Scanner;

public class Login{

    public static void main(String[] args) {
        String adminuser = "admin";
        String adminpass = "pass1";

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your admin username to access the admin portal");
        String adminusercheck = scanner.nextLine();

        System.out.println("Enter your admin password to access the admin portal");
        String adminpasscheck = scanner.nextLine();

        if (adminusercheck.equals(adminuser) && (adminpasscheck.equals(adminpass))) {
            System.out.println("Welcome to the admin portal");
        } else {
            System.out.println("Invalid username or password. Try again");

        }
    }
}
