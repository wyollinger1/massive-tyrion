package store;

/**
 * Name: Tyler Madouse, Joshua Thrush, Jared Bean
 * Section: 1
 * Program: Java Store Project
 * Date: 2/15/2013
 * Description: This file holds the customer class which inherits its methods from the user superclass.
 * 
 */

/**
 * The customer class has default and initializer constructors which inherit
 * from the user superclass. Also a toString is provided which returns all
 * customer information. The rest of the methods are inherited from the User
 * superclass
 * 
 * @author Tyler Madouse
 * @author Joshua Thrush
 * @author Jared Bean
 */
public class Customer extends User {

	// Default constructor - gets data from user class
	public Customer() {
		super();
	}

	// Initializer constructor- sets data members through user class
	public Customer(int ID, String name, String password, String city,
			double balance) {
		super(ID, name, password, city, balance);

	}

	@Override
	// toString holding all customer information
	public String toString() {
		return String.format("::CUSTOMER INFORMATION::\n"
				+ "Identification #:%20s \n" + "The Customer:%20s \n"
				+ "Password:%20s \n" + "City:%20s \n" + "Balance:%20f \n"
				+ "Shopping Cart:%20s \n" + "History:%20s \n", this.ID,
				this.name, this.password, this.city, this.balance,
				User.ordersToString(this.shoppingCart), User.ordersToString(this.history));

	}
}
