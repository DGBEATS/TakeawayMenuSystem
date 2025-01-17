package Menu;

import java.io.*;
import java.util.*;

public class MenuSystem {
    private OrderManager orderManager;
    private Scanner scanner;

    public MenuSystem() {
        this.orderManager = new OrderManager();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        displayMenu();
        orderManager.processOrder();
    }

    public void displayMenu() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/menu.txt"))) {
            String line;
            System.out.println("\n=== MENU ===");
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
}
