package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import org.junit.Assert;
import org.junit.Test;

import com.techelevator.view.Menu;

public class VendingMachineTest {

	@Test
	public void testReturnCoin() throws FileNotFoundException {
		BigDecimal balance = new BigDecimal(0.90);
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI test = new VendingMachineCLI(menu);
		String expectedValue = "Your change is: " + 3 + " quarter(s), " + 1 + " dime(s), " + 1 + " nickel(s).";
		Assert.assertEquals(expectedValue, test.returnCoin(balance));
	}

	@Test

	public void displayItemsTest() {
		Map<String, VendingItem> inventory = new LinkedHashMap<String, VendingItem>();
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI test = new VendingMachineCLI(menu);
		String expectedResult = "A1" + " " + "Harry Potter" + " $" + "2.50" + " " + "" + "\n";
		Drink harryPotter = new Drink("A1", "Harry Potter", new BigDecimal(2.50).setScale(2), "Drink");
		inventory.put("A1", harryPotter);
		Assert.assertEquals(expectedResult, test.displayItems(inventory));

	}

	@Test

	public void moneyDepositTest() {
		String bill = "$1 Bill";
		String bill2 = "$2 Bill";
		String bill5 = "$5 Bill";
		String bill10 = "$10 Bill";

		BigDecimal balance = new BigDecimal(0);

		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI test = new VendingMachineCLI(menu);

		String expectedResult = "$1.00";
		String expectedResult2 = "$2.00";
		String expectedResult5 = "$5.00";
		String expectedResult10 = "$10.00";
		Assert.assertEquals(expectedResult, test.moneyDeposit(bill));
		Assert.assertEquals(expectedResult2, test.moneyDeposit(bill2));
		Assert.assertEquals(expectedResult5, test.moneyDeposit(bill5));
		Assert.assertEquals(expectedResult10, test.moneyDeposit(bill10));

	}

	@Test
	public void purchaseItemsTest() throws FileNotFoundException {

		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI test = new VendingMachineCLI(menu);
		test.loadMachine();
		test.moneyDeposit("$5 Bill");

		String actualResult = test.purchaseItems("A1");
		String expectedResult = "Balance remaining: $" + "1.95";

		Assert.assertEquals(expectedResult, actualResult);
	}

	@Test
	public void candyTest() {
		VendingItem candy = new Candy("G4", "Sour Patch", new BigDecimal(5.00), "Candy");

		String actualName = candy.getName();
		String expectedName = "Sour Patch";

		BigDecimal actualPrice = candy.getPrice();
		BigDecimal expectedPrice = new BigDecimal(5.00);

		String actualLocation = candy.getLocation();
		String expectedLocation = "G4";

		int actualInv = candy.getInventory();
		int expectedInv = 5;

		String actualType = candy.getType();
		String expectedType = "Candy";

		int actualSold = candy.getTotalSold();
		int expectedSold = 0;

		String actualMessage = candy.getMessage();
		String expectedMessage = "Munch Munch, Yum!";

		String actualAvail = candy.getAvailability();
		String expectedAvail = "";

		Assert.assertEquals(expectedName, actualName);
		Assert.assertEquals(expectedPrice, actualPrice);
		Assert.assertEquals(expectedLocation, actualLocation);
		Assert.assertEquals(expectedInv, actualInv);
		Assert.assertEquals(expectedType, actualType);
		Assert.assertEquals(expectedSold, actualSold);
		Assert.assertEquals(expectedMessage, actualMessage);
		Assert.assertEquals(expectedAvail, actualAvail);
	}

