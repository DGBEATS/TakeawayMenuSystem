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

    public static double foodBasket(Order order) {
        Scanner scanner = new Scanner(System.in);
        double basketCost = 0;

        System.out.println("\nEnter the name of the item you want to add to your basket.");
        System.out.println("Type 'done' when you are finished:");

        while (true) {
            String input = scanner.nextLine().trim().toLowerCase();

            // Check if the user is done
            if (input.equals("done")) {
                break;
            }

            boolean found = false;

            try (BufferedReader reader = new BufferedReader(new FileReader("src/menu.txt"))) {
                String line;

                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 3) {
                        String itemName = parts[0].trim();
                        String price = parts[2].trim();

                        double priceAsDouble = Double.parseDouble(price);

                        if (input.equalsIgnoreCase(itemName)) {
                            // Item found and added to basket
                            System.out.println("You selected: " + itemName + " - Price: £" + price);
                            basketCost += priceAsDouble;
                            order.addItem(itemName, priceAsDouble, 1);
                            System.out.println("Adding " + itemName + " to your basket...");
                            System.out.println("Current basket cost: £" + basketCost + "\n");
                            found = true;
                            break;
                        }
                    }
                }

                if (!found) {
                    System.out.println("Please select a valid item from the menu!\n");
                }
            } catch (IOException e) {
                System.out.println("Error reading the menu.txt file: " + e.getMessage());
            }
        }
        return basketCost;
    }
}

class OrderManager {
    private List<Order> orders;
    private Scanner scanner;
    private String deliveryaddress;

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
            System.out.println("8. Cancel Order");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    MenuSystem menuSystem = new MenuSystem();
                    menuSystem.displayMenu();
                    basketCost = MenuSystem.foodBasket(order);
                    System.out.println("Total Basket Cost: £" + basketCost);
                }
                case 2 -> order.displayOrder();
                case 3 -> {
                    // Get order details
                    cutlery = getCutlery();
                    orders.add(order);
                    System.out.println("Order confirmed!\nCutlery: " + cutlery);

                    paymentMethod = getPaymentMethod();
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
                case 8 -> {
                    System.out.println("Order cancelled.\n");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void printOrderSummary(Order order, double basketCost, String cutlery, String paymentMethod,
                                   String review, String extraInfo, String deliveryInfo) {
        System.out.println("\nYour order summary:");


        System.out.println("Items ordered:");
        order.displayOrder();


        System.out.printf("Total Basket Cost: £%.2f\n", basketCost);


        System.out.println("Delivery Method: " + (deliveryInfo.isEmpty() ? "Standard" : deliveryInfo));


        System.out.println("Cutlery: " + cutlery);


        System.out.println("Review: " + (review.isEmpty() ? "No review" : review));


        System.out.println("Extra Information: " + (extraInfo.isEmpty() ? "No extra information" : extraInfo));


        System.out.println("Delivery Information: " + (deliveryInfo.isEmpty() ? "No additional delivery information" : deliveryInfo));

        System.out.println("\nOrder Summary Complete.");
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

    public String deliveryAddress() {
        System.out.println("Would you like to enter a new address for delivery that is not on the system? (y/n)");
        String input = scanner.nextLine().toLowerCase();
        if (input.equals("y")) {
            System.out.println("Enter the address you would like to deliver to:");
            deliveryaddress = scanner.nextLine();
            return deliveryaddress;
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






