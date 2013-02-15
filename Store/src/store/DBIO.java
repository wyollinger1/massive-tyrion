/**
 * Name: Jared Bean, Josh Thrush
 * Section: 1
 * Program: Project Phase 1
 * Date: 2/15/2013
 * Description: Interface to database for program
 */
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class contains methods to interface with store database backend.
 * Interfaces with the inventory and sales databases, allowing simple
 * queries (getTotalSales) and adding and removing items.
 * @author jib5153
 *
 */
public class DBIO {
	
	private static Album [] albumInventory; //Album part of the inventory database
	private static Audiobook [] bookInventory; //Audiobook part of inventory database
	private static Movie [] movieInventory; //Movie part of the inventory database
	private static ArrayList<Media> sold = new ArrayList<Media>(); //Media objects reference part of the Sales database, parallel to numSold
	private static ArrayList<Integer> numSold = new ArrayList<Integer>(); //Integer part of the Sales database, parallel to sold
	private final static String [] types = {"movie", "book", "album"}; //Lists the available types for this database, for ease-of-use
	
	/**
	 * Get's the different inventory types.
	 * The types are used as parameters in many of this class's methods.
	 * @return Array of the type names.
	 */
	public static String [] getTypes(){
		return types;
	}
	/**
	 * Sets the database's album inventory
	 * @param inv Album [] to set movie inventory to
	 */
	
	public static void setAlbumInventory(Album [] inv){
		albumInventory = inv;
	}
	/**
	 * Sets the database's book inventory
	 * @param inv AudioBook [] to set movie inventory to
	 */
	
	public static void setBookInventory(Audiobook [] inv){
		bookInventory = inv;
	}
	/**
	 * Sets the database's movie inventory
	 * @param inv Movie [] to set movie inventory to
	 */
	public static void setMovieInventory(Movie [] inv){
		movieInventory = inv;
	}
	
	/**
	 * Queries database.
	 * Currently just returns a list of media objects of the passed type
	 * @param type String denoting the type of media wanted
	 * @return Array of media objects in the inventory
	 */
	public static Media[] query(String type){
		if(type.equals("album")){
			return albumInventory;
		}else if(type.equals("movie")){
			return movieInventory;
		}
		else{
			return bookInventory;
		}
		
	}
	/**
	 * Removes the media object from the inventory.
	 * @param mObj Media object to remove.
	 * @param type	String type of the mObj
	 * @return boolean - true object was successfully removed false otherwise
	 */
	public static boolean remove(Media mObj, String type){
		Media [] inv = query(type);//reference holder for the current inventory
		Media [] newInv;//reference holder for the new inventory
		boolean found=false;
		//Delete by moving to end of Array and copying a copy of length-1 into source
		for(int i=0; i<inv.length; i++){
			if(inv[i]!=null && inv[i].equals(mObj)){
				inv[i]=inv[inv.length-1];
				if(type.equals("album")){
					newInv = new Album[inv.length-1];
					System.arraycopy(inv,0, newInv, 0, inv.length-1);
					albumInventory=(Album[])newInv;
				}else if(type.equals("movie")){
					newInv = new Movie[inv.length-1];
					System.arraycopy(inv,0, newInv, 0, inv.length-1);
					movieInventory=(Movie[])newInv;
				}else if(type.equals("book")){
					newInv = new Audiobook[inv.length-1];
					System.arraycopy(inv,0, newInv, 0, inv.length-1);
					bookInventory=(Audiobook[])newInv;
				}
				found=true;
				break;
			}
		}
		return found;
	}
	/**
	 * Adds mObj to the inventory database.
	 * @param mObj Media object to add to the inventory.
	 * @param type	String type of mObj.
	 * @return boolean - true if the object was successfully added, false otherwise
	 */
	public static boolean add(Media mObj, String type){
		Media [] inv;//reference holder for the current inventory
		Media [] newInv;//reference holder for the new inventory
		//Based on the type make a new array with one more slot 
		//copy the old one into it and add the new mObj at the end
		//type is needed to get the data member reference we want to change 
		if(type.equals("album")){
			inv=albumInventory;
			newInv = new Album[inv.length+1];
			System.arraycopy(inv, 0, newInv, 0, inv.length);
			newInv[inv.length]= mObj;
			albumInventory=(Album[])newInv;
		}else if(type.equals("movie")){
			inv=movieInventory;
			newInv = new Movie[inv.length+1];
			System.arraycopy(inv, 0, newInv, 0, inv.length);
			newInv[inv.length]= mObj;
			movieInventory=(Movie[])newInv;
		}
		else{
			inv=bookInventory;
			newInv = new Audiobook[inv.length+1];
			System.arraycopy(inv, 0, newInv, 0, inv.length);
			newInv[inv.length]= mObj;
			bookInventory=(Audiobook[])newInv;
		}
		return true;
	}
	/**
	 * Updates the sales database with number of a specific media object sold.
	 * @param mObj Media object being updated
	 * @param num Integer number of media objects sold
	 * @return boolean - true if update was successful false otherwise
	 */
	public static boolean updateNumSold(Media mObj, int num){
		Integer index=null;
		if(sold.contains(mObj)){
			index=sold.indexOf(mObj);
			numSold.set(index, num);
		}else{
			sold.add(mObj);
			numSold.add(num);
		}
		return true;
	}
	/**
	 * Gets the number sold for a specific media object
	 * @param mObj Media object to look up
	 * @return Integer number of media objects sold
	 */
	public static int getNumSold(Media mObj){
		if(sold.contains(mObj)){
			return numSold.get(sold.indexOf(mObj));
		}else{
			return 0;
		}
	}
	/**
	 * Gets the total dollar amount of sales so far for the store.
	 * @return A double representing the dollar amount sold.
	 */
	public static double getTotalSales(){
		Iterator<Media> mIter = sold.iterator(); //for the media obj. bit of the Sales database
		Iterator<Integer> numSoldIter = numSold.iterator();//for the integer number sold for each media obj.
		double totalSales=0;//total dollar amount of sales to return
		while(mIter.hasNext()){
			totalSales+= mIter.next().price*numSoldIter.next();
		}
		return totalSales;
	}
}
