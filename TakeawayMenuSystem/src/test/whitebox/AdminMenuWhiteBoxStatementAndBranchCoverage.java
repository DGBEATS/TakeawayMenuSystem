package test.whitebox;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;

import takeawaySystem.AdminMenu;

public class AdminMenuWhiteBoxStatementAndBranchCoverage {

	private final String filePath = "src/menu.txt";
	private final String filePath2 = "src/discounts.txt";
	private final String filePath3 = "src/credentials.txt";
	private final PrintStream originalOut = System.out;
	private final InputStream originalIn = System.in;

	void cleanLastLineMenu() throws IOException {
		System.setOut(originalOut);
		System.setIn(originalIn);

		Path filePath = Path.of(this.filePath);
		List<String> lines = Files.readAllLines(filePath);

		if (!lines.isEmpty()) {
			lines.remove(lines.size() - 1);
		}

		Files.write(filePath, lines);
	}

	void cleanLastLineDiscount() throws IOException {
		System.setOut(originalOut);
		System.setIn(originalIn);

		Path filePath = Path.of(this.filePath2);
		List<String> lines = Files.readAllLines(filePath);

		if (!lines.isEmpty()) {
			lines.remove(lines.size() - 1);
		}

		Files.write(filePath, lines);
	}

	void cleanLastLineCredentials() throws IOException {
		System.setOut(originalOut);
		System.setIn(originalIn);

		Path filePath = Path.of(this.filePath3);
		List<String> lines = Files.readAllLines(filePath);

		if (!lines.isEmpty()) {
			lines.remove(lines.size() - 1);
		}

		Files.write(filePath, lines);
	}

