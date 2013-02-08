
public class StoreTest
{
	public static void main(String[] args) 
	{ 
		
		Audiobook aud1 = new Audiobook("Josh", "2013", 120, "horror", 0, 30, 0,0);
		Movie mov1 = new Movie("Josh", "2013", 120, "horror", 0, 30, 0,0);
		
		Audiobook [] aInv = new Audiobook[1];
		Movie [] mInv = new Movie[1];
		mInv[0] = mov1;
		
		DBIO.setMovieInventory(mInv);
		DBIO.setBookInventory(aInv);
		
		Customer cust1 = new Customer(1,"Josh","pw",25.00,"Transformers","Spider Man 3");
		Customer cust2 = new Customer(2,"Jared","pw1",35.00,"Batman Begins","Spider Man 2");
		Manager mng1 = new Manager(1,"Tyler","pw2",55.00,"Jurassic Park","The Dark Knight");
		
		System.out.println(cust1.toString());
		System.out.println(cust2.toString());
		System.out.println(mng1.toString());
		
		cust1.purchase(mov1, "movie");
		cust2.purchase(aud1,"book");
	}
}