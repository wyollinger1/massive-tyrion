

public class Manager extends Customer{
	
	public Manager()
    {
		super();
    }
	public Manager(int ID,
             String name,
             String password,
             double balance,
             String shoppingCart,
             String history)
    {
		super(ID,name,password,balance,shoppingCart,history);
    }
	
	public void addMedia(String mediaObj)
	{
		
	}
	
	public void deleteMedia(String mediaObj)
	{
		
	}
	
	@Override
    public String toString()
    {
        return String.format("::MANAGER INFORMATION::\n"
                    + "    Identification #: %20s \n"
                    + "    The Manager:   	%20s \n"
                    + "    Password:   		%20s \n"
                    + "    Balance:        %20f \n"
                    + "    Shopping Cart:   %20s  \n"
                    + "    History:           %20s \n",
                    this.ID, this.name, this.password,
                    this.balance, this.shoppingCart, this.history);
        
    }
}
