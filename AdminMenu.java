import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


public class AdminMenu {
    private static final String FILE_NAME = "src/menu.txt";
    private static final String FILE_NAME2 = "src/credentials.txt";

    public static void main(String[] args) {

        initialDisplay();
        displayMenu();
        choice();
    }

    public static void initialDisplay() {
        System.out.println("Welcome to the Admin portal for the Tasty House Menu! Here is the current food and drink items available:\n ");
    }

    public static void displayMenu() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {


                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String itemName = parts[0].trim();
                    String price = parts[2].trim();
                    System.out.println(itemName + " - " + price);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the menu file: " + e.getMessage());
        }
    }

    public static String choice(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n Would you like to:\n Menu options \n a) add a item to the menu \n b) delete an item \n c) update the price of an item from the menu \n Discount codes \n d) add new discount codes \n e) delete discount codes");
        String choice = scanner.nextLine();
        return choice;
    }


}
