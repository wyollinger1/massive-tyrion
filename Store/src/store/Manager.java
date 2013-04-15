package store;

/**
 * Name: Tyler Madouse, Joshua Thrush, Jared Bean
 * Section: 1
 * Program: Java Store Project
 * Date: 2/15/2013
 * Description: This file holds the manager class which inherits from user and has methods which let a manager see
 * customer information, see total sales, or see sales for each item.
 * 
 */

/**
 * The manager class has default and initializer constructors which inherit from
 * the user superclass. The class also contains methods for adding media,
 * removing media, getting customer info, getting sales for an item, and getting
 * the total sales for the media store. The rest of the methods are inherited
 * from the user superclass
 * 
 * @author Tyler Madouse
 * @author Joshua Thrush
 * @author Jared Bean
 */
public class Manager extends User {
	// Constructor calls super class to default construct manager
	public Manager() {
		super();
	}

	public Manager(int ID, String name, String password, String city,
			double balance) {
		super(ID, name, password, city, balance);
	}

	// Adds media to store
	// Takes in a Media object: mediaObj
	// and the number to add
	public Media addMedia(Media mediaObj, int num) {
		DBIO.Types type;
		if (mediaObj instanceof Album) {
			type = DBIO.Types.ALBUM;
		} else if (mediaObj instanceof Audiobook) {
			type = DBIO.Types.AUDIOBOOK;
		} else if (mediaObj instanceof Movie) {
			type = DBIO.Types.MOVIE;
		} else {
			type = DBIO.Types.ALBUM;
		}
		return DBIO.add(mediaObj, type, num);
	}

	// Removes media from store
	// Takes in Media object: mediaObj
	// and number of media objects to remove
	public void deleteMedia(Media mediaObj, int num) {
		DBIO.remove(mediaObj.getId(), num);
	}

	// Retrieve all information of any customer
	public String getcustInfo(Customer cust) {

		return cust.toString();
	}

	// Takes in media object
	// will return the number sold
	public int getnumSold(Media mediaObj) {
		return mediaObj.getNumSold();
	}

	// Allows manager to get the total number of sales for the media store
	public double getTotalSales() {
		return DBIO.getTotalSales();
	}

	@Override
	// toString holding all customer information
	public String toString() {
		return String.format("::MANAGER INFORMATION::\n"
				+ " Identification #: %20s \n" + " The Manager: %20s \n"
				+ " Password: %20s \n" + " City:	  %20s \n"
				+ " Balance: %20f \n" + " Shopping Cart: %20s \n"
				+ " History: %20s \n", this.ID, this.name, this.password,
				this.city, this.balance, this.shoppingCart, this.history);

	}
}