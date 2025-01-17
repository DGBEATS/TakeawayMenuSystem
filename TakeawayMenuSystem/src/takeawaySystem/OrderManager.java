package takeawaySystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class OrderManager {
    private List<Order> orders;
    private Scanner scanner;
    private String deliveryAddress;
    private String allergyInfo;
    private String time;

    public OrderManager() {
        this.orders = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public void processOrder() {
        while (true) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Start New Order");
            System.out.println("2. View and Reorder Previous Orders");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            try {
                int mainChoice = scanner.nextInt();
                scanner.nextLine();

                switch (mainChoice) {
                    case 1 -> {
                        Order order = new Order();
                        handleOrderProcess(order);
                    }
                    case 2 -> {
                        if (orders.isEmpty()) {
                            System.out.println("No previous orders available.");
                        } else {
                            System.out.println("\nPrevious Orders:");
                            for (Order o : orders) {
                                o.displayOrder();
                                System.out.println();
                            }
                            System.out.print("Enter the Order ID to reorder, or '0' to return to the main menu: ");
                            int orderId = scanner.nextInt();
                            scanner.nextLine();

                            if (orderId != 0) {
                                Order previousOrder = orders.stream()
                                        .filter(o -> o.getOrderId() == orderId)
                                        .findFirst()
                                        .orElse(null);

                                if (previousOrder != null) {
                                    System.out.println("Reordering Order ID: " + orderId);
                                    Order newOrder = new Order();
                                    for (Map.Entry<String, ItemDetails> entry : previousOrder.items.entrySet()) {
                                        String itemName = entry.getKey();
                                        ItemDetails details = entry.getValue();
                                        newOrder.addItem(itemName, details.price, details.quantity);
                                    }
                                    handleOrderProcess(newOrder);
                                } else {
                                    System.out.println("Invalid Order ID.");
                                }
                            }
                        }
                    }
                    case 3 -> {
                        System.out.println("Thank you for using the system. Goodbye!");
                        return;
                    }
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }

    private void handleOrderProcess(Order order) {
        double basketCost = 0;

        while (true) {
            System.out.println("\nOrder Options:");
            System.out.println("1. Add Items");
            System.out.println("2. View Order");
            System.out.println("3. Confirm Order");
            System.out.println("4. Leave Review");
            System.out.println("5. Extra Information");
            System.out.println("6. Add Delivery Info");
            System.out.println("7. Change/Add Delivery Address");
            System.out.println("8. Schedule Delivery");
            System.out.println("9. Enter Allergy Information");
            System.out.println("10. Cancel Order");
            System.out.println("11. Remove Items");
            System.out.println("12. Return to Main Menu");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    MenuSystem menuSystem = new MenuSystem();
                    menuSystem.displayMenu();
                    basketCost = foodBasket(order);
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
                case 10 -> {
                    System.out.println("Order cancelled.");
                    return;
                }
                case 11 -> removeItemsFromBasket(order);
                case 12 -> {
                    System.out.println("Returning to the main menu...");
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private double foodBasket(Order order) {
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
                        double price = Double.parseDouble(parts[2].trim().replace("£", "").replace("$", ""));

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
            } catch (NumberFormatException e) {
                System.out.println("Error parsing price: " + e.getMessage());
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
        System.out.println("Thank you for your purchase!");
    }
}