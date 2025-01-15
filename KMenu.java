package Menu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Menu {



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
                    System.out.println(itemName + " - " + price);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the menu file: " + e.getMessage());
        }
    }

    

class OrderManager {
    private List<Order> orders;
    private Scanner scanner;
    private String deliveryaddress;
    String time = "";

    public OrderManager() {
        this.orders = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public void processOrder() {
        Order order = new Order();
        String cutlery = "";
        String paymentMethod = "";
        String review = "";
        String extraInfo = "";
        String deliveryInfo = "";
        double basketCost = 0;

        while (true) {
            System.out.println("\nOrder Options:");
            System.out.println("1. Add Items");
            System.out.println("2. View Order");
            System.out.println("3. Confirm Order");
            System.out.println("4. Leave Review");
            System.out.println("5. Extra Information");
            System.out.println("6. Add delivery info");
            System.out.println("7. Change delivery address/ Add address for guests");
            System.out.println("8. Schedule delivery for later");
            System.out.println("9. Cancel Order");
            System.out.println("10. Remove Items");
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
                     
                     

                    // Get order details
                    cutlery = getCutlery();
                    paymentMethod = getPaymentMethod();
                    orders.add(order);
                    System.out.println("Order confirmed!\nCutlery: " + cutlery);
                    printReceipt(order, basketCost);
                    
                    System.out.println("Payment Method: " + paymentMethod);


                    printOrderSummary(order, basketCost, cutlery, paymentMethod, review, extraInfo, deliveryInfo);

                    return;
                }
                case 4 -> {
                    review = getReview();
                    System.out.println("Review: " + review);
                }
                case 5 -> {
                    extraInfo = extraInfo();
                    System.out.println("Extra Information: " + extraInfo);
                }
                case 6 -> {
                    deliveryInfo = deliveryInfo();
                    System.out.println("Extra delivery details: " + deliveryInfo);
                }
                case 7 -> {
                    deliveryaddress = deliveryAddress();
                    System.out.println("Delivery address is:" + deliveryaddress);
                }
                case 8 ->{
                    time = orderTime();
                    System.out.println("Order time: " + time);
                }
                case 9 -> {
                    System.out.println("Order cancelled.\n");
                    return;
                }
                case 10 -> removeItemsFromBasket(order);

                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void printOrderSummary(Order order, double basketCost, String cutlery, String review, String extraInfo, String deliveryInfo, String deliveryAddress) {
        System.out.println("\nYour order summary:");

        System.out.println("Items ordered:");
        order.displayOrder();

        System.out.println("\nOrder Receipt:");
        System.out.printf("Total Basket Cost: £%.2f%n%n", basketCost);

        System.out.println("Delivery Method: " + (isNullOrEmpty(deliveryInfo) ? "Standard" : deliveryInfo));
        System.out.println("Cutlery: " + cutlery);
        System.out.println("Review: " + (isNullOrEmpty(review) ? "No review" : review));
        System.out.println("Extra Information: " + (isNullOrEmpty(extraInfo) ? "No extra information" : extraInfo));
        System.out.println("Delivery Information: " + (isNullOrEmpty(deliveryInfo) ? "No additional delivery information" : deliveryInfo));
        System.out.println("Delivery is scheduled for " + (isNullOrEmpty(deliveryInfo) ? "as soon as order processed" : deliveryInfo));
        System.out.println("Delivery address: " + (isNullOrEmpty(deliveryAddress) ? "No change to delivery address" : deliveryAddress));
    }

    // Helper method to check if a string is null or empty
    private boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }


    public String getCutlery() {
        System.out.println("Do you want to add cutlery to the order? (y/n)");
        String input = scanner.nextLine().toLowerCase();
        return input.equals("y") ? "Cutlery provided" : "No cutlery provided";
    }

    public String getPaymentMethod() {
        System.out.println("Do you want to pay a: online or b: in person?");
        String input = scanner.nextLine().toLowerCase();
        return (input.equals("a") || input.equals("online")) ? "Online payment" : "In person payment";
    }

    public String orderTime(){
        System.out.println("Do you want to schedule your order for a specific time? (y/n)");
        String input = scanner.nextLine().toLowerCase();
        if (input.equals("y")) {
            System.out.println("What time do you want to schedule your order for?");
        } else if (input.equals("n")) {
            time = "ASAP";
        }
        return time;
    }

    public String getReview() {
        System.out.println("Do you want to leave a review? (y/n)");
        String input = scanner.nextLine().toLowerCase();
        if (input.equals("y")) {
            System.out.println("Leave a review below:");
            return scanner.nextLine();
        }
        return "No review";
    }

    public String extraInfo() {
        System.out.println("Do you want to add any extra information to your order? (y/n)");
        String input = scanner.nextLine().toLowerCase();
        if (input.equals("y")) {
            System.out.println("Enter the extra information you would like to add:");
            return scanner.nextLine();
        }
        return "No extra information provided";
    }

    public String deliveryInfo() {
        System.out.println("Do you want to add any extra information for delivery? (y/n)");
        String input = scanner.nextLine().toLowerCase();
        if (input.equals("y")) {
            System.out.println("Enter the delivery information you would like to add:");
            return scanner.nextLine();
        }
        return "No delivery information provided";
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
                        // Remove currency symbols or any non-numeric characters
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
        System.out.println("Payment Method: Online");
        System.out.println("Thank you for your purchase!");
    }

    public String deliveryAddress() {
        System.out.println("Would you like to enter a new address for delivery that is not on the system? (y/n)");
        String input = scanner.nextLine().toLowerCase();
        if (input.equals("y")) {
            System.out.println("Enter the address you would like to deliver to:");
            input = scanner.nextLine();
            return input;
        }
        return "No address change/ collection";
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
}



