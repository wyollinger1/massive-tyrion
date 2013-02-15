//import java.util.Scanner;

public class Customer extends User {

    public Customer(){
    	super();
    }
    public Customer(int ID,
            String name,
            String password,
			 String city,
            double balance,
            String shoppingCart,
            String customerHistory){
    	super(ID, name, password, city, balance, shoppingCart, customerHistory);
    	
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

