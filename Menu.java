import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Menu {

	private static final String FILE_NAME = "src/menu.txt";
	private static final String FILE_NAME2 = "src/credentials.txt";

	private static String cutlery;
	private static String paymentMethod;
	private static String review;
	private static String extrainfo;
	private static String cardnumber;
	private static String feedback;

	public static void main(String[] args) {

		initialDisplay();
		displayMenu();


		cutlery = getCutlery();
		paymentMethod = getPaymentMethod();
		review = getReview();
		extrainfo = extraInfo();
		feedback = feedback();


		System.out.println("Cutlery: " + cutlery);
		System.out.println("Payment Method: " + paymentMethod);
		System.out.println("Review: " + review);
		System.out.println("Extrainfo: " + extrainfo);
		System.out.println("Your Feedback: " + feedback);

		// need to write code that retrieves username + card info from other class
	}


	public static void initialDisplay() {
		System.out.println("Welcome to the Tasty House Menu! Here is the current food and drink items available:\n ");
	}


	public static void displayMenu() {
		try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
			String line;
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

	public static String extraInfo(){
		Scanner scanner = new Scanner(System.in);
		System.out.println("Do you want to add any extra information to your order regarding any items? (y/n)");
		String input = scanner.nextLine().toLowerCase();
		if (input.equals("y")) {
			System.out.println("Enter the extra information you would like to add: ");
			return scanner.nextLine().toLowerCase();
		} else {
			return "N/A";
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

	public static String feedback(){
		Scanner scanner = new Scanner(System.in);
		System.out.println("Do you want to leave feedback for the chef? (y/n)");
		String input = scanner.nextLine().toLowerCase();
		if (input.equals("y")) {
			System.out.println("Leave feedback below:");
			return scanner.nextLine().toLowerCase();
		} else {
			System.out.println("No problem. Have a nice day!");
			return "";
		}
	}

	public static void choice(){
		Scanner scanner = new Scanner(System.in);
		System.out.println("Would you like a. delivery or b. collection?");
		String input = scanner.nextLine().toLowerCase();
		if (input.equals("a")|| input.equals("delivery")) {
			//

			System.out.println("Would you like to receive priority delivery? This is an extra charge of £2 and food will arrive within 15 mins. (y/n)");
			input = scanner.nextLine().toLowerCase();
			if (input.equals("y")) {
				System.out.println("You have selected priority delivery.");
				//
			} else {
				System.out.println("You have selected standard delivery.");
				//
			}

		} else if (input.equals("b")|| input.equals("collection")) {
			//
		}
	}
	
}

