public class StoreTest
{
    public static void main(String[] args)
    {
		//Creating Media objects
		Album alb1 = new Album("Josh", "Metallica", 90, "Rock", 0, 15,0 ,0); //start inventory
        Audiobook aud1 = new Audiobook("Josh", "Something", 120, "action", 0, 30, 0,0); //start inventory
        Movie mov1 = new Movie("Josh", "2013", 120, "horror", 0, 30, 0,0); //start inventory
        Movie mov2 = new Movie("Tyler", "Superbad", 60, "action", 3, 15, 0, 0); //manager add
        Movie[] tempArr; //temporary movie inventory array
        
		//Creating User objects
		Customer cust1 = new Customer(1,"Josh","pw", "Street", 25.00,"Transformers","Spider Man 3");
        Customer cust2 = new Customer(2,"Jared","pw1", "Street", 35.00,"Batman Begins","Spider Man 2");
        Manager mng1 = new Manager(1,"Tyler","pw2", "Street",55.00,"Jurassic Park","The Dark Knight");
		
		//Creating arrays for the differnt media types
        Album [] alInv = new Album[1];
        Audiobook [] aInv = new Audiobook[1];
        Movie [] mInv = new Movie[1];
		
		//populating the arrays
        mInv[0] = mov1;
		aInv[0] = aud1;
		alInv[0] = alb1;

		//Updating database with all media
        DBIO.setMovieInventory(mInv);
        DBIO.setBookInventory(aInv);
		DBIO.setAlbumInventory(alInv);
        
		//Get and print movie inventory
        tempArr = (Movie[])DBIO.query("movie");
        System.out.println(tempArr.length);
        for(int i=0; i<tempArr.length; i++){
        	System.out.println(tempArr[i]);
        }
		
		//Manager adds a new movie
		mng1.addMedia(mov2, "movie");
        
		
		//Get and print new movie inventory
        tempArr = (Movie[])DBIO.query("movie");
        System.out.println(tempArr.length);
        for(int i=0; i<tempArr.length; i++){
        	System.out.println(tempArr[i]);
        }
		
		
		//Display user info
        System.out.println(cust1.toString());
        System.out.println(cust2.toString());
        System.out.println(mng1.toString());
		
		//Customer purchases
        cust1.purchase(mov1, "movie"); //Buys
        cust2.purchase(aud1,"book"); //Not enough Credit!!
        
       //Get and print movie inventory after purchase
        tempArr = (Movie[])DBIO.query("movie");
        System.out.println(tempArr.length);
        for(int i=0; i<tempArr.length; i++){
        	System.out.println(tempArr[i]);
        }
        
        
        //Manager number of sales test
        System.out.println("\nThe movie " + mov2.name + " was sold " + Manager.getnumSold(mov2) + " times.");
        
		//Manager gets customer info test
        System.out.println("\n Retreive Customer information: \n" + Manager.getcustInfo(cust1));
    }
}