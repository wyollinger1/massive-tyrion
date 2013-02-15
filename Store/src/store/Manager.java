

public class Manager extends User
{
    //Constructor calls super class to default construct manager
    public Manager()
    {
        super();
    }

    public Manager(int ID,
             String name,
             String password,
			 String city,
             double balance,
             String shoppingCart,
             String history)
    {
        super(ID,name,password,city,balance,shoppingCart,history);
    }
    
    //Adds media to store
    //Takes in a Media object: mediaObj
    //and the type of media as a String
    public void addMedia(Media mediaObj, String type)
    {
           DBIO.add(mediaObj, type);
    }
    //Removes media from store
    //Takes in Media object: mediaObj
    //and type of media as a String
    public void deleteMedia(Media mediaObj, String type)
    {
           DBIO.remove(mediaObj,type);
    }
    
    // Retrieve all information of any customer
    public String getcustInfo(Customer cust)
    {
        
        return cust.toString();         
    }
    //Takes in media object
    //will return the number sold
    public int getnumSold(Media mediaObj) 
    {
        return mediaObj.getNumSold();
    }
    public double getTotalSales(){
    	return DBIO.getTotalSales();    	
    }
    @Override
    public String toString()
    {
        return String.format("::MANAGER INFORMATION::\n"
                    + " Identification #: %20s \n"
                    + " The Manager: %20s \n"
                    + " Password: %20s \n"
					+ " City:	  %20s \n"
                    + " Balance: %20f \n"
                    + " Shopping Cart: %20s \n"
                    + " History: %20s \n",
                    this.ID, this.name, this.password, this.city,
                    this.balance, this.shoppingCart, this.history);
        
    }
}