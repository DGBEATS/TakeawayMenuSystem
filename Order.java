import java.util.Map;
// handles the customer's order and their menu choices
public class Order
{
        private int orderId;
        private int customerId;
        private Map<MenuItem, Integer> items; // MenuItem (Key) -> Quantity (Value)
        private String status; // "Preparing", "Completed", "Cancelled"
}
