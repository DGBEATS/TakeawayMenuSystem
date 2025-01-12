package Menu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
	//Implementing Main class
public static void main(String[] args) {
	MenuSystem system = new MenuSystem();
	system.initialDisplay();
	
	}

}


//Class definition for the MenuSystem
class MenuSystem {
    
	private MenuManager menu;
	private Scanner scanner;
	
	public MenuSystem() {
		this.menu=new MenuManager();
		this.scanner = new Scanner(System.in);
	}

	
	// Method to display the initial menu and handle user interactio
	public void initialDisplay() {
		System.out.println("Welcome to the Tasty House Menu! ");
		
		// Infinite loop for continuously displaying the menu
        // Displaying the main menu options
		while(true) {
			System.out.println("\nMain menu");
			System.out.println("1. View Menu");
			System.out.println("2. Place Order");
			System.out.println("3. Show Order History");
			System.out.println("4. Exit");
			System.out.println("Choose an option: ");
			
		    // Reading the user's choice
			int choice = scanner.nextInt();
			scanner.nextLine();
			
			switch(choice) {
			case 1 -> menu.displayMenu();
			case 4 -> {
                System.out.println("Thank you for using our system!");
                return;
            }
            default -> System.out.println("Invalid option. Please try again.");
			}
		}
	}

	class MenuManager { 
		private List<MenuItem> menuItems;
		private static final String MENU_FILE = "menu.txt";
		
		public MenuManager() {
			this.menuItems = loadMenuFromFile();
			if (menuItems.isEmpty()) {
				initializeDefaultMenu();
			}
		}
			
		public void displayMenu() {
			System.out.println("\n=== Menu =====");
			for(MenuItem item : menuItems) {
				System.out.println(item);
			}
			
		
	}
	
		public MenuItem getMenuItem(int id) {
			return menuItems.stream().filter(item -> item.getId() == id).findFirst().orElse(null);
		}
		
		private List<MenuItem> loadMenuFromFile() {
		        List<MenuItem> items = new ArrayList<>();	
		        try (BufferedReader reader = new BufferedReader(new FileReader(MENU_FILE))) {
		        	String line;
		        	int id = 1;
		        	while((line = reader.readLine()) != null) {
		        		String[] parts = line.split(",");
		        		if (parts.length == 3) {
		        			String name = parts[0].trim();
		        			String category = parts[1].trim();
		        			// removes the unicode character for £ so we can convert to a plain numeric string
		        			double price = Double.parseDouble(parts[2].trim());
		        		    items.add(new MenuItem(id++, name, price, category));
		        		}
		        	}
		        		
		        	}catch (IOException e) {
		        		System.out.println("Error loading menu: " + e.getMessage());
		        	}
		             return items;
		        }
		        	
		    private void initializeDefaultMenu() {
		    	menuItems = new ArrayList<>();
		    	menuItems.add(new MenuItem(1, "Margherita Pizza", 9.99, "Pizza"));
		        menuItems.add(new MenuItem(2, "Pepperoni Pizza", 11.99, "Pizza"));
		        menuItems.add(new MenuItem(3, "Garlic Bread", 3.99, "Sides"));
		        menuItems.add(new MenuItem(4, "Cola", 1.99, "Drinks"));
		    
		        	
		  }
		
		


	class MenuItem {
		private int id;
		private String name;
		private double price;
		private String category;
		
		public MenuItem(int id, String name, double price, String category) {
			this.id = id;
			this.name = name;
			this.price = price;
			this.category = category;
			
		}
		public int getId() {
			return id;
		}
		
		public String getName() {
			return name;
		}
		public double getPrice() {
			return price;
		}
		
		@Override
		public String toString() {
			 return String.format("[%d] %s - \u00a3%.2f (%s)", id, name, price, category);
		}
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
