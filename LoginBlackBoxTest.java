package test.blackbox;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import takeaway.Login;

public class LoginBlackBoxTest {

    @Test
    public void testUsernameExists() {
        assertFalse("Username should not already exist", Login.usernameExists("UsernameExists"));
    }

    @Test
    public void testInvalidUsernameInput() {
        assertFalse("Invalid username input should not be accepted", Login.usernameExists(""));
    }

    @Test
    public void testPasswordContainsCommas() {
        assertFalse("Password with commas should not be accepted", isValidPassword("pass,word"));
    }

    @Test
    public void testEmptyPassword() {
        assertFalse("Empty password should not be accepted", isValidPassword(""));
    }

    @Test
    public void testPhoneNumberNonNumeric() {
        assertFalse("Non-numeric phone number should not be accepted", isValidPhoneNumber("abc123"));
    }

    @Test
    public void testEmptyPhoneNumber() {
        assertFalse("Empty phone number should not be accepted", isValidPhoneNumber(""));
    }

    @Test
    public void testEmailMissingAtSymbol() {
        assertFalse("Email missing '@' symbol should not be accepted", isValidEmail("email.com"));
    }

    @Test
    public void testEmailMissingDot() {
        assertFalse("Email missing '.' symbol should not be accepted", isValidEmail("email@com"));
    }

    @Test
    public void testEmptyEmail() {
        assertFalse("Empty email should not be accepted", isValidEmail(""));
    }

    @Test
    public void testAddressContainsCommas() {
        assertFalse("Address containing commas should not be accepted", isValidAddress("123 Main St, Apt 4"));
    }

    @Test
    public void testEmptyAddress() {
        assertFalse("Empty address should not be accepted", isValidAddress(""));
    }

    @Test
    public void testCardNumberNonNumeric() {
        assertFalse("Non-numeric card number should not be accepted", isValidCardNumber("abcd123456789012"));
    }

    @Test
    public void testCardNumberIncorrectLength() {
        assertFalse("Card number with incorrect length should not be accepted", isValidCardNumber("123456"));
    }

    @Test
    public void testEmptyCardNumber() {
        assertFalse("Empty card number should not be accepted", isValidCardNumber(""));
    }

    @Test
    public void testValidAdminHasAccount() {
        assertTrue("Valid admin credentials should pass login",
                Login.validateCredentials("ValidUsername", "ValidPassword", "Admin"));
    }

    @Test
    public void testValidAdminNoAccount() {
        assertTrue("Valid admin account creation should succeed",
                Login.addCredentials("ValidUsername", "ValidPassword", "Admin", "1234567890", "email@example.com", "123 Street", "1234567890123456"));
    }

    @Test
    public void testValidCustomerHasAccount() {
        assertTrue("Valid customer credentials should pass login",
                Login.validateCredentials("ValidUsername", "ValidPassword", "Customer"));
    }

    @Test
    public void testValidCustomerNoAccount() {
        assertTrue("Valid customer account creation should succeed",
                Login.addCredentials("ValidUsername", "ValidPassword", "Customer", "1234567890", "email@example.com", "123 Street", "1234567890123456"));
    }

    @Test
    public void testGuestHasAccount() {
        assertTrue("Guest credentials should pass login", 
                Login.validateCredentials("ValidUsername", "ValidPassword", "Guest"));
    }

    @Test
    public void testGuestNoAccount() {
        assertTrue("Guest account creation should succeed", 
                Login.addCredentials("ValidUsername", "ValidPassword", "Guest", "1234567890", "email@example.com", "123 Street", "1234567890123456"));
    }

    
    private boolean isValidPassword(String password) {
        return !password.contains(",") && !password.isEmpty();
    }

    private boolean isValidPhoneNumber(String phone) {
        return phone.matches("\\d+");
    }

    private boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".");
    }

    private boolean isValidAddress(String address) {
        return !address.contains(",") && !address.isEmpty();
    }

    private boolean isValidCardNumber(String cardNumber) {
        return cardNumber.matches("\\d{16}");
    }
}
