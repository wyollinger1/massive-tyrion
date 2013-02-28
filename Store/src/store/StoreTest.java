

public class StoreTest
{
    public static void main(String[] args)
    {
		//Creating Media objects
		Album alb1 = new Album("Josh", "Metallica", 90, "Rock", 0, 15,0 ,0); //start inventory
        Audiobook aud1 = new Audiobook("Josh", "Something", 120, "action", 0, 30, 0,0); //start inventory
        Movie mov1 = new Movie("Josh", "2013", 120, "horror", 0, 30, 0,0); //start inventory
        Movie mov2 = new Movie("Josh", "Something", 120, "comedy", 0, 30, 0,0); //second movie object
		Movie mov3 = new Movie("Tyler", "Superbad", 60, "action", 3, 15, 0, 0); //manager adds third object
        Movie[] tempArr; //temporary movie inventory array
        
		//Creating User objects
		Customer cust1 = new Customer(1,"Josh","pw", "City", 25.00,"Transformers","Spider Man 3");
        Customer cust2 = new Customer(2,"Jared","pw1", "City", 35.00,"Batman Begins","Spider Man 2");
        Manager mng1 = new Manager(1,"Tyler","pw2", "City",55.00,"Jurassic Park","The Dark Knight");
		
		//Creating arrays for the different media types
        Album [] alInv = new Album[1];
        Audiobook [] aInv = new Audiobook[1];
        Movie [] mInv = new Movie[2];
		
		//populating the arrays
        mInv[0] = mov1;
		mInv[1] = mov2;
		aInv[0] = aud1;
		alInv[0] = alb1;

		//Updating database with all media
        DBIO.setMovieInventory(mInv);
        DBIO.setBookInventory(aInv);
		DBIO.setAlbumInventory(alInv);
        
		//Get and print movie inventory
        tempArr = (Movie[])cust1.list("movie");
        System.out.println(tempArr.length);
        for(int i=0; i<tempArr.length; i++){
        	System.out.println(tempArr[i]);
        }
		
		//Manager adds a new movie and deletes an old one
		mng1.addMedia(mov3, "movie");
        mng1.deleteMedia(mov2,"movie");
		
		//Get and print new movie inventory
        tempArr = (Movie[])cust2.list("movie");
        System.out.println(tempArr.length);
        for(int i=0; i<tempArr.length; i++){
        	System.out.println(tempArr[i]);
        }
	
		
		//Display user info
        System.out.println(cust1.toString());
        System.out.println(cust2.toString());
        System.out.println(mng1.toString());
		
		//Customer purchases
        cust2.purchase(mov1, "movie"); //Buys
		cust2.rateMovie(mov1,5.0); //Rates
        cust1.purchase(aud1,"book"); //Not enough Credit!!
       
       //Get and print movie inventory after purchase and rating
        tempArr = (Movie[])cust1.list("movie");
        System.out.println(tempArr.length);
        for(int i=0; i<tempArr.length; i++){
        	System.out.println(tempArr[i].toString());
        }
        
        
        //Manager number of sales test
        System.out.println("\nThe movie " + mov3.getName() + " was sold " + mng1.getnumSold(mov3) + " time(s).");
        System.out.println("\nThe movie " + mov2.getName() + " was sold " + mng1.getnumSold(mov2) + " time(s).");
        System.out.println("\nThe movie " + mov1.getName() + " was sold " + mng1.getnumSold(mov1) + " time(s).");
        
        //Manager total sales
        System.out.printf("\nTotal Sales: %.2f", mng1.getTotalSales());
        assert mng1.getTotalSales()==75.0 : "That's not right!";
        
		//Manager gets customer info test
        System.out.println("\n Retreive Customer information: \n" + mng1.getcustInfo(cust1));
		
		
    }
}