package test.whitebox;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PasswordValidation {
	// White Box Tests
    @Test
    public void testPasswordWithCommas() {
        String password = "pass,word";
        assertTrue("Should detect comma in password", password.contains(","));
        String validPassword = "password123";
        assertFalse("Should not detect comma in valid password", validPassword.contains(","));
    }
}