	@Test
	public void chipTest() {
		VendingItem chip = new Chip("A1", "Potato Crisps", new BigDecimal(3.05), "Chip");

		String actualName = chip.getName();
		String expectedName = "Potato Crisps";

		BigDecimal actualPrice = chip.getPrice();
		BigDecimal expectedPrice = new BigDecimal(3.05);

		String actualLocation = chip.getLocation();
		String expectedLocation = "A1";

		int actualInv = chip.getInventory();
		int expectedInv = 5;

		String actualType = chip.getType();
		String expectedType = "Chip";

		int actualSold = chip.getTotalSold();
		int expectedSold = 0;

		String actualMessage = chip.getMessage();
		String expectedMessage = "Crunch Crunch, Yum!";

		String actualAvail = chip.getAvailability();
		String expectedAvail = "";

		Assert.assertEquals(expectedName, actualName);
		Assert.assertEquals(expectedPrice, actualPrice);
		Assert.assertEquals(expectedLocation, actualLocation);
		Assert.assertEquals(expectedInv, actualInv);
		Assert.assertEquals(expectedType, actualType);
		Assert.assertEquals(expectedSold, actualSold);
		Assert.assertEquals(expectedMessage, actualMessage);
		Assert.assertEquals(expectedAvail, actualAvail);
	}

	@Test
	public void gumTest() {
		VendingItem gum = new Gum("D1", "U-Chews", new BigDecimal(0.85), "Gum");

		String actualName = gum.getName();
		String expectedName = "U-Chews";

		BigDecimal actualPrice = gum.getPrice();
		BigDecimal expectedPrice = new BigDecimal(0.85);

		String actualLocation = gum.getLocation();
		String expectedLocation = "D1";

		int actualInv = gum.getInventory();
		int expectedInv = 5;

		String actualType = gum.getType();
		String expectedType = "Gum";

		int actualSold = gum.getTotalSold();
		int expectedSold = 0;

		String actualMessage = gum.getMessage();
		String expectedMessage = "Chew Chew, Yum!";

		String actualAvail = gum.getAvailability();
		String expectedAvail = "";

		Assert.assertEquals(expectedName, actualName);
		Assert.assertEquals(expectedPrice, actualPrice);
		Assert.assertEquals(expectedLocation, actualLocation);
		Assert.assertEquals(expectedInv, actualInv);
		Assert.assertEquals(expectedType, actualType);
		Assert.assertEquals(expectedSold, actualSold);
		Assert.assertEquals(expectedMessage, actualMessage);
		Assert.assertEquals(expectedAvail, actualAvail);
	}

	@Test
	public void drinkTest() {
		VendingItem drink = new Drink("C1", "Cola", new BigDecimal(1.25), "Drink");

		String actualName = drink.getName();
		String expectedName = "Cola";

		BigDecimal actualPrice = drink.getPrice();
		BigDecimal expectedPrice = new BigDecimal(1.25);

		String actualLocation = drink.getLocation();
		String expectedLocation = "C1";

		int actualInv = drink.getInventory();
		int expectedInv = 5;

		String actualType = drink.getType();
		String expectedType = "Drink";

		int actualSold = drink.getTotalSold();
		int expectedSold = 0;

		String actualMessage = drink.getMessage();
		String expectedMessage = "Glug Glug, Yum!";

		String actualAvail = drink.getAvailability();
		String expectedAvail = "";

		Assert.assertEquals(expectedName, actualName);
		Assert.assertEquals(expectedPrice, actualPrice);
		Assert.assertEquals(expectedLocation, actualLocation);
		Assert.assertEquals(expectedInv, actualInv);
		Assert.assertEquals(expectedType, actualType);
		Assert.assertEquals(expectedSold, actualSold);
		Assert.assertEquals(expectedMessage, actualMessage);
		Assert.assertEquals(expectedAvail, actualAvail);
	}

	@Test
	public void auditTest() throws IOException {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI test = new VendingMachineCLI(menu);
		File testFile = new File("Log-Test.txt");
		try (PrintWriter writer = new PrintWriter(new FileOutputStream(testFile, false))) {
			writer.append("");
		}
		test.audit("Hello World", "Log-Test.txt");

		String actual = "";
		Scanner auditTest = new Scanner(testFile);
		actual = auditTest.nextLine();
		auditTest.close();
		System.out.println(actual);
		String expected = "Hello World";

		Assert.assertEquals(expected, actual);
	}
}
