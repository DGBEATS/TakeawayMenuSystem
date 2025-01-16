package admin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class AdminMenu {
    private static final String FILE_NAME = "src/menu.txt";
    private static final String FILE_NAME2 = "src/credentials.txt";
    private static final String FILE_NAME3 = "src/discounts.txt";
    private static final String FILE_NAME4 = "src/feedback.txt";
    private static final String FILE_NAME5 = "src/rating.txt";
    private static String choice = "";

    public static void main(String[] args) {

        initialDisplay();
        choice = choice();
        useChoice();
    }

    public static void initialDisplay() {
        System.out.println("Welcome to the Admin portal for the Tasty House Menu!");
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
    
    public static void displayDiscounts() {
    	try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME3))){
    		String line;
    		while ((line = reader.readLine()) != null) {
    			String[] parts = line.split(",");
                if (parts.length == 2) {
                    String discountCode = parts[0].trim();
                    String discountAmountPercentage = parts[1].trim();
                    System.out.println(discountCode + " - " + discountAmountPercentage + "%");
                }
    		}
    	}catch (IOException e) {
            System.out.println("Error reading the discount file: " + e.getMessage());
        }
    }
    
    public static void displayFeedback() {
    	try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME4))){
    		String line;
    		while ((line = reader.readLine()) != null) {
    			String[] parts = line.split(",");
                if (parts.length == 2) {
                    String cusUsername = parts[0].trim();
                    String feedback = parts[1].trim();
                    System.out.println(cusUsername + " - " + feedback);
                }
    		}
    	}catch (IOException e) {
            System.out.println("Error reading the feedback file: " + e.getMessage());
        }
    }
    
    public static void displayRatings() {
    	try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME5))){
    		String line;
    		while ((line = reader.readLine()) != null) {
    			String[] parts = line.split(",");
                if (parts.length == 2) {
                    String cusUsername = parts[0].trim();
                    String rating = parts[1].trim();
                    System.out.println(cusUsername + " - " + rating + " out of 5.");
                }
    		}
    	}catch (IOException e) {
            System.out.println("Error reading the rating file: " + e.getMessage());
        }
    }
    
    public static void displayAdminAccounts() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME2))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3 && parts[2].trim().equalsIgnoreCase("admin")) {
                    String admUsername = parts[0].trim();
                    System.out.println(admUsername);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the credentials file: " + e.getMessage());
        }
    }
    
    public static void displayCustomerAccounts() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME2))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3 && parts[2].trim().equalsIgnoreCase("customer")) {
                    String cusUsername = parts[0].trim();
                    System.out.println(cusUsername);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the credentials file: " + e.getMessage());
        }
    }


    public static String choice(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n What would you like to:\n\n Menu options \n a) add a item to the menu \n "
        		+ "b) delete an item \n c) update the price of an item from the menu \n d) see menu \n Discount codes \n "
        		+ "e) add new discount codes \n f) delete discount codes \n g) see all available discount codes \n"
        		+ "Account Management \n h) add an admin account \n i) remove an admin account \n j) remove customer accounts \n "
        		+ "Reviews \n k) see ratings \n l) see feedback \n Sign out \n m) sign out \n\n Your answer: ");
        String choice = scanner.nextLine();
        return choice;
    }
    
    public static void useChoice() {
    	switch (choice) {
        case "a":
        	addItemToMenu();
        	choice = choice();
        	useChoice();
        	break;
        case "b":
        	removeItemFromMenu();
        	choice = choice();
        	useChoice();
        	break;
        case "c":
        	updatePriceOfItem();
        	choice= choice();
        	useChoice();
        	break;
        case "d":
        	System.out.println("\n Current menu: \n");
        	displayMenu();
        	choice = choice();
        	useChoice();
        	break;
        case "e":
        	addDiscountCode();
        	choice = choice();
        	useChoice();
        	break;
        case "f":
        	removeDiscountCode();
        	choice = choice();
        	useChoice();
        	break;
        case "g":
        	displayDiscounts();
        	choice = choice();
        	useChoice();
        	break;
        case "h":
        	addAdminAccountMenu();
        	choice = choice();
        	useChoice();
        	break;
        case "i":
        	deleteAdminAccount();
        	choice = choice();
        	useChoice();
        	break;
        case "j":
        	deleteCustomerAccount();
        	choice = choice();
        	useChoice();
        	break;
        case "k":
        	System.out.println("\n Current feedbacks: \n");
        	displayFeedback();
        	choice = choice();
        	useChoice();
        	break;
        case "l":
        	System.out.println("\n Current ratings: \n");
        	displayRatings();
        	choice = choice();
        	useChoice();
        	break;
        case "m":
        	signOut();
        	break;
        default:
        	System.out.println("Please enter a correct option.");
        	choice = choice();
        	useChoice();
        	break;
        }
    }
    
    public static void signOut() {
        System.out.println("Signing out...");
        Login login = new Login();
        login.start();
        
    }
    
    public static void addItemToMenu() {
    	System.out.println("\n Current menu: \n");
    	displayMenu();
    	
    	System.out.println("\n Would you still like to add an item? \n a) yes \n b) no");
    	Scanner scanner = new Scanner(System.in);
    	String answer = scanner.nextLine();
    	
    	if (answer.equalsIgnoreCase("a")){
    		boolean temp = false;
    		while (temp == false) {
    			System.out.println("\n What is the item name: ");
    	    	String itemName = scanner.nextLine();
    	    	System.out.println("What is the item type ('food' or 'drink' answer only): ");
    	    	String itemType = scanner.nextLine();
    	    	while ((!itemType.equals("food")) && (!itemType.equals("drink"))){
    	    		System.out.println("Please enter a valid type for the item.");
        	    	System.out.println("What is the item type ('food' or 'drink' answer only): ");
    	    		itemType = scanner.nextLine();
    	    	}
    	    	double itemPrice = 0.0;
    	    	boolean temp2 = false;
    	    	while (temp2 == false) {
    	    		System.out.println("What is the item price?: ");
        	    	String itemPriceStr = scanner.nextLine();
    	    		try {
    	    			itemPrice = Double.parseDouble(itemPriceStr);
    	    			temp2 = true;
    	    		} catch (NumberFormatException e) {
    	    			System.out.println("Please enter a number.");
    	    		}
    	    	}
    	    	System.out.println("Item Added successfully.");
    	    	temp = true;
    	    	
    	    	try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))){
    	    		writer.write(itemName + ", " + itemType + "," + itemPrice);
    	    		writer.newLine();
    	    	}catch (IOException e) {
    	    		System.out.println("Error writing code to the menu file: " + e.getMessage());
    	    	}
    	    	
    		}
    	} else if (answer.equalsIgnoreCase("b")) {
    		choice = choice();
    		useChoice();
    	}else {
    		System.out.println(" Please type a or b.");
    		addItemToMenu();
    	}
    	
    }
    
    public static void removeItemFromMenu(){
    	System.out.println("\n Current menu: \n");
    	displayMenu();
    	
    	System.out.println("\n Would you still like to remove an item? \n a) yes \n b) no");
    	Scanner scanner = new Scanner(System.in);
    	String answer = scanner.nextLine();
    	
    	if (answer.equalsIgnoreCase("a")){
    		boolean temp = false;
    		while (temp == false) {
    			System.out.println("Please enter full name of item to delete: ");
    			String answer2 = scanner.nextLine();
    			boolean temp2 = checkItemExists(answer2);
    			if (temp2 == true) {
    				
    				File inputFile = new File(FILE_NAME);
                    File tempFile = new File("tempFile.txt");

                    try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                         BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

                        String line;
                        while ((line = reader.readLine()) != null) {
                            String[] menu = line.split(",");
                            if (menu[0].trim().equalsIgnoreCase(answer2.trim())) {
                                continue;
                            }
                            writer.write(line);
                            writer.newLine();
                        }

                    } catch (IOException e) {
                        System.out.println("Error: " + e.getMessage());
                    }

                    if (inputFile.delete()) {
                        if (tempFile.renameTo(inputFile)) {
                            System.out.println("Menu updated.");
                        } else {
                            System.out.println("Error renaming the temp file.");
                        }
                    } else {
                        System.out.println("Error deleting the original file.");
                    }

                    temp = true;
    				
    			}else {
    				System.out.println("Item Not Found");
    			}
    		}
    	} else if (answer.equalsIgnoreCase("b")) {
    		choice = choice();
    		useChoice();
    	}else {
    		System.out.println(" Please type a or b.");
    		removeItemFromMenu();
    	}
    }
    
    public static Boolean checkItemExists(String item) {
    	try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))){
    		String line;
    		while ((line = reader.readLine()) !=null) {
    			String[] menu = line.split(",");
    			if (menu[0].equalsIgnoreCase(item)) {
    				return true;
    			}
    		}
    	}catch (IOException e) {
    		System.out.println("Error reading the file: " + e.getMessage());    	
    	}
    	return false;
    	
    }
    
    public static void updatePriceOfItem() {
    	System.out.println("\n Current menu: \n");
    	displayMenu();
    	
    	System.out.println("\n Would you still like to edit price of an item? \n a) yes \n b) no");
    	Scanner scanner = new Scanner(System.in);
    	String answer = scanner.nextLine();
    	
    	if (answer.equalsIgnoreCase("a")){
    		boolean temp = false;
    		while (temp == false) {
    		
    			System.out.println("Please enter full name of item to edit Price: ");
    			String answer2 = scanner.nextLine();
    			boolean temp2 = checkItemExists(answer2);
    			
    			if (temp2 == true) {
    				
    				String newPriceStr = "";
    				double itemPrice = 0.0;
        	    	boolean temp3 = false;
        	    	while (temp3 == false) {
        	    		System.out.println("Enter the new price for the item:");
                        newPriceStr = scanner.nextLine();
        	    		try {
        	    			itemPrice = Double.parseDouble(newPriceStr);
        	    			temp3 = true;
        	    		} catch (NumberFormatException e) {
        	    			System.out.println("Please enter a valid number.");
        	    		}
        	    	}

                    File inputFile = new File(FILE_NAME);
                    File tempFile = new File("tempFile.txt");
                    
                    try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                         BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
                    	
                    	String line;
                        while ((line = reader.readLine()) != null) {
                            String[] menu = line.split(",");
                            if (menu[0].trim().equalsIgnoreCase(answer2.trim())) {
                                writer.write(menu[0]+ "," + menu[1] + "," + newPriceStr);
                                writer.newLine();
                            }else {
                            	writer.write(line);
                            	writer.newLine();
                            }
                    	}
                    	
                    } catch (IOException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    
                    if (inputFile.delete()) {
                        if (tempFile.renameTo(inputFile)) {
                            System.out.println("Menu updated.");
                        } else {
                            System.out.println("Error renaming the temp file.");
                        }
                    } else {
                        System.out.println("Error deleting the original file.");
                    }
                    
                    temp = true;
                    
    			}else {
    				System.out.println("Item Not Found");
    			}
    		}
    	} else if (answer.equalsIgnoreCase("b")) {
    		choice = choice();
    		useChoice();
    	}else {
    		System.out.println(" Please type a or b.");
    		updatePriceOfItem();
    	}
    }
    
    private static void addDiscountCode() {
    	System.out.println("\n Current discount list: \n");
    	displayDiscounts();
    	
    	System.out.println("\n Would you still like to add a discount? \n a) yes \n b) no");
    	Scanner scanner = new Scanner(System.in);
    	String answer = scanner.nextLine();
    	
    	if (answer.equalsIgnoreCase("a")){
    		boolean temp = false;
    		while (temp == false) {
    			System.out.println("\n What is the discount code: ");
    			String discountCode = scanner.nextLine();
    			
    			int percentage = 0;
    			boolean temp2 = false;
    			while (temp2 == false) {
    				System.out.println("What is the discount percentage? (no % symbol, just number): ");
    				String percentageStr = scanner.nextLine();
    				try {
    	    			percentage = Integer.parseInt(percentageStr);
    	    			temp2 = true;
    	    		} catch (NumberFormatException e) {
    	    			System.out.println("Please enter a number.");
    	    		}
    			}
    			
    			System.out.println("Discount Added successfully.");
    	    	temp = true;
    	    	
    	    	try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME3, true))){
    	    		writer.write(discountCode + ", " + percentage);
    	    		writer.newLine();
    	    	}catch (IOException e) {
    	    		System.out.println("Error writing code to the discount file: " + e.getMessage());
    	    	}
    		}
    	}else if (answer.equalsIgnoreCase("b")) {
    		choice = choice();
    		useChoice();
    	}else {
    		System.out.println(" Please type a or b.");
    		addDiscountCode();
    	}
    }
    
    public static void removeDiscountCode() {
    	
    	System.out.println("\n Current discount list: \n");
    	displayDiscounts();
    	
    	System.out.println("\n Would you still like to remove a discount? \n a) yes \n b) no");
    	Scanner scanner = new Scanner(System.in);
    	String answer = scanner.nextLine();
    	
    	if (answer.equalsIgnoreCase("a")){
    		boolean temp = false;
    		while (temp == false) {
    			System.out.println("Please enter full name of discount code to delete: ");
    			String answer2 = scanner.nextLine();
    			boolean temp2 = checkDiscountExists(answer2);
    			if (temp2 == true) {
    				File inputFile = new File(FILE_NAME3);
                    File tempFile = new File("tempFile.txt");
                    
                    try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                         BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
                    	
                    	String line;
                    	while ((line = reader.readLine()) != null) {
                    		String[] discounts = line.split(",");
                            if (discounts[0].trim().equalsIgnoreCase(answer2.trim())) {
                                continue;
                            }
                            writer.write(line);
                            writer.newLine();
                    	}
                        }catch (IOException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        
                        if (inputFile.delete()) {
                            if (tempFile.renameTo(inputFile)) {
                                System.out.println("Discounts updated.");
                            } else {
                                System.out.println("Error renaming the temp file.");
                            }
                        } else {
                            System.out.println("Error deleting the original file.");
                        }

                        temp = true;
                    }else {
        				System.out.println("Item Not Found");
        			}
    			}		
    	}else if (answer.equalsIgnoreCase("b")) {
    		choice = choice();
    		useChoice();
    	}else {
    		System.out.println(" Please type a or b.");
    		addDiscountCode();
    	}
    }
    
    public static Boolean checkDiscountExists(String discountCode) {
    	try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME3))){
    		String line;
    		while ((line = reader.readLine()) !=null) {
    			String[] discount = line.split(",");
    			if (discount[0].equalsIgnoreCase(discountCode)) {
    				return true;
    			}
    		}
    	}catch (IOException e) {
    		System.out.println("Error reading the file: " + e.getMessage());    	
    	}
    	return false;
    }
    
    public static void addAdminAccountMenu() {
    	System.out.println("\n Current admin list: \n");
    	displayAdminAccounts();
    	
    	System.out.println("\n Would you still like to add an admin account? \n a) yes \n b) no");
    	Scanner scanner = new Scanner(System.in);
    	String answer = scanner.nextLine();
    	
    	if (answer.equalsIgnoreCase("a")){
    		Boolean temp = false;
    		while (temp == false) {
    			System.out.println("\n Would you like to make a customer account admin? \n a) yes \n b) no");
    	    	String answer2 = scanner.nextLine();
    	    	if (answer2.equalsIgnoreCase("a")) {
    	    		makeCustomerAdmin();
    	    		choice = choice();
    	    		useChoice();
    	    	}else if (answer2.equalsIgnoreCase("b")) {
    	    		System.out.println("\n Would you like to create an admin account from scratch? \n a) yes \n b) no");
        	    	String answer3 = scanner.nextLine();
        	    	if (answer3.equalsIgnoreCase("a")) {
        	    		createAdminAccount();
        	    		choice = choice();
        	    		useChoice();
        	    	}else{
        	    		System.out.println("\n No response given. Going back to main menu. \n");
        	    		choice = choice();
        	    		useChoice();
        	    	}
    	    	}else {
    	    		System.out.println(" Please type a or b.\n Going back to start. \n");
    	    		addAdminAccountMenu();
    	    	}
    		}
    	}else if (answer.equalsIgnoreCase("b")) {
    		choice = choice();
    		useChoice();
    	}else {
    		System.out.println(" Please type a or b.");
    		addAdminAccountMenu();
    	}
    }
    
    public static void makeCustomerAdmin() {
    	System.out.println("\n Current customer list: \n");
    	displayCustomerAccounts();
    	Scanner scanner = new Scanner(System.in);
    	Boolean temp = false;
    	
    	while (temp == false) {
    		System.out.println("\n Please enter username of customer you want admin: ");
    		String answer = scanner.nextLine();
    		
    		if (checkCustomerExists(answer) == false) {
    			System.out.println("Please enter a valid customer username!");
    			continue;
    		}else {
    			File inputFile = new File(FILE_NAME2);
                File tempFile = new File("tempFile.txt");
                
                try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                     BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
                    
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(",");
                        
                        if (parts[0].trim().equalsIgnoreCase(answer.trim()) && parts.length >= 3 && parts[2].trim().equalsIgnoreCase("customer")) {
                            writer.write(parts[0] + "," + parts[1] + ",admin");
                            writer.newLine();
                        } else {
                            writer.write(line);
                            writer.newLine();
                        }
                    }
                    System.out.println("Customer successfully updated to admin.");
        	    	temp = true;
                } catch (IOException e) {
                    System.out.println("Error handling the file: " + e.getMessage());
                }
                
                if (inputFile.delete()) {
                    if (tempFile.renameTo(inputFile)) {
                    } else {
                        System.out.println("Error renaming the temp file.");
                    }
                } else {
                    System.out.println("Error deleting the original file.");
                }
                
                temp = true;
    		}
    	}
		
    }

    
    
    public static Boolean checkCustomerExists(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME2))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] credentials = line.split(",");
                if (credentials.length >= 3 && credentials[2].trim().equalsIgnoreCase("customer") && credentials[0].trim().equalsIgnoreCase(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
        return false;
    }
    
    public static Boolean checkAdminExists(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME2))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] credentials = line.split(",");
                if (credentials.length >= 3 && credentials[2].trim().equalsIgnoreCase("admin") && credentials[0].trim().equalsIgnoreCase(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
        return false;
    }

    public static void createAdminAccount() {
    	System.out.println("\n What is the admin username: ");
    	Scanner scanner = new Scanner(System.in);
    	String adminUsername = scanner.nextLine();
    	System.out.println("\n What is the admin password: ");
    	String adminPassword = scanner.nextLine();
    	
    	try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME2, true))) {
            writer.write(adminUsername + "," + adminPassword + ",admin");
            writer.newLine();
            System.out.println("Admin account successfully added.");
        } catch (IOException e) {
            System.out.println("Error writing in the file: " + e.getMessage());
        }
    	
    }
    
    public static void deleteAdminAccount() {
    	
    	System.out.println("\n Admin list: \n");
    	displayAdminAccounts();
    	
    	System.out.println("\n Would you still like to remove an admin? \n a) yes \n b) no");
    	Scanner scanner = new Scanner(System.in);
    	String answer = scanner.nextLine();
    	
    	if (answer.equalsIgnoreCase("a")){
    		boolean temp = false;
    		while (temp == false) {
    			System.out.println("Please enter username of admin to delete: ");
    			String answer2 = scanner.nextLine();
    			boolean temp2 = checkAdminExists(answer2);
    			if (temp2 == true) {
    				
    				File inputFile = new File(FILE_NAME2);
                    File tempFile = new File("tempFile.txt");

                    try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                         BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

                        String line;
                        while ((line = reader.readLine()) != null) {
                            String[] admin = line.split(",");
                            if (admin[0].trim().equalsIgnoreCase(answer2.trim())) {
                                continue;
                            }
                            writer.write(line);
                            writer.newLine();
                        }

                    } catch (IOException e) {
                        System.out.println("Error: " + e.getMessage());
                    }

                    if (inputFile.delete()) {
                        if (tempFile.renameTo(inputFile)) {
                            System.out.println("Admins updated.");
                        } else {
                            System.out.println("Error renaming the temp file.");
                        }
                    } else {
                        System.out.println("Error deleting the original file.");
                    }

                    temp = true;
    				
    			}else {
    				System.out.println("Admin Not Found");
    			}
    		}
    	} else if (answer.equalsIgnoreCase("b")) {
    		choice = choice();
    		useChoice();
    	}else {
    		System.out.println(" Please type a or b.");
    		deleteAdminAccount();
    	}
    	
    }
    
