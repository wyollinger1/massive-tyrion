import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.DatabaseMetaData;
import java.util.Iterator;
import java.util.Map;
/**
 * Uses arrays to hold inventory.
 * Transition to SQL imminent.
 * @author jib5153
 *
 */
public class DBIO {
	private static final String driverName = "org.sqlite.JDBC"; //May let driver be changed in future
	private static final String jdbcUrlPre = "jdbc:sqlite:"; //Prefix for all SQLite JDBC url's
	private static Connection con;
	private static Statement stmnt;
	private static Album [] albumInventory;
	private static Audiobook [] bookInventory;
	private static Movie [] movieInventory;
	
	/**
	 * Initializes static class with the SQLite JDBC driver.
	 * TODO Docs claim Class.forName() is no longer necessary ... 
	 * 		which is all this init does
	 * @throws ClassNotFoundException
	 */
	public static void init() throws ClassNotFoundException{
		Class.forName(driverName);
	}
	
	/**
	 * Set the current working database.
	 * 
	 * @param dbUrl String url of database.
	 * @return true on successful connection false otherwise
	 */
	public static boolean setDb(String dbUrl){
		try{
			con = DriverManager.getConnection(jdbcUrlPre+dbUrl);
			try{
				stmnt = con.createStatement();
			}catch(SQLException sqlE){
				stmnt=null;
				con.close();
				throw sqlE;
			}
			return true;
		}catch(SQLException sqlE){
			con = null; //Probably not necessary
			return false;
		}
	}
	
	/**
	 * Removes the num number of media object from the inventory.
	 * @param mObj Media object to remove.
	 * @param num integer number of media objects to remove
	 * @return integer number of rows updated, -1 on error
	 */
	public static int remove(int mId, int num){
		int isSuccess=0;
		try{
			isSuccess=stmnt.executeUpdate("UPDATE Inventory SET numInStock = numInStock-"+num+" WHERE mId="+mId+" AND numInStock>0");
		}catch(SQLException sqlE){
			return -1;
		}
		return isSuccess;
	}
	/**
	 * Adds num number of media objects with id mId to the inventory.
	 * 
	 * @param mId integer id of the media object
	 * @param num integer number of media objects to add
	 * @return integer number of rows updated, -1 on error
	 */
	public static int add(int mId, int num){
		int isSuccess=0;
		try{
			isSuccess=stmnt.executeUpdate("UPDATE Inventory SET numInStock = numInStock"+num+" WHERE mId="+mId);
		}catch(SQLException sqlE){
			return -1;
		}
		return isSuccess;
	}
	/**
	 * Manager method to update/manipulate item's descriptive data.
	 * Note: ratings data and number in stock have separate manipulation
	 * functions.
	 * 
	 * @param mObj Media object containing new data to set for it's mId
	 * @return integer number of rows manipulated, -1 on error
	 */
	public static int update(int mId, Media mObj){
		int isSuccess=0;
		try{
			isSuccess=stmnt.executeUpdate(
		"UPDATE Inventory SET creator="+mObj.getCreator()+", "+
		"name="+mObj.getName()+", duration="+mObj.getDuration()+", genre="+ mObj.getGenre()+ " "+
		"WHERE mId="+mId
			);
		}catch(SQLException sqlE){
			return -1;
		}
		return isSuccess;
	}
	/**
	 * Updates rating average with new rating
	 * @param mObj Media object being rated
	 * @param rating rating being added of type double
	 * @return integer number of rows manipulated, -1 on error
	 */
	public static int updateRating(Media mObj, double rating){
		int mId=mObj.getId(); //TODO Media object needs an id field and a getter for that field
		int isSuccess=0;
		try{
			isSuccess=stmnt.executeUpdate(
		"UPDATE Inventory SET"+
		"avgRating=(avgRating*numRating +" + rating +")/(numRating+1), "+
		"numRating=numRating+1" + 
		"WHERE mId="+mId
			);
			refreshMedia(mObj); //TODO make a refreshMedia, probably based on query
		}catch(SQLException sqlE){
			return -1;
		}
		return isSuccess;
	}
	/**
	 * Builds a select query from a map of column name strings to search parameter strings
	 * TODO make searchOptions be able to hold operator as well
	 * @param searchOptions
	 * @return array of media objects gotten from db query
	 */
	public Media [] query(Map<String, String> searchOptions){
		/*Using an array since need to loop over twice with same keySet
		 *grabbing two iterators risks seeing a change in the underlying set
		 *and the array is not backed by the set so no risk there
		 */
		String keyArr[] = (String[])searchOptions.keySet().toArray();
		String prepareString= "SELECT * WHERE ";
		ResultSet results;
		for(int i=0; i<keyArr.length; i++){
			if(i!=0){
				prepareString+=", ";
			}
			prepareString+="keyArr=?";
		}
		
		try {
			PreparedStatement pStmnt = con.prepareStatement(prepareString);
			//TODO figure out how to make this query builder work
		} catch (SQLException e) {
			
		}
		return null;
	}
}
