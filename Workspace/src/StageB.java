import java.util.*; // Lazy loading everything...

// Notes: Toy.class.isInstance(holdings[x]))

public class StageB {
	// Scanner Object
	private static Scanner consoleInput = new Scanner(System.in);
	
	// Track if the program just started and is first run of main()
	private static boolean programStart = false;
	
	// Items Storage Array
	private static Item[] holdings = new Item[5];
	// Stage D Requirement
	//private static ArrayList<Item> holdings = new ArrayList<Item>();
	
	// Item Group Array
	// these are permanent and cannot be changed
	private static final String[] itemGroups = new String[] {"Toy","DressUp","PlayEquipment"}; 
	
	// Tracking Number of Items in holdings
	private static int holdingsCount = 0;
	
	/* Main() Method */
	public static void main(String[] args) {
		// On First load only
		if (programStart == false) {
			displayIntro();
			programStart = true;
		}
		
		String userInput = "";
		displayMenu();
		
		// do-while loop for menu selections
		do {
			userInput = consoleInput.nextLine().toLowerCase(); // drop to lower case for easy matching
			
			switch(userInput) {
				/* Menu Option: 1 */
				case "1":
					itemCreate();
					displayMenu();
					break;
					
				/* Menu Option: 2 - Create Event */
				case "2":
					itemList();
					displayMenu();
					break;
				
				/* Menu Option: 5 - Create Booking/Ticket */
				case "3":
					itemShow();
					displayMenu();
					break;
				
				case "4":
					itemHire();
					displayMenu();
					break;
					
				case "5":
					itemReturn();
					displayMenu();
					break;
				
				case "8":
					generateReport("summary");
					displayMenu();
					break;
					
				/* Exit Application */
				case "x":
					System.out.println("Farewell!");
					break;
				/* No Matching case */
				default:
					System.out.println("Invalid Selection. Please try again!");
			}
		} while (!userInput.contentEquals("x"));
		
	}
	
	public static void displayIntro() {
		String text = "Welcome to...\n"
				+ "   _____ _     _ _     _     _____  _                                                                                         \r\n"
				+ "  / ____| |   (_) |   | |   |  __ \\| |                                                                                        \r\n"
				+ " | |    | |__  _| | __| |___| |__) | | __ _ _   _                                                                             \r\n"
				+ " | |    | '_ \\| | |/ _` |_  /  ___/| |/ _` | | | |                                                                            \r\n"
				+ " | |____| | | | | | (_| |/ /| |    | | (_| | |_| |                                                                            \r\n"
				+ "  \\_____|_| |_|_|_|\\__,_/___|_| __ |_|\\__,_|\\__, |                                   _      _____           _                 \r\n"
				+ " |_   _| |                 |  \\/  |          __/ |                                  | |    / ____|         | |                \r\n"
				+ "   | | | |_ ___ _ __ ___   | \\  / | __ _ _ _|___/_ _  __ _  ___ _ __ ___   ___ _ __ | |_  | (___  _   _ ___| |_ ___ _ __ ___  \r\n"
				+ "   | | | __/ _ \\ '_ ` _ \\  | |\\/| |/ _` | '_ \\ / _` |/ _` |/ _ \\ '_ ` _ \\ / _ \\ '_ \\| __|  \\___ \\| | | / __| __/ _ \\ '_ ` _ \\ \r\n"
				+ "  _| |_| ||  __/ | | | | | | |  | | (_| | | | | (_| | (_| |  __/ | | | | |  __/ | | | |_   ____) | |_| \\__ \\ ||  __/ | | | | |\r\n"
				+ " |_____|\\__\\___|_| |_| |_| |_|  |_|\\__,_|_| |_|\\__,_|\\__, |\\___|_| |_| |_|\\___|_| |_|\\__| |_____/ \\__, |___/\\__\\___|_| |_| |_|\r\n"
				+ "                                                      __/ |                                        __/ |                      \r\n"
				+ "                                                     |___/               Version 1.0              |___/    By Adam Mutimer \n\n";
		System.out.print(text);
	}
	
