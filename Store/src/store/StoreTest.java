public class StoreTest
{
    public static void main(String[] args)
    {

        Audiobook aud1 = new Audiobook("Josh", "2013", 120, "horror", 0, 30, 0,0); //start inventory
        Movie mov1 = new Movie("Josh", "2013", 120, "horror", 0, 30, 0,0); //start inventory
        Movie mov3 = new Movie("Tyler", "Superbad", 60, "action", 3, 15, 0, 0); //manager add
        Movie[] tempArr; //temporary movie inventory array
        
        
        Audiobook [] aInv = new Audiobook[1];
        Movie [] mInv = new Movie[1];
        mInv[0] = mov1;

        DBIO.setMovieInventory(mInv);
        DBIO.setBookInventory(aInv);

        Customer cust1 = new Customer(1,"Josh","pw",25.00,"Transformers","Spider Man 3");
        Customer cust2 = new Customer(2,"Jared","pw1",35.00,"Batman Begins","Spider Man 2");
        Manager mng1 = new Manager(1,"Tyler","pw2",55.00,"Jurassic Park","The Dark Knight");

        //User info
        System.out.println(cust1.toString());
        System.out.println(cust2.toString());
        System.out.println(mng1.toString());

        cust1.purchase(mov1, "movie"); //Buys
        cust2.purchase(aud1,"book"); //Not enough Credit!!
        
       
        mng1.addMedia(mov3, "movie");
       
        //Get and print new movie inventory
        tempArr = (Movie[])DBIO.query("movie");
        System.out.println(tempArr.length);
        for(int i=0; i<tempArr.length; i++){
        	System.out.println(tempArr[i]);
        }
        
        
        System.out.println("\nThe movie " + mov3.name + " was sold " + Manager.getnumSold(mov3) + " times.");
        
        System.out.println("\n Retreive Customer information: \n" + Manager.getcustInfo(cust1));
    }
}