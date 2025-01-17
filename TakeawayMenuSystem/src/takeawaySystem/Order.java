package takeawaySystem;

import java.util.*;

public class Order {
    private static int idCounter = 1;
    private int orderId;
    public Map<String, ItemDetails> items;

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