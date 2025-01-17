package test.whitebox;

import static org.junit.jupiter.api.Assertions.*;
import java.io.*;
import org.junit.jupiter.api.Test;
import takeawaySystem.*; // Import all classes from the Menu package


class WhiteBoxMenuSystemTest {

    /**
     * Test exception handling during menu display in MenuSystem.
     * White-box technique: Statement coverage.
     * Expected: Proper error message when the menu file is unreadable.
     */
    @Test
    void testDisplayMenu_ExceptionHandling() {
        // Redirect system output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        MenuSystem menuSystem = new MenuSystem();
        menuSystem.displayMenu();

        // Verify error message
        assertTrue(outputStream.toString().contains("Error reading the menu file"), "Expected error message for missing or invalid file");
    }

    /**
     * Test adding and removing items in an Order.
     * White-box technique: Statement coverage.
     * Expected: Items are correctly added and removed.
     */
    @Test
    void testOrder_AddAndRemoveItem() {
        Order order = new Order();
        order.addItem("Burger", 5.99, 1);
        order.addItem("Pizza", 7.49, 2);

        // Verify items added
        assertFalse(order.isEmpty(), "Order should not be empty after adding items");
        assertEquals(2, order.items.size(), "Order should contain 2 items");

        // Remove item and verify
        assertTrue(order.removeItem("Pizza"), "Pizza should be removed from the order");
        assertEquals(1, order.items.size(), "Order should contain 1 item after removal");
    }

    /**
     * Test displaying order details.
     * White-box technique: Statement coverage.
     * Expected: Order details are correctly displayed.
     */
    @Test
    void testOrder_DisplayOrder() {
        Order order = new Order();
        order.addItem("Burger", 5.99, 1);
        order.addItem("Pizza", 7.49, 2);

        // Redirect system output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        order.displayOrder();
        String output = outputStream.toString();

        // Verify displayed output
        assertTrue(output.contains("Burger"), "Output should contain Burger");
        assertTrue(output.contains("Pizza"), "Output should contain Pizza");
        assertTrue(output.contains("Total Cost"), "Output should display total cost correctly");
    }

    /**
     * Test processing an order in OrderManager.
     * White-box technique: Branch coverage.
     * Expected: Orders are processed correctly based on user input.
     */
    @Test
    void testOrderManager_ProcessOrder() {
        // Simulate user input:
        // - "1" to start a new order
        // - "done" to finish adding items
        // - "3" to exit
        String input = """
            1
            done
            3
        """;
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        // Redirect system output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Create an instance of OrderManager and call processOrder
        OrderManager orderManager = new OrderManager();
        orderManager.processOrder();

        // Verify the output
        String output = outputStream.toString();
        assertTrue(output.contains("Main Menu:"), "Expected 'Main Menu' to be displayed");
        assertTrue(output.contains("Thank you for using the system. Goodbye!"), "Expected system exit message");
    }


    /**
     * Test removing non-existent items in Order.
     * White-box technique: Branch coverage.
     * Expected: Removal of a non-existent item fails gracefully.
     */
    @Test
    void testOrder_RemoveNonExistentItem() {
        Order order = new Order();
        order.addItem("Burger", 5.99, 1);

        // Attempt to remove an item not in the order
        assertFalse(order.removeItem("Pizza"), "Removing a non-existent item should fail");
    }
}