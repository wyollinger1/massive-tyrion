package store;
/**
 * Name: Jared Bean, Josh Thrush
 * Section: 1
 * Program: Project Phase 1
 * Date: 2/15/2013
 * Description: Interface to database for program
 */
import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Class contains methods to interface with store database backend. Interfaces
 * with the inventory and sales databases, allowing simple queries
 * (getTotalSales) and adding and removing items. SQL implementation. Abstracts
 * queries away so all database calls are made through this API.
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
	 * @returns true on success and false otherwise
	 */
	public static boolean init() {
		try{
			Class.forName(driverName);
		}catch(ClassNotFoundException ignore){
			return false;
		}
		return true;
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
	public static boolean isConnected(){
		try{
		if(con!=null){
			if(!con.isClosed()){
				return true;
			}
			return false;
		}else{
			return false;
		}
		}catch(SQLException e){
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
		String upExist="UPDATE Inventory SET numInStock =(SELECT numInStock FROM Inventory WHERE mId=?)+? WHERE mId=?";
		PreparedStatement upInv = null;
		try {
			upInv=con.prepareStatement(upExist);
			upInv.setInt(1, mId);
			upInv.setInt(2, num);
			upInv.setInt(3, mId);
			upInv.executeUpdate();
		} catch (SQLException sqlE) {
			isSuccess=-1;
		}finally{
			//Try to close the statement but ignore any exceptions
			if(upInv!=null)try{upInv.close();}catch(SQLException ignore){} 
		}
		return isSuccess;
	}
	/**
	 * Either adds a number of existing media objects to the number in stock or
	 * inserts a new media object into the inventory and refreshes the media object 
	 * with its new id number.
	 * @param mObj Media object to add, id of zero if new
	 * @param type Type of object to add 
	 * @param num Number to add to inventory
	 * @return refreshed media object or null on error
	 */
	public static Media add(Media mObj, String type, int num){
		String insMedia = "INSERT INTO Inventory (creator, name, duration, genre, numInStock, price, type)"
				+ "VALUES(?, ?, ?, ?, ?, ?, ?)";
		String selMid = "SELECT mId FROM Inventory WHERE creator=? AND name=? AND duration=? AND price=? AND type=? ORDER BY mId DESC";
		PreparedStatement insStmnt=null;
		PreparedStatement selId = null;
		ResultSet rs;
		Media retVal=null;
		try{
			if(mObj.getId()==0){
				con.setAutoCommit(false);
				//Prepare the Insert statement
				insStmnt=con.prepareStatement(insMedia);
				insStmnt.setString(1, mObj.getCreator());
				insStmnt.setString(2, mObj.getName());
				insStmnt.setInt(3, mObj.getDuration());
				insStmnt.setString(4, mObj.getGenre());
				insStmnt.setInt(5, num);
				insStmnt.setDouble(6, mObj.getPrice());
				insStmnt.setString(7, type);
				
				insStmnt.executeUpdate();
				
				//Prepare the select statement with same vals
				selId=con.prepareStatement(selMid);
				selId.setString(1, mObj.getCreator());
				selId.setString(2, mObj.getName());
				selId.setInt(3, mObj.getDuration());
				selId.setString(4, mObj.getGenre());
				selId.setInt(5, num);
				selId.setDouble(6, mObj.getPrice());
				selId.setString(7, type);
				
				//Get the newly inserted media object
				rs = selId.executeQuery();
				if(rs.first()){
					retVal=	DBIO.getMedia(rs.getInt("mId"));
				}else{
					throw new SQLException("Couldn't get inserted media");
				}
				con.commit();
			}else{
				//If it already has a non-zero mId we can use the simpler add
				DBIO.add(mObj.getId(), num);
			}
		}catch(SQLException sqlE){
			//Rollback query if we have any problems
			if(con!=null){
				try {
					con.rollback();
				} catch (SQLException ignore) {}
			}
			retVal=null;
		}finally{
			//Try to close things and put things back as we found them
			if (insStmnt!=null){try{insStmnt.close();} catch(SQLException ignore){}}
			if (selId!=null){try{selId.close();} catch(SQLException ignore){}}
			try{con.setAutoCommit(true);}catch(SQLException ignore){}
		}
		return retVal;
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
	 * @return Refreshed Media object with updated data members, including new
	 *         rating. If the update failed due to an SQLException, or changes
	 *         no data returns the same media object passed in. If the refresh
	 *         query failed then it returns null.
	 */
	public static Media updateRating(Media mObj, double rating) {
		int mId = mObj.getId();
		int isUpdated = 0;
		try {
			isUpdated = stmnt.executeUpdate("UPDATE Inventory SET"
					+ "avgRating=(avgRating*numRating +" + rating
					+ ")/(numRating+1), " + "numRating=numRating+1"
					+ "WHERE mId=" + mId);
			if (isUpdated != 0) { // Don't make an unnecessary SQL query
				mObj = getMedia(mObj.getId());
			}
		} catch (SQLException sqlE) {
			mObj = mObj;
		}
		return mObj; // TODO: Should this go in a finally? what's the diff?
	}

	/**
	 * Adds the sale of the num number of media objects to the specified
	 * customer. Fails if the customer doesn't have enough money or the store
	 * doesn't have enough inventory.
	 * 
	 * @param mObj
	 *            Media object being sold
	 * @param cust
	 *            customer buying item
	 * @param num
	 *            Integer number of media objects being sold.
	 * @return number of rows updated or -1 on failure
	 */
	public static int addSale(Media mObj, User cust, int num) {
		int numRows = 0;
		double price = 0, balance = 0;
		int numInStock;

		String sBal = "SELECT balance FROM Inventory WHERE uId=?";
		String sInv = "SELECT price, numInStock FROM Inventory WHERE mId=?";

		// Tests done in Java Transaction (does this cause it to be inefficient?
		// Should the tests be done in SQL if possible?
		// Does this cause loss of portability
		String uBal = "UPDATE User SET balance=? WHERE uId=?";
		String upInvStock = "UPDATE Inventory SET numInStock=? WHERE mId=?";
		String upSales = "INSERT INTO Sales (mId, numSold, uId, date) VALUES (?,?,?,?)";

		PreparedStatement getBal = null;
		PreparedStatement getInv = null;
		PreparedStatement decBal = null;
		PreparedStatement decStock = null;
		PreparedStatement insSales = null;
		try {
			getBal = con.prepareStatement(sBal);
			getInv = con.prepareStatement(sInv);
			decBal = con.prepareStatement(uBal);
			decStock = con.prepareStatement(upInvStock);
			insSales = con.prepareStatement(upSales);

			con.setAutoCommit(false);
			Savepoint save = con.setSavepoint();

			ResultSet rs;

			// Prepare getBal and call
			getBal.setInt(1, cust.getID());
			rs = getBal.executeQuery();
			rs.first();
			balance = rs.getDouble("balance");
			rs.close();

			// Prepare getInv and call
			getInv.setInt(1, mObj.getId());
			rs = getInv.executeQuery();
			rs.first();
			price = rs.getDouble("price");
			numInStock = rs.getInt("numInStock");
			rs.close();

			// Prepare decBal and call
			decBal.setDouble(1, balance - (num * price));
			decBal.setInt(2, cust.getID());
			numRows += decBal.executeUpdate();

			// Prepare decStock and call
			decStock.setInt(1, numInStock - num);
			decStock.setInt(2, mObj.getId());
			numRows += decStock.executeUpdate();

			// Insert Sales
			insSales.setInt(1, mObj.getId());
			insSales.setInt(2, num);
			insSales.setInt(3, cust.getID());
			// today's date, conflict between java.sql.Date and java.util.Date
			// there's really got to be a better way
			insSales.setDate(4, new java.sql.Date((new Date().getTime())));
			numRows += insSales.executeUpdate();

			// If customer doesn't have enough money or there aren't that many
			// in stock rollback
			if ((num * price) > balance || numInStock < num) {
				con.rollback(save);
				numRows = -1;
			}
			con.commit();

		} catch (SQLException sqlE) {
			numRows = -1;
		} finally {
			// Try and close prepared statements, ignoring any exceptions
			if (getBal != null)
				try {
					getBal.close();
				} catch (SQLException ignore) {
				}
			if (getInv != null)
				try {
					getInv.close();
				} catch (SQLException ignore) {
				}
			if (decBal != null)
				try {
					decBal.close();
				} catch (SQLException ignore) {
				}
			if (decStock != null)
				try {
					decStock.close();
				} catch (SQLException ignore) {
				}
			if (insSales != null)
				try {
					insSales.close();
				} catch (SQLException ignore) {
				}

		}
		return numRows;
	}

	// TODO: Should there be a removeSale and how should it do this? (thinking
	// of audit trailing)

	/**
	 * Refreshes/resets media object with data in database
	 * 
	 * @param mObj
	 *            Media object to refresh
	 * @return the refreshed media object or null on error
	 */
	public static Media getMedia(int mId) {
		String[] cols = { "*" };
		Integer[] condArr = { mId };
		ResultSet results;
		Media mObj;
		SelectBuilder sb = getSelectBuilder(cols, "Inventory");
		try {
			sb.addIntCondition("mId", "=", condArr, true);

			results = executeQuery(sb);
			mObj = result2Media(results).get(0);
			// TODO:Counting on finding exactly one row,probably should add some
			// checks or something.
		} catch (SQLException sqlE) {
			mObj = null;
		} catch (ArrayIndexOutOfBoundsException indexE) {
			mObj = null;
		} catch (Exception e) {
			mObj = null;
		}
		return mObj;

	}

	/**
	 * Turns a ResultSet from the Inventory Table into a Media array, which is
	 * what all Media results should be returned as.
	 * 
	 * @param results
	 *            ResultSet to process for Media objects
	 * @return Array of media objects extracted from the ResultSet
	 * @throws SQLException
	 *             if error reading from the ResultSet
	 */
	private static ArrayList<Media> result2Media(ResultSet results)
			throws SQLException {
		int mId, duration, numSold, numRating;
		double price, avgRating;
		String creator, name, genre, type;
		ArrayList<Media> mediaObjs = new ArrayList<Media>();
		//can handle null input
		if(results==null){
			return null;
		}
		while(results.next()){
			mId = results.getInt("mId");
			creator = results.getString("creator");
			name = results.getString("name");
			duration = results.getInt("duration");
			genre = results.getString("genre");
			numSold = results.getInt("numInStock"); // TODO: This is just plain
													// wrong
			price = results.getDouble("price");
			numRating = results.getInt("numRating");
			avgRating = results.getDouble("avgRating");
			type = results.getString("type");
			if (type.equalsIgnoreCase("book")) {
				mediaObjs.add(new Audiobook(creator, name, duration, genre,
						numSold, price, numRating, avgRating, mId));
			} else if (type.equalsIgnoreCase("album")) {
				mediaObjs.add(new Album(creator, name, duration, genre,
						numSold, price, numRating, avgRating, mId));
			} else if (type.equalsIgnoreCase("movie")) {
				mediaObjs.add(new Movie(creator, name, duration, genre,
						numSold, price, numRating, avgRating, mId));
			} else {
				mediaObjs.add(new Media(creator, name, duration, genre,
						numSold, price, numRating, avgRating, mId));
			}
		} 
		return mediaObjs;
	}

	/**
	 * Grabs all the media items of a certain type from the database as an
	 * ArrayList of Media objects. Safe to cast each item to the correct
	 * sub-type
	 * 
	 * @param type
	 *            String type to get a list of
	 * @return ArrayList of Media objects that representing all of the items of
	 *         the type wanted in the inventory
	 */
	public static ArrayList<Media> listOfType(String type) {
		String[] all = { "*" }, typeArr = { type };
		SelectBuilder listType = DBIO.getSelectBuilder(all, "Inventory");
		ArrayList<Media> retList;
		try {
			listType.addStringCondition("type", "=", typeArr, true);
		} catch (SQLException sqlE) {
			// Bug in code -- conditionArr is wrong size
			return null;
		} catch (Exception e) {
			// Bug in code -- operator isn't one
			return null;
		}
		try {
			retList = result2Media(listType.executeSelect(con));
		} catch (SQLException e) {
			// Database connection problems or malformed sql
			System.err.println(e.getMessage());
			return null;
		}
		return retList;

	}

	/**
	 * Gets a SelectBuilder object to build a Select query to execute on the
	 * database.
	 * 
	 * @param columnArr
	 *            String array of columns to select, must be non-null, can
	 *            contain solitary item, i.e. ["*"]
	 * @param tableName
	 *            String name of table to select columns from
	 * @return SelectBuilder initialized with the columns and table, allowing to
	 *         build a Where clause
	 */
	public static SelectBuilder getSelectBuilder(String[] columnArr,
			String tableName) {
		return new SelectBuilder(columnArr, tableName);
	}

	/**
	 * Executes the built Select statement on the database and returns the
	 * ResultSet.
	 * 
	 * @param sb
	 *            SelectBuilder containing the completed Select statement
	 * @return ResultSet containing the results returned by the database
	 */
	public static ResultSet executeQuery(SelectBuilder sb) {
		// TODO Take care of this thrown exception either here or in the
		// SelectBuilder class
		ResultSet rs;
		try {
			rs = sb.executeSelect(con);
		} catch (SQLException sqlE) {
			rs = null;
		}
		return rs;
	}
	/**
	 * Gets the total sales of the store.
	 * @return Double value of total sales of the store, NaN on error.
	 */
	public static double getTotalSales(){
		String [] cols = {"numSold", "curPrice"};
		SelectBuilder sales = DBIO.getSelectBuilder(cols, "Inventory");
		ResultSet allSales=null;
		double totalSales=0;
		try{
			allSales=sales.executeSelect(con);
			while(allSales.next()){
				int numSold = allSales.getInt("numSold");
				double price = allSales.getInt("curPrice");
				totalSales+= numSold*price;
			}
		}catch(SQLException sqlE){
			totalSales=Double.NaN;
		}finally{
			if (allSales!=null){try{allSales.close();}catch(SQLException ignore){}}
		}
		return totalSales;
	}
	/**
	 * Search's a field in inventory for a string, filtering by a media type.
	 * @param searchStr String to match on -- searches *searchStr* or in SQL LIKE %searchStr%
	 * @param searchField String name of field to match on
	 * @param mediaType String name of mediaType to filter on
	 * @return ArrayList of Media objects or null on error
	 */
	public static ArrayList<Media> searchInventory(String searchStr, String searchField, String mediaType){
		SelectBuilder sbInv=DBIO.search(searchStr, searchField, "Inventory");
		String [] typeCond = {mediaType};
		if(sbInv!=null){
			try{
				//TODO:security flaw here on searchField can't trust the user - need some sort of schema to check against
				sbInv.addStringCondition(searchField, "=", typeCond, true);
				return result2Media(sbInv.executeSelect(con));
			}catch(SQLException sqlE){
				return null;
			}catch(Exception e){
				return null;
			}
		}
		return null;
	}
	
	/**
	 * Queries the specific table for the pattern on the table on a specific media type if that makes sense on this context.
	 * @param searchStr	String pattern to match on
	 * @param searchField String name of column name to search on
	 * @param tableName String name of table to search on
	 * @return SelectBuilder with search condition applied to it.
	 */
	public static SelectBuilder search(String searchStr, String searchField, String tableName){
		String [] cols = {"*"};
		String [] searchCond = {"%"+searchStr+"%"}; //SelectBuilder needs it as an array
		//TODO: make an enum for the database schema to switch on
		SelectBuilder sb = DBIO.getSelectBuilder(cols,	 tableName);
		
		try{
			sb.addStringCondition(searchField, "LIKE", searchCond, true);
		}catch(SQLException sqlE){
			return null;
		}catch(Exception e){
			return null;
		}
		
		
		
		return null;
	}
}
