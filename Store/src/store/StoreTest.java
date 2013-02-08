
public class StoreTest
{
	public static void main(String[] args) 
	{ 
		int ID;
		String name;
        String password;
        double balance;
        String shoppingCart;
        String history;
		
		Customer cust1 = new Customer(1,"Josh","pw",25.00,"Transformers","Spider Man 3");
		Customer cust2 = new Customer(2,"Jared","pw1",35.00,"Batman Begins","Spider Man 2");
		Manager mng1 = new Manager(1,"Tyler","pw2",55.00,"Jurassic Park","The Dark Knight");
		System.out.println(cust1.toString());
		System.out.println(cust2.toString());
		System.out.println(mng1.toString());
	}
}