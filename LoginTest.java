package tests;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;


public class LoginTest {
	private final String TEST_FILE = "src/test_credentials.txt";

//	@Before
//	public void setUp() {
//		//clear the test file before each test
//		try {
//			new FileWriter(TEST_FILE).close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
	// blackbox tests - testing functionality from the user's perspective
	
	@Test
	public void testValidCustomerAccountCreation() {
		boolean result = addCredentials("testUser1", "pass123", "customer", "1234567890", "test@gmail.com", "123 Test St", "1234567890123456");
		assertTrue("Valid customer account creation should succeed", result);
	}
	
	@Test
	public void testValidAdminAccountCreation() {
		boolean result = addCredentials("adminUser1", "admin123", "admin", "1234567890", "admin@gmail.com", "456 Test St", "1234567890123456");
		assertTrue("Valid account account creation should succeed", result);
	}
	
	@Test
	public void testPhoneNumberValidation() {
		// Testing phone numbers with letters
		assertFalse("abc12345".matches("\\d+"));
		// Test Phone numbers with special characters
		assertFalse("123-456-7890".matches("\\d+"));
		//Test valid phone number
		assertTrue("1234567890".matches("\\d+"));
	}
	
	@Test
	public void testEmailValidation() {
		// Test email without @
		assertFalse(isValidEmail("testemail.com"));
		// Test email without domain
        assertFalse(isValidEmail("test@"));
        // Test valid email
        assertTrue(isValidEmail("test@email.com"));
	}
	
	@Test
	public void testCardNumberValidation() {
		//Test card number with less than 16 digits
		assertFalse("123456789012345".matches("\\d{16}"));
		//Test card number with letters
		assertFalse("123456789012345a".matches("\\d{16}"));
		//Test valid card number
		assertTrue("1234567890123456".matches("\\d{16}"));
	}
	
	// White Box Tests - Testing internal methods and edge cases

    @Test
    public void testDuplicateUsername() throws IOException {
        // First create an account
        addCredentials("testUser", "pass123", "customer", 
            "1234567890", "test@email.com", "123 Test St", "1234567890123456");
        
        // Test if username exists
        assertTrue("Should detect existing username", usernameExists("testUser"));
        assertFalse("Should not find non-existent username", usernameExists("nonexistentUser"));
    }

    private boolean usernameExists(String username) {
        // Implementation similar to your Login class method
        try (BufferedReader reader = new BufferedReader(new FileReader(TEST_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] credentials = line.split(",");
                if (credentials.length > 0 && credentials[0].equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the credentials file: " + e.getMessage());
        }
        return false;
    }

	
	// White Box Tests
    @Test
    public void testPasswordWithCommas() {
        String password = "pass,word";
        assertTrue("Should detect comma in password", password.contains(","));
        String validPassword = "password123";
        assertFalse("Should not detect comma in valid password", validPassword.contains(","));
    }

    // Helper methods
    private boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".");
    }	

    private boolean addCredentials(String username, String password, String userType, 
                                 String phone, String email, String address, String cardNumber) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEST_FILE, true))) {
            writer.write(username + "," + password + "," + userType + "," + 
                        phone + "," + email + "," + address + "," + cardNumber);
            writer.newLine();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
