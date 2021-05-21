import java.util.concurrent.atomic.AtomicInteger;

public class Item {
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
	
	// Method for hiring an item
	public Boolean hireItem(String customerID, int numWeeks) {
		if (!this.itemHired) {
			this.itemHired = true; 			// Mark item as hired
			this.customerID = customerID; 	// Update Item with customerID
			this.numWeeks = numWeeks;		// Update Item with numWeeks item has been hired
			
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
