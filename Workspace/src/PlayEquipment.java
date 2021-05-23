
public final class PlayEquipment extends Item {
	private double itemWeight;	// Item Weight Kilograms
	private double itemHeight;	// Item Height on Centimeters
	private double itemWidth;	// Item Width in Centimeters
	private double itemDepth; 	// Item Depth in Centimeters

	public PlayEquipment(String itemName, String itemDescription, double itemCost, double itemWeight, double itemHeight, double itemWidth, double itemDepth) {
		super(itemName, itemDescription, itemCost);
		
		this.itemWeight = itemWeight;
		this.itemHeight = itemHeight;
		this.itemWidth = itemWidth;
		this.itemDepth = itemDepth;
	}
	
	// Simple Wrapper method to return getItemCost() as determinePrice() as per requirements
	public double determinePrice() {	
		return super.getItemCost();
	}
	
	/* 
	 * Override Method to output item information as a list entry when item type is Dress-Ups
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
						  printableDouble(determinePrice()),
						  "Play Equipment",
						  abbreviateString(printableDouble(this.itemWeight) + "KG, " +
						  				   "H:" + printableDouble(this.itemHeight) + " x W:" + 
						  				   printableDouble(this.itemWidth) + " x D:" + printableDouble(this.itemDepth) + "CM",50),  
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
		System.out.println(" Item Type:       " +  "Play Equipment");
		System.out.println(" Item Weight:     " + printableDouble(itemWeight) + "KG");
		System.out.println(" Item Dimensions: " + printableDouble(this.itemHeight) + "CM x " + 
				   			printableDouble(this.itemWidth) + "CM x " + 
				   			printableDouble(this.itemDepth) + "CM");
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
	 * Override Method for hiring an item of type PlayEquipment
	 * 	in this method we produce extra information on the receipt
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
				hireTimeLine = super.getNumWeeks() + " Weeks @ $" + printableDouble(determinePrice()) + " Per Week";
			} else {
				hireTimeLine = super.getNumWeeks() + " Week @ $" + printableDouble(determinePrice()) + " Per Week";
			}
			
			System.out.println("");
			System.out.println("+---------------------RECEIPT---------------------+");
			System.out.println("  Customer ID:     " + super.getCustomerID());
			System.out.println("  Item Code:       " + super.getItemID());
			System.out.println("  Item Name:       " + capitalize(super.getItemName()));
			System.out.println("  Item Type:       " + "Play Equipment");
			System.out.println("  Item Weight:     " + printableDouble(this.itemWeight) + "KG");
			System.out.println("  Item Dimensions: " + printableDouble(this.itemHeight) + "CM x " + 
							   printableDouble(this.itemWidth) + "CM x " + 
							   printableDouble(this.itemDepth) + "CM");
			System.out.println("");
			System.out.println("  Hire Time: " + hireTimeLine);
			System.out.println("  Total Cost: $" + printableDouble(determinePrice() * super.getNumWeeks()));
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
	 * 	in this method we replace description with Type
	 */
	@Override
	public double itemHireSummary(double totalRevenue) {
		double subTotal = this.determinePrice() * super.getNumWeeks();
		totalRevenue = totalRevenue + subTotal;
		
		String leftAlignFormat = "| %-3s | %-20s | %-22s | %-13s | %-9s | %-15s | %-9s | %-13s |%n";
		System.out.format(leftAlignFormat,
						  super.getItemID(),
						  abbreviateString(super.getItemName(), 20), // Abbreviate Item name to fit a max of 20 chars
						  "Play Equipment",
						  printableDouble(super.getItemCost()), 	
						  super.getNumWeeks(),
						  abbreviateString(super.getCustomerID(), 15),
						  printableDouble(subTotal),
						  printableDouble(totalRevenue));

		return totalRevenue;
	}
	
	// Setters and Getters
	public double getItemWeight() {
		return itemWeight;
	}

	public double getItemHeight() {
		return itemHeight;
	}

	public double getItemWidth() {
		return itemWidth;
	}

	public double getItemDepth() {
		return itemDepth;
	}

	
} 