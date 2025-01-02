public class Payment //Handles payments details, payment method and payment processing
{
    private int paymentId;
    private int orderId; // Links the payment to an order
    private String paymentMethod; // "Online", "In-Person"
    // For online payments
    private String cardNumber;
    private String expiryDate; // Card expiry date
    private String cvv; // Card CVV
    private boolean isPaid; // Indicates if the payment is completed
}