	@Test
	void testAdminAddItemToMenu() throws IOException {
		String adminInput = "a\nSushi\nfood\n6.49\n";
		System.setIn(new ByteArrayInputStream(adminInput.getBytes()));

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStream));

		AdminMenu.addItemToMenu();

		String lastLine = null;
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				lastLine = line;
			}
		}

		assertEquals("Sushi, food,6.49", lastLine, "Lines must match.");

		String consoleOutput = outputStream.toString();
		assertTrue(consoleOutput.contains("Item Added successfully."));
		cleanLastLineMenu();
	}

	@Test
	void testAdminDeleteItemToMenu() throws IOException {
		String adminInput = "a\nSushi\nfood\n6.49\n";
		System.setIn(new ByteArrayInputStream(adminInput.getBytes()));

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStream));

		AdminMenu.addItemToMenu();

		String lastLine = null;
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				lastLine = line;
			}
		}

		assertEquals("Sushi, food,6.49", lastLine, "Lines must match.");

		String consoleOutput = outputStream.toString();
		assertTrue(consoleOutput.contains("Item Added successfully."));

		String adminInput2 = "a\nSushi";
		System.setIn(new ByteArrayInputStream(adminInput2.getBytes()));

		ByteArrayOutputStream outputStream2 = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStream2));

		AdminMenu.removeItemFromMenu();

		String lastLine2 = null;
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				lastLine2 = line;
			}
		}

		assertNotEquals("Sushi, food,6.49", lastLine2, "Lines must not match.");
	}

	@Test
	void testAdminUpdatePrice() throws IOException {
		String adminInput = "a\nSushi\nfood\n6.49\n";
		System.setIn(new ByteArrayInputStream(adminInput.getBytes()));

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStream));

		AdminMenu.addItemToMenu();

		String lastLine = null;
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				lastLine = line;
			}
		}

		assertEquals("Sushi, food,6.49", lastLine, "Lines must match.");

		String consoleOutput = outputStream.toString();
		assertTrue(consoleOutput.contains("Item Added successfully."));

		String adminInput2 = "a\nSushi\n8.49\n";
		System.setIn(new ByteArrayInputStream(adminInput2.getBytes()));

		ByteArrayOutputStream outputStream2 = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStream2));

		AdminMenu.updatePriceOfItem();

		String lastLine2 = null;
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				lastLine2 = line;
			}
		}

		assertEquals("Sushi, food,8.49", lastLine2, "Lines must match.");

		cleanLastLineMenu();
	}

	@Test
	void testAdminAddDiscount() throws IOException {
		String adminInput = "a\nDiscCode01\n50";
		System.setIn(new ByteArrayInputStream(adminInput.getBytes()));

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStream));

		AdminMenu.addDiscountCode();

		String lastLine = null;
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath2))) {
			String line;
			while ((line = reader.readLine()) != null) {
				lastLine = line;
			}
		}

		assertEquals("DiscCode01, 50", lastLine, "Lines must match.");
		cleanLastLineDiscount();
	}

	@Test
	void testAdminRemoveDiscount() throws IOException {
		String adminInput = "a\nDiscCode01\n50";
		System.setIn(new ByteArrayInputStream(adminInput.getBytes()));

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStream));

		AdminMenu.addDiscountCode();

		String lastLine = null;
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath2))) {
			String line;
			while ((line = reader.readLine()) != null) {
				lastLine = line;
			}
		}

		assertEquals("DiscCode01, 50", lastLine, "Lines must match.");

		String adminInput2 = "a\nDiscCode01";
		System.setIn(new ByteArrayInputStream(adminInput2.getBytes()));

		ByteArrayOutputStream outputStream2 = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStream2));

		AdminMenu.removeDiscountCode();

		String lastLine2 = null;
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath2))) {
			String line;
			while ((line = reader.readLine()) != null) {
				lastLine2 = line;
			}
		}

		assertNotEquals("DiscCode01, 50", lastLine2, "Lines must match.");
	}

	@Test
	void testMakeCustomerAdmin() throws IOException {
		appendTemporaryCustomer();

		String adminInput = "adminTest";
		System.setIn(new ByteArrayInputStream(adminInput.getBytes()));

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStream));

		AdminMenu.makeCustomerAdmin();

		String lastLine = null;
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath3))) {
			String line;
			while ((line = reader.readLine()) != null) {
				lastLine = line;
			}
		}

		assertEquals("adminTest,adminPassTest,admin", lastLine, "Lines must match.");
		cleanLastLineCredentials();
	}

	void appendTemporaryCustomer() throws IOException {
		Path path = Path.of(filePath3);
		Files.writeString(path, "adminTest,adminPassTest,customer\n", java.nio.file.StandardOpenOption.APPEND);
	}

	@Test
	void testAdminCreateAdminAccount() throws IOException {
		String adminInput = "adminTest\nadminPassTest";
		System.setIn(new ByteArrayInputStream(adminInput.getBytes()));

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStream));

		AdminMenu.createAdminAccount();

		String lastLine = null;
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath3))) {
			String line;
			while ((line = reader.readLine()) != null) {
				lastLine = line;
			}
		}

		assertEquals("adminTest,adminPassTest,admin", lastLine, "Lines must match.");
		cleanLastLineCredentials();
	}

	@Test
	void testAdminCoverageAddAdmin() throws IOException {
	    String adminInput = "a\nb\nb\n";
	    System.setIn(new ByteArrayInputStream(adminInput.getBytes()));

	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(outputStream));
	    try {
	        AdminMenu.addAdminAccountMenu();
	    } catch (RuntimeException e) {
	        assertEquals("No line found", e.getMessage(), "Expected to stop after reaching the target line.");
	    }

	    String consoleOutput = outputStream.toString();

	    assertTrue(consoleOutput.contains("\n No response given. Going back to main menu. \n"));
	}


	@Test
	void testAdminRemoveAdminAccount() throws IOException {
		appendTemporaryAdmin();
		String adminInput = "a\nadminTest";
		System.setIn(new ByteArrayInputStream(adminInput.getBytes()));

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStream));

		AdminMenu.deleteAdminAccount();

		String lastLine = null;
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath3))) {
			String line;
			while ((line = reader.readLine()) != null) {
				lastLine = line;
			}
		}

		assertNotEquals("adminTest,adminPassTest,admin", lastLine, "Lines must match.");
	}

	void appendTemporaryAdmin() throws IOException {
		Path path = Path.of(filePath3);
		Files.writeString(path, "adminTest,adminPassTest,admin\n", java.nio.file.StandardOpenOption.APPEND);
	}

	@Test
	void testAdminDeleteCustomerAccount() throws IOException {
		appendTemporaryCustomer();
		String adminInput = "a\nadminTest";
		System.setIn(new ByteArrayInputStream(adminInput.getBytes()));

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStream));

		AdminMenu.deleteCustomerAccount();

		String lastLine = null;
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath3))) {
			String line;
			while ((line = reader.readLine()) != null) {
				lastLine = line;
			}
		}

		assertNotEquals("adminTest,adminPassTest,customer", lastLine, "Lines must match.");
	}



}