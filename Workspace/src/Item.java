import java.util.concurrent.atomic.AtomicInteger;

public abstract class Item {
	private static final AtomicInteger count = new AtomicInteger(99);  //see: https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/atomic/AtomicInteger.html
	private int itemID; 				// Item ID
	private String itemName;			// Item Name
	private String itemDescription;		// Item Description
	private double itemCost;			// Item Cost per week
	private Boolean itemHired;			// Item is hired True, Item is available False
	private String customerID;			// ID of customer hiring the item 
	private int numWeeks;				// Number of weeks the item has been hired
	
	
	public Item(String itemName, String itemDescription, double itemCost) {
		this.itemID = count.incrementAndGet(); // Special datatype, so ID remains the same after deletions
		this.itemName = itemName;
		this.itemDescription = itemDescription;
		this.itemCost = itemCost;
		this.itemHired = false; // All new items are not hired
		this.customerID = "";
		this.numWeeks = 0;
	}

	/* 
	 * printableDouble()
	 * Method converts a double (0.00000000) to a 2 decimal place printable string
	 * We do not store this, we only store the full double to be more accurate
	 */
	public static String printableDouble(double input) {
		String result;
		result = new java.text.DecimalFormat("0.00").format(input);
		
		return result;
	}
	
	/* 
	 * Taken from: https://stackoverflow.com/questions/3904579/how-to-capitalize-the-first-letter-of-a-string-in-java
	 * Capitalizes first letter of string
	 * Why? I was to lazy to write it myself
	 */
	public static String capitalize(String str)	{
	    if(str == null) return str;
	    return str.substring(0, 1).toUpperCase() + str.substring(1);
	}
	
	/*
	 * Method for abbreviating Strings
	 * quickly slapped together but works
	 * 
	 * Require 2 Variables
	 * String Input = text to be abbreviated
	 * int Length = max length required
	 */
	public static String abbreviateString(String input, int length) {
		String output;
		
		if (input.length() > length) {
			int actullLength = length - 3; // We need to add 3 ... to the end
			
			output = input.substring(0, Math.min(input.length(), actullLength));
			output = output + "...";
		} else {
			output = input;
		}
		
		return output;
	}
	
	// Method to output item information as a list entry
	public void itemListEntry() {
		// Temp Variables
		String tmpNumWeeks;
		String tmpCustomerID;
		
		if (!this.itemHired) {
			tmpNumWeeks = "N/A";
			tmpCustomerID = "N/A";
		} else {
			tmpNumWeeks = String.valueOf(this.numWeeks);
			tmpCustomerID = this.customerID;
		}
		
		
		String leftAlignFormat = "| %-3s | %-20s | %-22s | %-13s | %-9s | %-9s | %-12s |%n";
		System.out.format(leftAlignFormat,
						  this.itemID,
						  abbreviateString(this.itemName, 20), // Abbreviate Item name to fit a max of 20 chars
						  abbreviateString(this.itemDescription, 22),
						  printableDouble(this.itemCost), 
						  this.itemHired, 
						  tmpNumWeeks,
						  tmpCustomerID);

	}
	
	// Method to show details about target item
	public void itemShow() {
		System.out.println("");
		System.out.println(" Item Name: " + capitalize(this.itemName));
		System.out.println(" Description: \n" + this.itemDescription + "\n");
		System.out.println(" Cost Per Week: $" + printableDouble(this.getItemCost()));
		if (this.itemHired) {
			System.out.println(" Item Avaliable: Unavaliable");
			System.out.println("------------------------------------");
			System.out.println(" Customer ID: " + this.customerID);
			System.out.println(" Number of Weeks Hired: " + this.numWeeks);
			System.out.println("------------------------------------");
			System.out.println("");
		} else {
			System.out.println(" Item Avaliable: Avaliable");
			System.out.println("");
		}
	}
	
	// Method for hiring an item
	public Boolean hireItem(String customerID, int numWeeks) {
		if (!this.itemHired) {
			this.itemHired = true; 			// Mark item as hired
			this.customerID = customerID; 	// Update Item with customerID
			this.numWeeks = numWeeks;		// Update Item with numWeeks item has been hired
			String hireTimeLine = null;		// Receipt text line for Hire Time
			
			// Hire Time Output
			if (this.numWeeks > 1) {
				hireTimeLine = this.numWeeks + " Weeks @ $" + printableDouble(this.itemCost) + " Per Week";
			} else {
				hireTimeLine = this.numWeeks + " Week @ $" + printableDouble(this.itemCost) + " Per Week";
			}
			
			System.out.println("");
			System.out.println("+---------------------RECEIPT---------------------+");
			System.out.println("  Customer ID: " + this.customerID);
			System.out.println("  Item Code: " + this.itemID);
			System.out.println("  Item Name: " + capitalize(this.itemName));
			System.out.println("");
			System.out.println("  Hire Time: " + hireTimeLine);
			System.out.println("  Total Cost: $" + printableDouble(this.itemCost * this.numWeeks));
			System.out.println("+------------------------------------------------+");				
			
			return true;
		} else {
			// Item has already been hired
			return false;
		}
	}
	
	// Method for returning an item
	public Boolean returnItem() {
		if (this.itemHired) {
			this.itemHired = false;	// Mark item as available
			this.customerID = ""; 	// Remove customerID
			this.numWeeks = 0;		// set numWeeks to 0
			return true;
		} else {
			return false;
		}
	}
	
	// Method for generating a summary report
	public double itemHireSummary(double totalRevenue) {
		double subTotal = this.itemCost * this.numWeeks;
		totalRevenue = totalRevenue + subTotal;
		
		String leftAlignFormat = "| %-3s | %-20s | %-22s | %-13s | %-9s | %-11s | %-9s | %-13s |%n";
		System.out.format(leftAlignFormat,
						  this.itemID,
						  abbreviateString(this.itemName, 20), // Abbreviate Item name to fit a max of 20 chars
						  abbreviateString(this.itemDescription, 22),
						  printableDouble(this.itemCost), 	
						  this.numWeeks,
						  this.customerID,
						  printableDouble(subTotal),
						  printableDouble(totalRevenue));

		return totalRevenue;
	}
	
	// Setters and Getters
	
	public int getItemID() {
		return itemID;
	}

	public void setItemID(int itemID) {
		this.itemID = itemID;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public double getItemCost() {
		return itemCost;
	}

	public void setItemCost(double itemCost) {
		this.itemCost = itemCost;
	}

	public Boolean getItemHired() {
		return itemHired;
	}

	public void setItemHired(Boolean itemHired) {
		this.itemHired = itemHired;
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public int getNumWeeks() {
		return numWeeks;
	}

	public void setNumWeeks(int numWeeks) {
		this.numWeeks = numWeeks;
	}

	public static AtomicInteger getCount() {
		return count;
	}
}
