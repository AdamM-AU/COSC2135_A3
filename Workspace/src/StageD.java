import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*; // Lazy loading everything...

// Notes: Toy.class.isInstance(holdings[x]))

public class StageD {
	// Scanner Object
	private static Scanner consoleInput = new Scanner(System.in);
	
	// Track if the program just started and is first run of main()
	private static boolean programStart = false;
	
	// Items Storage Array
	private static Item[] holdings = new Item[10];
	// Stage D Requirement
	//private static ArrayList<Item> holdings = new ArrayList<Item>();
	
	// Item Group Array
	// these are permanent and cannot be changed
	private static final String[] itemGroups = new String[] {"Toy","Dress-Ups","Play Equipment"}; 
	
	// Tracking Number of Items in holdings
	private static int holdingsCount = 0;
	
	// Save/Restore File
	private static final String savefile = "data.csv";
	
	/* Main() Method */
	public static void main(String[] args) {
		// On First load only
		if (programStart == false) {
			displayIntro();
			restoreUGD(savefile); // Restore previous UGD (User Generated Data)
			programStart = true;
		}
		
		String userInput = "";
		displayMenu();
		
		// do-while loop for menu selections
		do {
			userInput = consoleInput.nextLine().toLowerCase(); // drop to lower case for easy matching
			
			switch(userInput) {
				/* Menu Option: 1 - Create Item */
				case "1":
					itemCreate("none");
					displayMenu();
					break;
					
				/* Menu Option: 2 - Show Item */
				case "2":
					itemShow();
					displayMenu();
					break;
				
				/* Menu Option 3 - Hire Item */
				case "3":
					itemHire();
					displayMenu();
					break;

				/* Menu Option 4 - Return Item */	
				case "4":
					itemReturn();
					displayMenu();
					break;

				/* Menu Option 7 - Item List Report */
				case "7":
					generateReport("list");
					displayMenu();
					break;
				
				/* Menu Option: 8 - Hire Summary Report */
				case "8":
					generateReport("summary");
					displayMenu();
					break;

				/* Exit Application */
				case "x":
					saveUGD(savefile); // Save on exit
					System.out.println("Farewell!");
					// Save Code here!
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
		System.out.println("  1: Item - Create"); 		// Create a new item
		System.out.println("  2: Item - Show Item"); 	// Show item and details about item, including current hires
		System.out.println("  3: Item - Hire"); 		// Hire an item
		System.out.println("  4: Item - Return");		// Return an item
		System.out.println("");

		System.out.println("***************************************");
		System.out.println("*               Reports               *");
		System.out.println("***************************************");
		System.out.println("  7: Inventory List"); 	// Inventory List / Item List
		System.out.println("  8: Hire Summary"); 	// Hire Summary
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
	public static void itemCreate(String itemGroup) {
		// Declare and/or initialize variables
		String userInput = null;		// User Input Temp Variable
		String itemName = null; 		// Item Name
		String itemDescription = null;	// Item Description
		double itemCost = 0;			// Cost of Item
		
		String toyCategory = null;		// Used only for toy type items - Category
		String itemSize = null;			// Used only for DressUp type items - Size
		String itemGenre = null;		// Used only for DressUp type items - Genre
		int itemPieceCount = 0;		   	// Used only for DressUp type items - Piece Count
		double itemWeight = 0;
		double itemHeight = 0;
		double itemWidth = 0;
		double itemDepth = 0;
		
		
		
		System.out.print("\r\n");
		System.out.println("**** Item - Create ****");
		
		boolean groupMatch = false;	
		
		// if itemType hasn't been set when the method was called request user input
		if (itemGroup.equals("none")) {
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
		} else {
			System.out.println("Item Group: " + Item.capitalize(itemGroup));
		}
		// Primary Questions
		System.out.print("Item Name: ");
		userInput = consoleInput.nextLine();
		itemName = userInput;
		
		System.out.print("Description: ");
		userInput = consoleInput.nextLine();
		itemDescription = userInput;

		/* NEEEDS VALIDATION */
		// Per Item Type Questions
		if (itemGroup.equals("toy")) {
			System.out.println("");
			System.out.println("Avaliable Toy Categories: ");
			
			String[] categories = Toy.getItemToyCategories(); // Fetch Toy categories
			
			// Loop though type array and print the item groups
			for (int i = 0; i < categories.length; i++ ) {
				// Borrowing capitalize from items class :)
				System.out.print(categories[i]);
				if ((i+1) < categories.length) {
					 System.out.print(", ");
				}
			}
			
			System.out.print("\r\n\r\n");		
			// Needs Validation
			System.out.print("Toy Category: ");
			userInput = consoleInput.nextLine();
			toyCategory = userInput;
		}
		
		if (itemGroup.equals("dress-ups")) {
			System.out.print("Dress-Up Size: ");
			userInput = consoleInput.nextLine();
			itemSize = userInput;
			
			System.out.print("Dress-Up Genre: ");
			userInput = consoleInput.nextLine();
			itemGenre = userInput;
			
			// Needs Validation
			System.out.print("Dress-Up Piece Count: ");
			userInput = consoleInput.nextLine();
			itemPieceCount = Integer.parseInt(userInput);
		}
		
		if (itemGroup.equals("play equipment")) {
			System.out.print("Item Cost P/W: ");
			userInput = consoleInput.nextLine();
			itemCost = Double.parseDouble(userInput);
			
			System.out.print("Item Weight (KG): ");
			userInput = consoleInput.nextLine();
			itemWeight = Double.parseDouble(userInput);

			System.out.print("Item Height (CM): ");
			userInput = consoleInput.nextLine();
			itemHeight = Double.parseDouble(userInput);

			System.out.print("Item Width (CM): ");
			userInput = consoleInput.nextLine();
			itemWidth = Double.parseDouble(userInput);
			
			System.out.print("Item Depth (CM): ");
			userInput = consoleInput.nextLine();
			itemDepth = Double.parseDouble(userInput);
		}
		
		/* END NEEDS VALIDATION */
			
		// Selecting the right class to instantiate
		if (itemGroup.equals("toy")) {
			try {
					holdings[holdingsCount] = new Toy(itemName, itemDescription, toyCategory);
					System.out.println("Item Created Successfully!");
				}
				catch(IllegalArgumentException error) {
				  System.out.println(error.getMessage());
				  // Re run item creation skipping item type input
				  itemCreate("toy");
				}
			
		} else if (itemGroup.equals("dress-ups")) {
			holdings[holdingsCount] = new DressUp(itemName, itemDescription, itemSize, itemGenre, itemPieceCount);
			
		} else if (itemGroup.equals("play equipment")) {
			holdings[holdingsCount] = new PlayEquipment(itemName, itemDescription, itemCost, itemWeight, itemHeight, itemWidth, itemDepth);
			
		} else {
			// User should never get here, but they always manage to find a way...
			System.out.println("ERROR: Something went wrong during the item creation process!");
			System.out.println("ERROR: Please contact technical support.");
		}
		
		holdingsCount++; // Increment the holdings count
		System.out.print("\r\n");
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
		
		System.out.println("r) Return Item");
		System.out.println("c) Continue");
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
			try {
				holdings[itemID].returnItem();
			} catch(HiringException error) {
				System.out.println(error.getMessage());
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
				
		try {
			holdings[itemID].hireItem(customerID, numWeeks);
		} catch (HiringException error) {
			System.out.println(error.getMessage());
		}
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
		
		try {
			holdings[itemID].returnItem();
		} catch(HiringException error) {
			System.out.println(error.getMessage());
		}
		systemPause();
		
	}
	
	/*
	 *  Method for generating various reports
	 *  	Such as a Hire Summary and Item List
	 */
	public static void generateReport(String reportName) {
		switch (reportName.toLowerCase()) {
		case "summary" :
			double runningTotal = 0.00; // Keep track of our running total
			int hiredCount = 0; // Keep track of hired items
			System.out.print("\r\n");
			System.out.println("**** Report: Hire Summary ****");
			
			// Fancy Table B.S 
			System.out.format("+-----+----------------------+------------------------+---------------+-----------+-------------+-----------+---------------+%n");
			System.out.format("| ID  | Name                 | Category > Sub         | Cost Per Week | Num Weeks | Customer ID | Sub Total | Total Revenue |%n");
			System.out.format("+-----+----------------------+------------------------+---------------+-----------+-------------+-----------+---------------+%n");
			
			for (int i=0; i<holdingsCount; i++) {
				Item item = holdings[i];
				if (item.getItemHired()) {
					runningTotal = item.itemHireSummary(runningTotal);
					hiredCount++;
				}
			}
			System.out.format("+-----+----------------------+------------------------+---------------+-----------+-------------+-----------+---------------+%n");
			System.out.println("Total Income: $" + Item.printableDouble(runningTotal));
			System.out.println("Total Items Hired: " + hiredCount);
			System.out.println("");
			
			systemPause();
			break;
			
		case "list" :
			System.out.print("\r\n");
			System.out.println("**** Report: Inventory List ****");
			
			// Fancy Table B.S 
			System.out.format("+-----+----------------------+------------------------+---------------+------------------------+----------------------------------------------------+-------------+%n");
			System.out.format("| ID  | Name                 | Description            | Cost Per Week | Category > Sub         | Extra Information                                  | Hired       |%n");
			System.out.format("+-----+----------------------+------------------------+---------------+------------------------+----------------------------------------------------+-------------+%n");
			
			for (int i=0; i<holdingsCount; i++) {
				Item item = holdings[i];
				item.itemListEntry();
			}
			System.out.format("+-----+----------------------+------------------------+---------------+------------------------+----------------------------------------------------+-------------+%n");
			System.out.println("");
			systemPause();
			break;
		
		default:
			// This should never ever ever ever ever happen, but users will somehow find a way...
			System.out.println("ERROR: Report type not found. Please contact technical support!");
		}
	}
	
	/*
	 * Method for writing User Generated Data to file
	 * In this method i am writing to file in CSV format
	 * Stage D - Requirement
	 */
	public static void saveUGD(String savefile) {
		try {
			BufferedWriter writeCsv = new BufferedWriter(new FileWriter(savefile));
			
			int count = 0;
			
			// Loop the array and write lines to the buffer,
			// at the end of the line send \n to make a 
			// new line and flush the buffer to the file
			
			while (holdings.length > count) {
				
				// Find suitable extended class to read data
				if (Toy.class.isInstance(holdings[count])) {
					// Parent Class Fields	
					writeCsv.append("\"" + ((Toy) holdings[count]).getItemID() + "\",");					// Item ID
					writeCsv.append("\"" + ((Toy) holdings[count]).getItemName() + "\",");					// Item Name
					writeCsv.append("\"" + ((Toy) holdings[count]).getItemDescription() + "\",");			// Item Description
					writeCsv.append("\"" + ((Toy) holdings[count]).getItemCost() + "\",");					// Item Cost
					writeCsv.append("\"" + ((Toy) holdings[count]).getItemHired() + "\",");					// Item Hired
					writeCsv.append("\"" + ((Toy) holdings[count]).getCustomerID() + "\",");				// Hirer Customer ID
					writeCsv.append("\"" + ((Toy) holdings[count]).getNumWeeks() + "\",");					// Hired for x Weeks
					
					// Extended Class Fields
					writeCsv.append("\"Toy" + "\",");							 							// Item Class Type
					writeCsv.append("\"" + ((Toy) holdings[count]).getItemToyCategory() + "\","); 			// Cast typing extended class to holdings[count] - Item Category
					
				} else if (DressUp.class.isInstance(holdings[count])) {
					// Parent Class Fields
					writeCsv.append("\"" + ((DressUp) holdings[count]).getItemID() + "\",");				// Item ID
					writeCsv.append("\"" + ((DressUp) holdings[count]).getItemName() + "\",");				// Item Name
					writeCsv.append("\"" + ((DressUp) holdings[count]).getItemDescription() + "\",");			// Item Description
					writeCsv.append("\"" + ((DressUp) holdings[count]).getItemCost() + "\",");				// Item Cost
					writeCsv.append("\"" + ((DressUp) holdings[count]).getItemHired() + "\",");				// Item Hired
					writeCsv.append("\"" + ((DressUp) holdings[count]).getCustomerID() + "\",");			// Hirer Customer ID
					writeCsv.append("\"" + ((DressUp) holdings[count]).getNumWeeks() + "\",");				// Hired for x Weeks
					
					// Extended Class Fields
					writeCsv.append("\"DressUp" + "\",");													// Item Class Type
					writeCsv.append("\"" + ((DressUp) holdings[count]).getItemSize() + "\","); 				// Item Size
					writeCsv.append("\"" + ((DressUp) holdings[count]).getItemGenre() + "\","); 			// Item Genre
					writeCsv.append("\"" + ((DressUp) holdings[count]).getItemPieceCount() + "\","); 		// Item Piece Count
					
				} else if (PlayEquipment.class.isInstance(holdings[count])) {
					// Parent Class Fields
					writeCsv.append("\"" + ((PlayEquipment) holdings[count]).getItemID() + "\",");			// Item ID
					writeCsv.append("\"" + ((PlayEquipment) holdings[count]).getItemName() + "\",");		// Item Name
					writeCsv.append("\"" + ((PlayEquipment) holdings[count]).getItemDescription() + "\",");	// Item Description
					writeCsv.append("\"" + ((PlayEquipment) holdings[count]).getItemCost() + "\",");		// Item Cost
					writeCsv.append("\"" + ((PlayEquipment) holdings[count]).getItemHired() + "\",");		// Item Hired
					writeCsv.append("\"" + ((PlayEquipment) holdings[count]).getCustomerID() + "\",");		// Hirer Customer ID
					writeCsv.append("\"" + ((PlayEquipment) holdings[count]).getNumWeeks() + "\",");		// Hired for x Weeks
					
					// Extended Class Fields
					writeCsv.append("\"PlayEquipment" + "\",");												// Item Class Type
					writeCsv.append("\"" + ((PlayEquipment) holdings[count]).getItemWeight() + "\","); 		// Item Weight
					writeCsv.append("\"" + ((PlayEquipment) holdings[count]).getItemHeight() + "\","); 		// Item Height
					writeCsv.append("\"" + ((PlayEquipment) holdings[count]).getItemWidth() + "\","); 		// Item Width
					writeCsv.append("\"" + ((PlayEquipment) holdings[count]).getItemDepth() + "\","); 		// Item Depth
					
				}

				writeCsv.newLine(); // New Line/Row in file
				writeCsv.flush(); // Flush to file / Writer buffer to file.
				count++;
			}
			writeCsv.close(); // Final flush and close IO to file
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Method to de-quote a string
	public static String deQuote(String text) {
		text = text.replace("\"", "");
		return text.replace("\"", "");
	}
	
	
	/*
	 * Method for restoring User Generated Data to file
	 * In this method i am reading in a file thats in CSV format
	 * Stage D - Requirement
	 */
	public static void restoreUGD(String filepath) {
		// check if UGD file exists
		try {
			BufferedReader csvReader = new BufferedReader(new FileReader(filepath));
			String row;
			
			while ((row = csvReader.readLine()) != null) {
			    String[] split = row.split("\",");
			    
				String itemType = deQuote(split[7]);
				String itemName = deQuote(split[1]);
				String itemDescription = split[2];

				boolean isHired = Boolean.parseBoolean(deQuote(split[4]));
				
				switch(itemType.toLowerCase()) {
					case "toy":
						// Create Item
						holdings[holdingsCount] = new Toy(itemName, deQuote(itemDescription), deQuote(split[8]));
						
						if (isHired) {
							holdings[holdingsCount].setItemHired(true);
							holdings[holdingsCount].setCustomerID(deQuote(split[5]));
							holdings[holdingsCount].setNumWeeks(Integer.parseInt(deQuote(split[6])));
						}
						
						holdingsCount++; // Increment Holdings Counter
						break;
					
					case "dressup":
						holdings[holdingsCount] = new DressUp(itemName, deQuote(itemDescription), deQuote(split[8]), deQuote(split[9]), Integer.parseInt(deQuote(split[10])));
						
						if (isHired) {
							holdings[holdingsCount].setItemHired(true);
							holdings[holdingsCount].setCustomerID(deQuote(split[5]));
							holdings[holdingsCount].setNumWeeks(Integer.parseInt(deQuote(split[6])));
						}
						
						holdingsCount++; // Increment Holdings Counter
						break;
						
					case "playequipment":
						holdings[holdingsCount] = new PlayEquipment(itemName, deQuote(itemDescription), 
																	Double.parseDouble(deQuote(split[3])), 
																	Double.parseDouble(deQuote(split[8])), 
																	Double.parseDouble(deQuote(split[9])), 
																	Double.parseDouble(deQuote(split[10])), 
																	Double.parseDouble(deQuote(split[11]))
																	);
						
						if (isHired) {
							holdings[holdingsCount].setItemHired(true);
							holdings[holdingsCount].setCustomerID(deQuote(split[5]));
							holdings[holdingsCount].setNumWeeks(Integer.parseInt(deQuote(split[6])));
						}
						holdingsCount++; // Increment Holdings Counter
						break;
					
					default:
						// Do nothing bad data type, bad record skip it
						break;
				}			    
			}
			csvReader.close();
		}
		catch (Exception e) {
			
		}
		
		/*
		try {
			Scanner csv = new Scanner(new File(filepath)); // Read in file
			
			while (csv.hasNext()) { // Process the CSV line by line in a loop
				String csvRow = csv.next();
				String[] split = csvRow.split(",");
				System.out.println(csvRow);
				
				for (int i=0; i < split.length; i++) {
					//System.out.println(i + ") " + split[i]);	
				}
				if (split.length > 4) {
					String itemType = split[7];
				}
				*/
				/*String itemName = split[1];
				String itemDescription = split[2];
				
				switch(itemType.toLowerCase()) {
					case "toy":
						holdings[holdingsCount] = new Toy(itemName, itemDescription, split[8]);
						holdingsCount++; // Increment Holdings Counter
						break;
					
					case "dressup":
						break;
						
					case "playequipment":
						break;
					
					default:
						// Do nothing bad data type, bad record skip it
						break;
				}
				*/
			/*}
			csv.close(); // Close IO on filepath
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
}
