package KMenu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class KMenu {

    public static void main(String[] args) {
        MenuSystem menuSystem = new MenuSystem();
        menuSystem.start();
    }
}

class MenuSystem {
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
                    System.out.println(itemName + " - £" + price);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the menu file: " + e.getMessage());
        }
    }
}

class OrderManager {
    private List<Order> orders;
    private Scanner scanner;
    private String deliveryAddress;
    private String time = "";

    public OrderManager() {
        this.orders = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public void processOrder() {
        Order order = new Order();
        double basketCost = 0;

        while (true) {
            try {
                System.out.println("\nOrder Options:");
                System.out.println("1. Add Items");
                System.out.println("2. View Order");
                System.out.println("3. Confirm Order");
                System.out.println("4. Remove Items");
                System.out.println("5. Add Drink");
                System.out.println("6. Cancel Order");
                System.out.print("Choose an option: ");

                String input = scanner.nextLine();
                int choice = Integer.parseInt(input);

                switch (choice) {
                    case 1 -> {
                        MenuSystem menuSystem = new MenuSystem();
                        menuSystem.displayMenu();
                        basketCost = addItemsToBasket(order);
                        System.out.println("Total Basket Cost: £" + basketCost);
                    }
                    case 2 -> order.displayOrder();
                    case 3 -> {
                        if (order.isEmpty()) {
                            System.out.println("You cannot confirm an order without adding items to the basket. Please add items.");
                            break;
                        }
                        orders.add(order);
                        System.out.println("Order confirmed!");
                        printReceipt(order, basketCost);
                        return;
                    }
                    case 4 -> removeItemsFromBasket(order);
                    case 5 -> {
                        System.out.println("Adding drinks to your order...");
                        basketCost += addItemsToBasket(order);
                        System.out.println("Updated Total Basket Cost: £" + basketCost);
                    }
                    case 6 -> {
                        System.out.println("Order cancelled.");
                        return;
                    }
                    default -> System.out.println("Invalid option. Please enter a number between 1 and 6.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 6.");
            }
        }
    }

    private double addItemsToBasket(Order order) {
        double basketCost = 0;
        System.out.println("Type the name of the item to add to your basket or type 'done' to finish:");

        while (true) {
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("done")) {
                break;
            }

            boolean itemFound = false;
            try (BufferedReader reader = new BufferedReader(new FileReader("src/menu.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 3) {
                        String itemName = parts[0].trim();
                        double price = Double.parseDouble(parts[2].trim());

                        if (input.equalsIgnoreCase(itemName)) {
                            order.addItem(itemName, price, 1);
                            System.out.println(itemName + " added to your basket!");
                            basketCost += price;
                            itemFound = true;
                            break;
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Error reading the menu file: " + e.getMessage());
            }

            if (!itemFound) {
                System.out.println("Invalid item name. Please select an item from the menu.");
            }
        }
        return basketCost;
    }

    private void removeItemsFromBasket(Order order) {
        System.out.println("Enter the name of the item to remove from your basket or type 'done' to finish:");

        while (true) {
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("done")) {
                break;
            }

            if (order.removeItem(input)) {
                System.out.println(input + " removed from your basket.");
            } else {
                System.out.println("Item not found in your basket.");
            }
        }
    }

    private void printReceipt(Order order, double basketCost) {
        System.out.println("\n=== RECEIPT ===");
        System.out.println("Order ID: " + order.getOrderId());
        order.displayOrder();
        System.out.printf("Total Cost: £%.2f\n", basketCost);
        System.out.println("Payment Method: Online");
        System.out.println("Thank you for your purchase!");
    }
}

class Order {
    private static int idCounter = 1;
    private int orderId;
    private Map<String, ItemDetails> items;

    public Order() {
        this.orderId = idCounter++;
        this.items = new HashMap<>();
    }

    public void addItem(String itemName, double price, int quantity) {
        items.putIfAbsent(itemName, new ItemDetails(price, 0));
        items.get(itemName).quantity += quantity;
    }

    public boolean removeItem(String itemName) {
        if (items.containsKey(itemName)) {
            items.remove(itemName);
            return true;
        }
        return false;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public int getOrderId() {
        return orderId;
    }

    public void displayOrder() {
        System.out.println("\nOrder ID: " + orderId);
        System.out.println("Items:");
        double totalCost = 0;
        for (Map.Entry<String, ItemDetails> entry : items.entrySet()) {
            String itemName = entry.getKey();
            ItemDetails details = entry.getValue();
            double itemCost = details.price * details.quantity;
            totalCost += itemCost;
            System.out.printf("- %s x%d (£%.2f each) - £%.2f\n", itemName, details.quantity, details.price, itemCost);
        }
        System.out.printf("Total Cost: £%.2f\n", totalCost);
    }
}

class ItemDetails {
    double price;
    int quantity;

    public ItemDetails(double price, int quantity) {
        this.price = price;
        this.quantity = quantity;
    }
}
