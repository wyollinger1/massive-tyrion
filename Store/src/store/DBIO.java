import java.util.ArrayList;

/**
 * Uses arrays to hold inventory.
 * Transition to SQL imenent.
 * @author jib5153
 *
 */
public class DBIO {
	
	private static Album [] albumInventory;
	private static Audiobook [] bookInventory;
	private static Movie [] movieInventory;
	private static ArrayList<Media> sold;
	private static ArrayList<Integer> numSold;
	
	DBIO(String dirName){
		
	}
	
	/**
	 * Set album inventory
	 * @param inv Album [] to set movie inventory to
	 */
	
	public static void setAlbumInventory(Album [] inv){
		albumInventory = inv;
	}
	/**
	 * Set book inventory
	 * @param inv AudioBook [] to set movie inventory to
	 */
	
	public static void setBookInventory(Audiobook [] inv){
		bookInventory = inv;
	}
	/**
	 * Set movie inventory
	 * @param inv Movie [] to set movie inventory to
	 */
	public static void setMovieInventory(Movie [] inv){
		movieInventory = inv;
	}
	
	/**
	 * Queries database.
	 * Currently just returns a list of media objects of the passed type
	 * @param type String denoting the type of media wanted
	 * @return
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
	 * @return
	 */
	public static boolean remove(Media mObj, String type){
		Media [] inv = query(type);
		Media [] newInv;
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
	 * Adds mObj to the inventory.
	 * @param mObj Media object to add to the inventory.
	 * @param type	String type of mObj.
	 * @return
	 */
	public static boolean add(Media mObj, String type){
		Media [] inv;
		Media [] newInv;
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
	public static int getNumSold(Media mObj){
		if(sold.contains(mObj)){
			return numSold.get(sold.indexOf(mObj));
		}else{
			return 0;
		}
	}
}