	/*
	 * Display Menu
	 */
	public static void displayMenu() {
		System.out.print("\r\n");
		
		System.out.println("***************************************");
		System.out.println("*           Item Management           *");
		System.out.println("***************************************");
		System.out.println("  1: Item - Create"); // Create a new item
		System.out.println("  2: Item - List All"); // Create a new item
		System.out.println("  3: Item - Show Item"); // Show item and details about item, including current hires
		System.out.println("  4: Item - Hire"); // Show item and details about item, including current hires
		System.out.println("  5: Item - Return");
		System.out.println("");
		
		System.out.println("***************************************");
		System.out.println("*         Customer Management         *");
		System.out.println("***************************************");
		System.out.println("  4: Customer - Create"); // Create a new Customer
		System.out.println("  5: Customer - List"); // Create a new Customer
		System.out.println("  6: Customer - Show Customer Details"); // Show details about customer, including current hires 
		System.out.println("  7: Customer - Hire"); // Hire an item to a customer 
		System.out.println("");

		System.out.println("***************************************");
		System.out.println("*               Reports               *");
		System.out.println("***************************************");
		System.out.println("  8: Hire Summary"); // Create a new item
		System.out.println("");		
		System.out.println("  X: Exit");
		System.out.println("");
		System.out.print("> Please enter selection: ");
		System.out.println("");
	}

	// Simple Method for Pausing the program
	public static void systemPause() {
		System.out.print("Press [Enter] to continue");
		consoleInput.nextLine(); // Not storing input as we dont care really
		System.out.print("\r\n");
	}
	
	// Method for creating a new item
	public static void itemCreate() {
		String userInput = "";
		System.out.print("\r\n");
		System.out.println("**** Item - Create **** " + itemGroups.length);
		
		String itemGroup = ""; // Store Item Type
		boolean groupMatch = false;	
		
		// Loop until we get valid input
		while (!groupMatch) {
			
			System.out.println("Avaliable Item Groups: ");
			
			// Loop though type array and print the item groups
			for (int i = 0; i < itemGroups.length; i++ ) {
				// Borrowing capitalize from items class :)
				System.out.print(itemGroups[i]);
				if ((i+1) < itemGroups.length) {
					 System.out.print(", ");
				}
			}
			System.out.print("\r\n\r\n");

			System.out.print("Item Group: ");
			userInput = consoleInput.nextLine();
			
			// Don't want Empty Input
			if (userInput != "") {
				for (int i = 0; i < itemGroups.length; i++) {
					groupMatch = itemGroups[i].toLowerCase().contains(userInput.toLowerCase());
					
					// If we get a match no point in continuing the loop
					if (groupMatch) {
						itemGroup = userInput.toLowerCase();
						break;
					}
				}
			}
			// Not Match throw error
			if (!groupMatch) {
				System.out.println("ERROR: Group (" + userInput + ") not found please try again!\r\n");
			}
			
		}
		
		System.out.print("Item Name: ");
		userInput = consoleInput.nextLine();
		String itemName = userInput;
		
		System.out.print("Description: ");
		userInput = consoleInput.nextLine();
		String itemDescription = userInput;
		
		System.out.print("Item Cost P/W: ");
		userInput = consoleInput.nextLine();
		Double itemCost = Double.parseDouble(userInput);
		
		// Selecting the right class to instantiate
		if (itemGroup.equals("toy")) {
			holdings[holdingsCount] = new Toy(itemName, itemDescription, itemCost);
		} else if (itemGroup.equals("dressup")) {
			holdings[holdingsCount] = new DressUp(itemName, itemDescription, itemCost);	
		} else if (itemGroup.equals("playequipment")) {
			holdings[holdingsCount] = new PlayEquipment(itemName, itemDescription, itemCost);
		} else {
			// User should never get here, but they always manage to find a way...
			System.out.println("ERROR: Something went wrong during the item creation process!");
			System.out.println("ERROR: Please contact technical support.");
		}
		
		holdingsCount++; // Increment the holdings count
		System.out.print("\r\n");
	}
	
	// Method for listing all items
	public static void itemList() {
		System.out.print("\r\n");
		System.out.println("**** Item - List ****");
		
		// Fancy Table B.S 
		System.out.format("+-----+----------------------+------------------------+---------------+-----------+-----------+--------------+%n");
		System.out.format("| ID  | Name                 | Description            | Cost Per Week | Hired     | Num Weeks | Customer ID  |%n");
		System.out.format("+-----+----------------------+------------------------+---------------+-----------+-----------+--------------+%n");
		
		for (int i=0; i<holdingsCount; i++) {
			Item item = holdings[i];
			item.itemListEntry();
		}
		System.out.format("+-----+----------------------+------------------------+---------------+-----------+-----------+--------------+%n");
		systemPause();
	}
	
