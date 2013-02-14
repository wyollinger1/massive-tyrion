//import java.util.Scanner;

public class Customer {

    protected int ID;
	protected String name;
    protected String password;
	protected String city;
	protected String shoppingCart;
    protected double balance;
    protected String history;
    

    // default constructor
    public Customer()
    {
        ID = 0;
        name = "";
        password = "";
		city = "";
		shoppingCart = "";
        balance = 0.0;
		history = "";
    }
    
    // Initializer constructor
    public Customer(int ID,
             String name,
             String password,
			 String city,
             double balance,
             String shoppingCart,
             String customerHistory)
    {
        this.ID = ID;
        this.name = name;
        this.password = password;
		this.city = city;
        this.balance = balance;
        this.shoppingCart = shoppingCart;
        this.history = customerHistory;
    }
    
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getcity() {
		return city;
	}
	
	public void setcity(String city) {
		this.city = city;
	}
	
	public String getShoppingCart() {
		return shoppingCart;
	}

	public void setShoppingCart(String shoppingCart) {
		this.shoppingCart = shoppingCart;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = history;
	}
    
	//Allows the user to search for a media object
	public void Search(Media mediaObj)
	{
		
	}
	
	//Displays the attributes of the current media object on screen
	public String displayItem(Media mediaObj)
	{
		
		return mediaObj.toString();
	}
	
	//Allows the customer to make a purchase of a media object
	public boolean purchase(Media mediaObj, String type)
	{
		boolean purchased = false;
		
		if(balance > mediaObj.getPrice())
		{
			if(DBIO.remove(mediaObj,type))
			{
				balance = balance - mediaObj.getPrice();
				purchased = true;
				DBIO.updateNumSold(mediaObj,1);
			}
		}
		else
		{
			System.out.println("Not enough money");
		}
		return purchased;
	}
	
	//Allows the user to rate a purchased media object
	public void rateMovie(Media movieObj, double rating)
	{	
		movieObj.addRating(rating);
	}
	public void rateBook(Media bookObj, double rating)
	{	
		bookObj.addRating(rating);
	}
	public void rateAlbum(Media albumObj, double rating)
	{	
		albumObj.addRating(rating);
	}
	
    @Override
    public String toString()
    {
        return String.format("::CUSTOMER INFORMATION::\n"
                    + "Identification #:%20s \n"
                    + "The Customer:%20s \n"
                    + "Password:%20s \n"
					+ "City:%20s \n"
                    + "Balance:%20f \n"
                    + "Shopping Cart:%20s \n"
                    + "History:%20s \n",
                    this.ID, this.name, this.password, this.city,
                    this.balance, this.shoppingCart, this.history);
        
    }
}

