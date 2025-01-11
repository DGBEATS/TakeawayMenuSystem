import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Menu {

	private static final String FILE_NAME = "src/menu.txt";
	private String cutlery;
	private String paymentMethod;
	private String review;


	public static void main(String[] args) {
		initialDisplay();
		displayMenu();
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

	public String basket(){
		return "";
	}

	public static String cutlery(String cutlery){
		Scanner scanner = new Scanner(System.in);
		System.out.println("Do you want to add cutlery to the order? (y/n)");
		String input = scanner.nextLine().toLowerCase();
		if (input.equals("y")) {
			cutlery= "cutlery provided";
		} else {
			cutlery= "no cutlery provided";
		}
		return cutlery;
	}

	public static String paymentMethod(String paymentMethod){
		Scanner scanner = new Scanner(System.in);
		System.out.println("Do you want to pay a: online or b: in person?");
		String input = scanner.nextLine().toLowerCase();
		if (input.equals("online")|| input.equals("a")) {
			paymentMethod= "online";
		}else if (input.equals("b")||input.equals("in person")) {}
			paymentMethod="in person";

		return paymentMethod;
	}

	public static String leaveReview(String review) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Do you want to leave a review? (y/n)");
		String input = scanner.nextLine().toLowerCase();
		if (input.equals("y")) {
			System.out.println("Leave a review below:");
			review= scanner.nextLine().toLowerCase();
		} else if (input.equals("n")) {
			System.out.println("No problem, have a nice day and we hope you enjoyed your meal.");
			review= "no review";
		}
		return review;
	}

}

