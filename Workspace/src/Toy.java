
public final class Toy extends Item {
	private String itemToyCategory; // Toy Category
	private static String[] categories = new String[] {"construction","ride-on","sport"}; // Categories
	
	public Toy(String itemName, String itemDescription, String itemToyCategory) throws IllegalArgumentException {
		super(itemName, itemDescription, 0.00); // ItemCost is 0.00 as it is unused for this item type
		
		boolean categoryMatch = false;
		
		// Should always be in lower-case
		itemToyCategory = itemToyCategory.toLowerCase();
		
		// Loop though category to see if we have a match
		for (int i = 0; i < categories.length; i++) {
			categoryMatch = categories[i].toLowerCase().contains(itemToyCategory);
				
			// If we get a match break from the loop as their no point in continuing the loop
			if (categoryMatch) {
				this.itemToyCategory = itemToyCategory; // construction, ride-on, sport are the only valid options
				break;
			}
		}

		
		// No Match process exception error message
		if (!categoryMatch) {
			// No match throw the exception
			String acceptableInput = "";
			
			// Generate list of acceptable input options
			for (int x = 0; x < categories.length; x++) {
				acceptableInput += capitalize(categories[x]);
				
				if ((x+1) < categories.length) {
					 acceptableInput += ", ";
				}
			}
			// Throw the exception
			throw new IllegalArgumentException("ERROR: Invalid Toy Category! Valid Options are: " + acceptableInput);
		}
	}
	
	// Method to determine the price of hire
	public double determinePrice() {
		double price = 0.00;
		
		switch (this.itemToyCategory.toLowerCase()) {
			case "construction":
				price = 5.45;
				break;
				
			case "ride-on":
				price = 8.00;
				break;
			
			case "sport":
				price = 6.50;
				break;
		}
		
		return price;
	}
	
	/* 
	 * Override Method to output item information as a list entry when item type is toy
	 * 	Code populates Category > Sub Category & Extra information fields
	 */
	@Override
	public void itemListEntry() {
		String isHired;
		
		// We dont want to output true or false so we are setting isHired
		if (super.getItemHired()) {
			isHired = "Yes";
		} else {
			isHired = "No";
		}
		
		String leftAlignFormat = "| %-3s | %-20s | %-22s | %-13s | %-22s | %-50s | %-11s |%n";
		System.out.format(leftAlignFormat,
						  super.getItemID(),
						  abbreviateString(super.getItemName(), 20), // Abbreviate Item name to fit a max of 20 chars
						  abbreviateString(super.getItemDescription(), 22),
						  printableDouble(this.determinePrice()),
						  abbreviateString(getClass().getName() + " > " + capitalize(this.itemToyCategory),22), // Current Class name + Sub Category + Abbreviate
						  "N/A",
						  isHired);
	}

	/*
	 * Override Method to show details about target item
	 * 	in this method we output produce extra information about the item
	 */
	@Override
	public void itemShow() {
		System.out.println("");
		System.out.println(" Item Name: " + capitalize(super.getItemName()));
		System.out.println(" Description: \n " + super.getItemDescription() + "\n");
		System.out.println(" Item Type:     " +  getClass().getName());
		System.out.println(" Toy Category:  " + capitalize(this.itemToyCategory));
		System.out.println("");
		System.out.println(" Cost Per Week: $" + printableDouble(this.determinePrice()));
		if (super.getItemHired()) {
			System.out.println(" Item Avaliable: Unavaliable");
			System.out.println("------------------------------------");
			System.out.println(" Customer ID: " + super.getCustomerID());
			System.out.println(" Number of Weeks Hired: " + super.getNumWeeks());
			System.out.println("------------------------------------");
			System.out.println("");
		} else {
			System.out.println(" Item Avaliable: Avaliable");
			System.out.println("**************************");
			System.out.println("");
		}
	}
	
	/*
	 * Override Method for hiring an item of type Toy
	 * 	in this method we produce extra information on the receipt
	 *  this method also allows us to use determinePrice()
	 */
	@Override
	public void hireItem(String customerID, int numWeeks) throws HiringException {
		if (!super.getItemHired()) {
			super.setItemHired(true);			// Mark item as hired
			super.setCustomerID(customerID); 	// Update Item with customerID
			super.setNumWeeks(numWeeks);		// Update Item with numWeeks item has been hired
			String hireTimeLine = null;			// Receipt text line for Hire Time
			
			// Hire Time Output
			if (super.getNumWeeks() > 1) {
				hireTimeLine = super.getNumWeeks() + " Weeks @ $" + printableDouble(this.determinePrice()) + " Per Week";
			} else {
				hireTimeLine = super.getNumWeeks() + " Week @ $" + printableDouble(this.determinePrice()) + " Per Week";
			}
			
			System.out.println("");
			System.out.println("+---------------------RECEIPT---------------------+");
			System.out.println("  Customer ID: " + super.getCustomerID());
			System.out.println("  Item Code:   " + super.getItemID());
			System.out.println("  Item Name:   " + capitalize(super.getItemName()));
			System.out.println("  Item Type:   " + getClass().getName()); // Displays current class name
			System.out.println("");
			System.out.println("  Hire Time: " + hireTimeLine);
			System.out.println("  Total Cost: $" + printableDouble(this.determinePrice() * super.getNumWeeks()));
			System.out.println("+------------------------------------------------+");				
		} else {
			// Item has already been hired
			// Throw the exception
			throw new IllegalArgumentException("ERROR: Item [" + super.getItemID() + " - " + capitalize(super.getItemName()) + 
											   "] has already been hired and is unavaliable!");
		}
	}
	
	/*
	 * Override Method for generating a summary report
	 * 	in this method we replace description with Type + Toy Category
	 */
	@Override
	public double itemHireSummary(double totalRevenue) {
		double subTotal = this.determinePrice() * super.getNumWeeks();
		totalRevenue = totalRevenue + subTotal;
		
		String leftAlignFormat = "| %-3s | %-20s | %-22s | %-13s | %-9s | %-11s | %-9s | %-13s |%n";
		System.out.format(leftAlignFormat,
						  super.getItemID(),
						  abbreviateString(super.getItemName(), 20), // Abbreviate Item name to fit a max of 20 chars
						  abbreviateString(getClass().getName() + " > " + capitalize(this.itemToyCategory),22), // Current Class name + Sub Category + Abbreviate
						  printableDouble(super.getItemCost()), 	
						  super.getNumWeeks(),
						  super.getCustomerID(),
						  printableDouble(subTotal),
						  printableDouble(totalRevenue));

		return totalRevenue;
	}

	// Setters and Getters
	public String getItemToyCategory() {
		return itemToyCategory;
	}
	
	public static String[] getItemToyCategories() {
		return categories;
	}
	
}
