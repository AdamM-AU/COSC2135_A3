import java.util.*; // Lazy loading everything...

public class StageA {
	// Scanner Object
	private static Scanner consoleInput = new Scanner(System.in);
	
	// Track if the program just started and is first run of main()
	private static boolean programStart = false;
	
	// Items Storage Array
	private static Item[] holdings = new Item[5];
	
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
				//consoleBookingCreate();
				itemShow();
				displayMenu();
				break;
			
			case "4":
				//consoleBookingRefund();
				displayMenu();
				break;
				
			case "5":
				//consoleBookingDisplay();
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
		
		System.out.print("Item Name: ");
		userInput = consoleInput.nextLine();
		String itemName = userInput;
		
		System.out.print("Description: ");
		userInput = consoleInput.nextLine();
		String itemDescription = userInput;
		
		System.out.print("Item Cost P/W: ");
		userInput = consoleInput.nextLine();
		Double itemCost = Double.parseDouble(userInput);
		
		holdings[holdingsCount] = new Item(itemName, itemDescription, itemCost);
		
		holdingsCount++; // Increment the holdings count
		System.out.print("\r\n");
	}
	
	// Method for listing all items
	public static void itemList() {
		System.out.print("\r\n");
		
		for (int i=0; i<holdingsCount; i++) {
			Item item = holdings[i];
			System.out.println(item.getItemID() + " | " + item.getItemName());
		}
		
		systemPause();
	}
	
	// Method for displaying a single item in detail
	public static void itemShow() {
		String userInput = "";
		System.out.print("\r\n");
		
		System.out.print("Item ID: ");
		userInput = consoleInput.nextLine();
		int itemID = Integer.parseInt(userInput);
		itemID = itemID - 100; // Simple Maths to get us the right array entry haha
		
		System.out.println(holdings[itemID].getItemID());
		System.out.println(holdings[itemID].getItemName());
		System.out.println(holdings[itemID].getItemDescription());
		System.out.println(holdings[itemID].getItemCost());
		System.out.println(holdings[itemID].getItemHired());
		System.out.println(holdings[itemID].getCustomerID());
		System.out.println(holdings[itemID].getNumWeeks());
		
		systemPause();
	}
	
	// Method for Hiring an Item
	public static void itemHire() {
		
	}
	
	// Method for Returning an item
	public static void itemReturn() {
		
	}
	
	// Method for generating various reports
	public static void generateReport(String reportName) {
		switch (reportName.toLowerCase()) {
		case "summary" :
			// Summary Report
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
