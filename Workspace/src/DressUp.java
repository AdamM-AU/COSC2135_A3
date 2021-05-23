
public final class DressUp extends Item {
	private String itemSize;	// Item Size
	private String itemGenre;	// Item Genre
	private int itemPieceCount; // Item Piece Count

	public DressUp(String itemName, String itemDescription, String itemSize, String itemGenre, int itemPieceCount) {
		super(itemName, itemDescription, 0.00); // ItemCost is 0.00 as it is unused for this item type
		
		this.itemSize = itemSize;
		this.itemGenre = itemGenre;
		this.itemPieceCount = itemPieceCount;

	}
	
	// Method to determine the price of hire
	public double determinePrice() {
		double laundryFee = 3.00; 	// Laundry Fee 
		double price = 3.50;		// Setting base price
		
		price = (this.itemPieceCount * price) + laundryFee; 
		
		return price;
	}
	
	/* 
	 * Override Method to output item information as a list entry when item type is Dress-ups
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
						  abbreviateString("Dress-Ups" + " > " +capitalize(this.itemGenre),22), // Current Class name + Sub Category + Abbreviate
						  abbreviateString("Size: " + this.itemSize + ", Piece/s: " + this.itemPieceCount,50),
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
		System.out.println(" Item Type:      " +  "Dress-Ups");
		System.out.println(" Item Category:  " + capitalize(this.itemGenre));
		System.out.println(" Item Size:      " + this.itemSize);
		System.out.println(" Item Peice/s:   " + this.itemPieceCount);
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
	 * Override Method for hiring an item of type DressUp
	 * 	in this method we produce extra information on the receipt
	 *  this method also allows us to use determinePrice()
	 */
	@Override
	public void hireItem(String customerID, int numWeeks) throws HiringException{
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
			System.out.println("  Customer ID:  " + super.getCustomerID());
			System.out.println("  Item Code:    " + super.getItemID());
			System.out.println("  Item Name:    " + capitalize(super.getItemName()));
			System.out.println("  Item Type:    " + "Dress-Ups");
			System.out.println("  Item Size:    " + this.itemSize);
			System.out.println("  Item Piece/s: " + this.itemPieceCount);
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
	 * 	in this method we replace description with Type + Dress Ups Genre
	 */
	@Override
	public double itemHireSummary(double totalRevenue) {
		double subTotal = this.determinePrice() * super.getNumWeeks();
		totalRevenue = totalRevenue + subTotal;
		
		String leftAlignFormat = "| %-3s | %-20s | %-22s | %-13s | %-9s | %-11s | %-9s | %-13s |%n";
		System.out.format(leftAlignFormat,
						  super.getItemID(),
						  abbreviateString(super.getItemName(), 20), // Abbreviate Item name to fit a max of 20 chars
						  abbreviateString("Dress-Ups > " + capitalize(this.itemGenre),22), // Item Type + Sub Category (Genre) + Abbreviate
						  printableDouble(super.getItemCost()), 	
						  super.getNumWeeks(),
						  super.getCustomerID(),
						  printableDouble(subTotal),
						  printableDouble(totalRevenue));

		return totalRevenue;
	}

}
