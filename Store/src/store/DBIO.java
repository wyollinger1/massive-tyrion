import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

/**
 * SQL implementation.  Abstracts queries away so all database calls are made through this API.
 * 
 * @author jib5153
 * 
 */
public class DBIO {
	private static final String driverName = "org.sqlite.JDBC"; // May let
																// driver be
																// changed in
																// future
	private static final String jdbcUrlPre = "jdbc:sqlite:"; // Prefix for all
																// SQLite JDBC
																// url's
	private static Connection con;
	private static Statement stmnt;

	/**
	 * Initializes static class with the SQLite JDBC driver. TODO Docs claim
	 * Class.forName() is no longer necessary ... which is all this init does
	 * 
	 * @throws ClassNotFoundException
	 */
	public static void init() throws ClassNotFoundException {
		Class.forName(driverName);
	}

	/**
	 * Set the current working database.
	 * 
	 * @param dbUrl
	 *            String url of database.
	 * @return true on successful connection false otherwise
	 */
	public static boolean setDb(String dbUrl) {
		try {
			con = DriverManager.getConnection(jdbcUrlPre + dbUrl);
			try {
				stmnt = con.createStatement();
			} catch (SQLException sqlE) {
				stmnt = null;
				con.close();
				throw sqlE;
			}
			return true;
		} catch (SQLException sqlE) {
			con = null; // Probably not necessary
			return false;
		}
	}

	/**
	 * Removes the num number of media object from the inventory.
	 * 
	 * @param mObj
	 *            Media object to remove.
	 * @param num
	 *            integer number of media objects to remove
	 * @return integer number of rows updated, -1 on error
	 */
	public static int remove(int mId, int num) {
		int isSuccess = 0;
		try {
			isSuccess = stmnt
					.executeUpdate("UPDATE Inventory SET numInStock = numInStock-"
							+ num + " WHERE mId=" + mId + " AND numInStock>0");
		} catch (SQLException sqlE) {
			return -1;
		}
		return isSuccess;
	}

	/**
	 * Adds num number of media objects with id mId to the inventory.
	 * 
	 * @param mId
	 *            integer id of the media object
	 * @param num
	 *            integer number of media objects to add
	 * @return integer number of rows updated, -1 on error
	 */
	public static int add(int mId, int num) {
		int isSuccess = 0;
		try {
			isSuccess = stmnt
					.executeUpdate("UPDATE Inventory SET numInStock = numInStock"
							+ num + " WHERE mId=" + mId);
		} catch (SQLException sqlE) {
			return -1;
		}
		return isSuccess;
	}

	/**
	 * Manager method to update/manipulate item's descriptive data. Note:
	 * ratings data and number in stock have separate manipulation functions.
	 * 
	 * @param mObj
	 *            Media object containing new data to set for it's mId
	 * @return integer number of rows manipulated, -1 on error
	 */
	public static int update(int mId, Media mObj) {
		int isSuccess = 0;
		try {
			isSuccess = stmnt.executeUpdate("UPDATE Inventory SET creator="
					+ mObj.getCreator() + ", " + "name=" + mObj.getName()
					+ ", duration=" + mObj.getDuration() + ", genre="
					+ mObj.getGenre() + " " + "WHERE mId=" + mId);
		} catch (SQLException sqlE) {
			return -1;
		}
		return isSuccess;
	}

	/**
	 * Updates rating average with new rating
	 * 
	 * @param mObj
	 *            Media object being rated
	 * @param rating
	 *            rating being added of type double
	 * @return integer number of rows manipulated, -1 on error
	 */
	public static int updateRating(Media mObj, double rating) {
		int mId = mObj.getId(); // TODO Media object needs an id field and a
								// getter for that field
		int isSuccess = 0;
		try {
			isSuccess = stmnt.executeUpdate("UPDATE Inventory SET"
					+ "avgRating=(avgRating*numRating +" + rating
					+ ")/(numRating+1), " + "numRating=numRating+1"
					+ "WHERE mId=" + mId);
			refreshMedia(mObj); 
		} catch (SQLException sqlE) {
			return -1;
		}
		return isSuccess;
	}
	/**
	 * Refreshes/resets media object with data in database
	 * @param mObj Media object to refresh
	 * @return the refreshed media object
	 */
	private static Media refreshMedia(Media mObj){
		String [] cols = {"*"};
		Integer [] condArr = {mObj.getId()};//TODO: Media needs and id field
		ResultSet results;
		Media [] mediaArr;
		SelectBuilder sb = getSelectBuilder(cols, "Inventory");
		sb.addIntCondition("mId", "=", condArr, true);
		results=executeQuery(sb);
		mObj = result2Media(results)[0]; //Counting on finding exactly one row, probably should add some checks or something.
		return mObj; 
		
	}

	/**
	 * Turns a ResultSet from the Inventory Table into a Media array, which is what
	 * all Media results should be returned as.
	 * @param results ResultSet to process for Media objects
	 * @return Array of media objects extracted from the ResultSet
	 * @throws SQLException if error reading from the ResultSet
	 */
	private static Media[] result2Media(ResultSet results) throws SQLException{
		int mId, duration, numSold, numRating; //TODO: Media class needs an id data member
		double price, avgRating;
		String creator, name, genre;
		ArrayList<Media> mediaObjs = new ArrayList<Media>();		
		do{
			mId=results.getInt("mId");
			creator=results.getString("creator");
			name=results.getString("name");
			duration=results.getInt("duration");
			genre=results.getString("genre");
			numSold=results.getInt("numInStock"); //TODO: This is just plain wrong
			price=results.getDouble("price");
			numRating=results.getInt("numRating");
			avgRating=results.getDouble("avgRating");
			mediaObjs.add(new Media(creator, name, duration, genre, numSold, price, numRating, avgRating)); //TODO Inventory should have type column to switch on so can create specific media not generic
		}
		while(results.next());
		return (Media[])mediaObjs.toArray();
	}
	
	/**
	 * Gets a SelectBuilder object to build a Select query to execute on the database.
	 * 
	 * @param columnArr String array of columns to select, must be non-null, can contain solitary item, i.e. ["*"]
	 * @param tableName String name of table to select columns from
	 * @return SelectBuilder initialized with the columns and table, allowing to build a Where clause
	 */
	public static SelectBuilder getSelectBuilder(String[] columnArr, String tableName){
		return new SelectBuilder(columnArr, tableName);
	}
	
	/**
	 * Executes the built Select statement on the database and returns the ResultSet.
	 * 
	 * @param sb SelectBuilder containing the completed Select statement
	 * @return ResultSet containing the results returned by the database
	 * @throws SQLException on database access error
	 */
	public static ResultSet executeQuery(SelectBuilder sb) throws SQLException{ //TODO Take care of this exception either here or in the QueryBuilder class
		return sb.executeSelect(con);
	}
}
