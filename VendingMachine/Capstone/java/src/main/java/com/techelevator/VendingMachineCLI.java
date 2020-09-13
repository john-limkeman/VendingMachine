package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.techelevator.view.Menu;

public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE,
			MAIN_MENU_OPTION_EXIT };
	private static final String[] PURCHASE_MENU = { "Feed Money", "Select Product", "Finish Transaction", "Back" };
	private static final String[] MONEY_MENU = { "$1 Bill", "$2 Bill", "$5 Bill", "$10 Bill", "Done" };
	public BigDecimal balance = new BigDecimal(0).setScale(2, RoundingMode.HALF_UP);
	public static Map<String, VendingItem> inventory = new LinkedHashMap<String, VendingItem>();

	private Menu menu;

	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}

	public void run() throws FileNotFoundException {
		while (true) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			System.out.println(choice);

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				System.out.println(displayItems(inventory));
				
			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				processPurchaseMenuOptions();

			} else if (choice.equals(MAIN_MENU_OPTION_EXIT)) {
				System.exit(0);
			}
		}
	}

	public String displayItems(Map<String, VendingItem>  inventory ) {
		Set<String> allTheKeys = inventory.keySet();
		String displayResultOfItems = "";
		for (String key : allTheKeys) {
			VendingItem item = inventory.get(key);
			displayResultOfItems +=  key + " " + item.getName() + " $" + item.getPrice() + " " + item.getAvailability() + "\n";
			
		}return displayResultOfItems;

	}

	public void processPurchaseMenuOptions() throws FileNotFoundException {
		String purchaseMenuOption = "";
		while (!purchaseMenuOption.equals("Back")) {
			purchaseMenuOption = (String) menu.getChoiceFromOptions(PURCHASE_MENU);

			if (purchaseMenuOption.equals("Feed Money")) {
				processMoneyFeed();
			} else if (purchaseMenuOption.equals("Select Product")) {
				System.out.println(displayItems(inventory));
				Scanner selector = new Scanner(System.in);
				String selection = selector.nextLine();
				System.out.println(purchaseItems(selection));
			}
			else if (purchaseMenuOption.equals("Finish Transaction")) {
				String returnAmount = returnCoin(balance);
				System.out.println(returnAmount);
				balance = balance.subtract(balance);
				System.out.println("Current Balance: $" + balance);
				purchaseMenuOption = "Back";
				
				
				
			} else {
				System.out.println("Error");
			}
		}
	}

	public String returnCoin(BigDecimal changeLeft) throws FileNotFoundException {

		int quarter = 0;
		int dime = 0;
		int nickel = 0;
		
		changeLeft = changeLeft.setScale(2, RoundingMode.HALF_UP);
		BigDecimal quarterBD = new BigDecimal(0.25).setScale(2, RoundingMode.HALF_UP);
		BigDecimal dimeBD = new BigDecimal(0.10).setScale(2, RoundingMode.HALF_UP);
		BigDecimal nickelBD = new BigDecimal(0.05).setScale(2, RoundingMode.HALF_UP);

		while (changeLeft.compareTo(quarterBD) >= 0) {
			changeLeft = changeLeft.subtract(quarterBD);
			quarter++;
}
		while (changeLeft.compareTo(dimeBD) >= 0) {
			changeLeft = changeLeft.subtract(dimeBD);
			dime++;
}
		while (changeLeft.compareTo(nickelBD) >= 0) {
			changeLeft = changeLeft.subtract(nickelBD);
			nickel++;
}
		System.out.println("Current Balance: $" + balance);
		String change = "Your change is: " + quarter + " quarter(s), " + dime + " dime(s), " + nickel + " nickel(s).";
		
		//audit function
		String auditString = ">" + timeStamp() +  " GIVE CHANGE: "  + balance + " $0.00" + ">" + "\n";
		audit(auditString, "Log.txt");
		//audit end
		
		return change;
	}

	public String purchaseItems(String selection) throws FileNotFoundException {
		VendingItem chosen = null;
		String result = "";
		BigDecimal priorBalance = balance;
		if (!inventory.containsKey(selection)) {
			System.out.println("Invalid item.");
		} else {
			chosen = inventory.get(selection);
			if (chosen.getPrice().compareTo(balance) == 1) {
				System.out.println("MONEY PLEASE!");
			}else if (chosen.getInventory() == 0){
				System.out.println(chosen + " SOLD OUT");
			} else {	
			System.out.println("You have selected " + chosen.getName()+", which costs: $" + chosen.getPrice());
			System.out.println(chosen.getMessage());
			chosen.subtractItem();
			balance = balance.subtract(chosen.getPrice());
			System.out.println(chosen.getName() + " remaining: " + chosen.getInventory());
			result = "Balance remaining: $" + balance;
			
			//audit operations
			String auditString = ">" + timeStamp() + " " + chosen.getName() + " " + chosen.getLocation() + " " + priorBalance + " " + balance;
			audit(auditString, "Log.txt");
			//audit end
			
			return result;
			}
		}return result;
	}

	private void processMoneyFeed() throws FileNotFoundException {
		String feedOptions = "";
		while (!feedOptions.contentEquals("Done")) {
			feedOptions = (String) menu.getChoiceFromOptions(MONEY_MENU);
			
			//audit operations
			String moneyAdded = moneyDeposit(feedOptions);
			if (!moneyAdded.equals("")) {
			String auditString = ">" + timeStamp() + " FEED MONEY: " + moneyAdded + " " + balance + " "; 
			audit(auditString, "Log.txt");
			//end audit
			
			System.out.println("Current Balance: $" + balance);
			}
		}
	}

	public String moneyDeposit(String bill) {
		BigDecimal deposit1 = new BigDecimal(1);
		BigDecimal deposit2 = new BigDecimal(2);
		BigDecimal deposit5 = new BigDecimal(5);
		BigDecimal deposit10 = new BigDecimal(10);

		String moneyAdded = "";
		if (!bill.contentEquals("Done")) {
			if (bill.equals("$1 Bill")) {
				balance = balance.add(deposit1);
				System.out.println("You added 1 dollar!");
				moneyAdded = "$1.00";
			} else if (bill.equals("$2 Bill")) {
				balance = balance.add(deposit2);
				System.out.println("You added 2 dollars!");
				moneyAdded = "$2.00";
			} else if (bill.equals("$5 Bill")) {
				balance = balance.add(deposit5);
				System.out.println("You added 5 dollars!");
				moneyAdded = "$5.00";
			} else if (bill.equals("$10 Bill")) {
				balance = balance.add(deposit10);
				System.out.println("You added 10 dollars!");
				moneyAdded = "$10.00";
				
			}
		}
		return moneyAdded;
	}

	public static void main(String[] args) throws IOException {
			File auditLog = new File("Log.txt");
			if (!auditLog.exists()) {
				auditLog.createNewFile();
			}
					
			Menu menu = new Menu(System.in, System.out);
			VendingMachineCLI cli = new VendingMachineCLI(menu);
			cli.loadMachine();
			cli.run();
		}

	public String timeStamp() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyy HH:mm:ss");
		Date date = new Date();
		String auditTime = formatter.format(date);
		return auditTime;
	}
	public void audit(String audit, String file) throws FileNotFoundException {
		try(PrintWriter writer = new PrintWriter(new FileOutputStream(file, true))) {
			writer.append(audit);
		}
	}

	public void loadMachine() throws FileNotFoundException {
		File invSheet = new File("vendingmachine.csv");
		if (!invSheet.exists()) {
			System.out.println("Out of order");
			System.exit(0);
		} else {
			Scanner invScan = new Scanner(invSheet);
			while (invScan.hasNextLine()) {
				String lineItem = invScan.nextLine();
				String[] itemProp = lineItem.split("\\|"); 
															
				BigDecimal price = new BigDecimal(itemProp[2]);
				VendingItem item = null;
				if (itemProp[3].equals("Chip")) {
					item = new Chip(itemProp[0], itemProp[1], price, itemProp[3]);
					inventory.put(itemProp[0], item);
				} else if (itemProp[3].equals("Candy")) {
					item = new Candy(itemProp[0], itemProp[1], price, itemProp[3]);
					inventory.put(itemProp[0], item);
				} else if (itemProp[3].equals("Drink")) {
					item = new Drink(itemProp[0], itemProp[1], price, itemProp[3]);
					inventory.put(itemProp[0], item);
				} else if (itemProp[3].equals("Gum")) {
					item = new Gum(itemProp[0], itemProp[1], price, itemProp[3]);
					inventory.put(itemProp[0], item);
				}
			}

		}
	}
}