	// Method for displaying a single item in detail
	public static void itemShow() {
		String userInput = "";
		System.out.print("\r\n");
		System.out.println("**** Item - Show Item ****");
		
		System.out.print("Item ID: ");
		userInput = consoleInput.nextLine();
		int itemID = Integer.parseInt(userInput);
		itemID = itemID - 100; // Simple maths to get us the right array row haha
		
		holdings[itemID].itemShow();
		
		System.out.println("r) Return Item \n\t c) Continue");
		System.out.print("> Please enter selection: ");
		userInput = "";
		userInput = consoleInput.nextLine();
		while(!userInput.toLowerCase().equals("r") && !userInput.toLowerCase().equals("c")) {
			System.out.println("Invalid Selection. Please try again!");
			System.out.println("");
			System.out.println("r) Return Item \t c) Continue");
			System.out.print("> Please enter selection: ");
			userInput = "";
			userInput = consoleInput.nextLine();
		}
		
		if (userInput.toLowerCase().equals("r")) {
			if (holdings[itemID].returnItem()) {
				System.out.println("Item " + (itemID + 100) + " Returned Successfully");
			} else {
				System.out.println("ERROR: Item " + (itemID + 100) + " was not returned successfully!");
			}
			systemPause();
		}
	}
	
	// Method for Hiring an Item
	public static void itemHire() {
		String userInput = "";
		System.out.print("\r\n");
		System.out.println("**** Item - Hire ****");		
		
		System.out.print("Customer ID: ");
		userInput = consoleInput.nextLine();
		String customerID = userInput;

		System.out.print("Item ID: ");
		userInput = consoleInput.nextLine();
		int itemID = Integer.parseInt(userInput);
		itemID = itemID - 100; // Get actual array row
		
		System.out.print("Weeks to hire: "); 
		userInput = consoleInput.nextLine();
		int numWeeks = Integer.parseInt(userInput);
				
		holdings[itemID].hireItem(customerID, numWeeks);
		systemPause();
		
	}
	
	// Method for Returning an item
	public static void itemReturn() {
		String userInput = "";
		System.out.print("\r\n");
		System.out.println("**** Item - Return ****");
		
		System.out.print("Item ID: ");
		userInput = consoleInput.nextLine();
		int itemID = Integer.parseInt(userInput);
		itemID = itemID - 100; // Get actual array row
		
		if (holdings[itemID].returnItem()) {
			System.out.println("Item " + (itemID + 100) + " Returned Successfully");
		} else {
			System.out.println("ERROR: Item " + (itemID + 100) + " was not returned successfully!");
		}
		systemPause();
		
	}
	
	// Method for generating various reports
	public static void generateReport(String reportName) {
		switch (reportName.toLowerCase()) {
		case "summary" :
			double runningTotal = 0; // Keep track of our running total
			System.out.print("\r\n");
			System.out.println("**** Report: Hire Summary ****");
			
			// Fancy Table B.S 
			System.out.format("+-----+----------------------+------------------------+---------------+-----------+-------------+-----------+---------------+%n");
			System.out.format("| ID  | Name                 | Description            | Cost Per Week | Num Weeks | Customer ID | Sub Total | Total Revenue |%n");
			System.out.format("+-----+----------------------+------------------------+---------------+-----------+-------------+-----------+---------------+%n");
			
			for (int i=0; i<holdingsCount; i++) {
				Item item = holdings[i];
				if (item.getItemHired()) {
					runningTotal += item.itemHireSummary(runningTotal);
					
					
				}
			}
			System.out.format("+-----+----------------------+------------------------+---------------+-----------+-------------+-----------+---------------+%n");
			systemPause();			
			break;
			
		case "list" :
			// List all items
			break;
		
		default:
			// This should never ever ever ever ever happen, but users will somehow find a way...
			System.out.println("ERROR: Report type not found. Please contact technical support!");
		}
	}
}