public static void deleteCustomerAccount() {
    	
    	System.out.println("\n Customer list: \n");
    	displayCustomerAccounts();
    	
    	System.out.println("\n Would you still like to remove a customer? \n a) yes \n b) no");
    	Scanner scanner = new Scanner(System.in);
    	String answer = scanner.nextLine();
    	
    	if (answer.equalsIgnoreCase("a")){
    		boolean temp = false;
    		while (temp == false) {
    			System.out.println("Please enter username of customer to delete: ");
    			String answer2 = scanner.nextLine();
    			boolean temp2 = checkCustomerExists(answer2);
    			if (temp2 == true) {
    				
    				File inputFile = new File(FILE_NAME2);
                    File tempFile = new File("tempFile.txt");

                    try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                         BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

                        String line;
                        while ((line = reader.readLine()) != null) {
                            String[] customer = line.split(",");
                            if (customer[0].trim().equalsIgnoreCase(answer2.trim())) {
                                continue;
                            }
                            writer.write(line);
                            writer.newLine();
                        }

                    } catch (IOException e) {
                        System.out.println("Error: " + e.getMessage());
                    }

                    if (inputFile.delete()) {
                        if (tempFile.renameTo(inputFile)) {
                            System.out.println("Customers updated.");
                        } else {
                            System.out.println("Error renaming the temp file.");
                        }
                    } else {
                        System.out.println("Error deleting the original file.");
                    }

                    temp = true;
    				
    			}else {
    				System.out.println("Customer Not Found");
    			}
    		}
    	} else if (answer.equalsIgnoreCase("b")) {
    		choice = choice();
    		useChoice();
    	}else {
    		System.out.println(" Please type a or b.");
    		deleteCustomerAccount();
    	}
    	
    }
    

    
}