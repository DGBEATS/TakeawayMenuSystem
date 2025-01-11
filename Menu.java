import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Menu {

	private static final String FILE_NAME = "src/menu.txt";
	private static String cutlery;
	private static String paymentMethod;
	private static String review;

	public static void main(String[] args) {
		
		initialDisplay();
		displayMenu();

		
		cutlery = getCutlery();
		paymentMethod = getPaymentMethod();
		review = getReview();

		// Print the details after the user interaction
		System.out.println("Cutlery: " + cutlery);
		System.out.println("Payment Method: " + paymentMethod);
		System.out.println("Review: " + review);
	}

	
	public static void initialDisplay() {
		System.out.println("Welcome to the Tasty House Menu! Here is the current food and drink items available:\n ");
	}

	
	public static void displayMenu() {
		try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
			String line;
			while ((line = reader.readLine()) != null) {
				// Split the line into parts: item name, category, and price
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

	
	public static String getCutlery() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Do you want to add cutlery to the order? (y/n)");
		String input = scanner.nextLine().toLowerCase();
		if (input.equals("y")) {
			return "Cutlery provided";
		} else {
			return "No cutlery provided";
		}
	}

	
	public static String getPaymentMethod() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Do you want to pay a: online or b: in person?");
		String input = scanner.nextLine().toLowerCase();
		if (input.equals("online") || input.equals("a")) {
			return "Online payment";
		} else if (input.equals("b") || input.equals("in person")) {
			return "In person payment";
		} else {
			return "Invalid payment method";
		}
	}

	
	public static String getReview() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Do you want to leave a review? (y/n)");
		String input = scanner.nextLine().toLowerCase();
		if (input.equals("y")) {
			System.out.println("Leave a review below:");
			return scanner.nextLine().toLowerCase();
		} else {
			System.out.println("No problem, have a nice day and we hope you enjoyed your meal.");
			return "No review";
		}
	}
}

