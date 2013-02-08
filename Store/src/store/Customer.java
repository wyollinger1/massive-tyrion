package Store;

public class Customer {

    protected int ID;
	protected String name;
    protected String password;
	protected String shoppingCart;
    protected double balance;
    protected String history;

    // default constructor
    public Customer()
    {
        ID = 0;
        name = "";
        password = "";
		shoppingCart = "";
        balance = 0.0;
		history = "";
    }
    
    // Initializer constructor
    public Customer(int ID,
             String name,
             String password,
             double balance,
             String shoppingCart,
             String customerHistory)
    {
        this.ID = ID;
        this.name = name;
        this.password = password;
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
    
	public void Search(String mediaObj)
	{
		
	}
	
	public String displayItem(String mediaObj)
	{
		return mediaObj;
	}
	
	public void purchase(String mediaObj)
	{
		
	}
	
	public void rate(String mediaObj)
	{
		
	}
	
	
    @Override
    public String toString()
    {
        return String.format("::CUSTOMER INFORMATION::\n"
                    + "    Identification #: %20s \n"
                    + "    The Customer:   	%20s \n"
                    + "    Password:   		%20s \n"
                    + "    Balance:        %20f \n"
                    + "	   Shopping Cart:	%20s \n"
                    + "    History:           %20s \n",
                    this.ID, this.name, this.password,
                    this.balance, this.shoppingCart, this.history);
        
    }
}

