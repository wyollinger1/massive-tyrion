import java.sql.SQLException;

/**
 * Name: Jared Bean, Josh Thrush
 * Section: 1
 * Program: Project Phase 1
 * Date: 2/15/2013
 * Description: Super class for all the media types
 */

/**
 * A data class representing a media object with appropriate getters and setters.
 * @author Jared Bean, Josh Thrush
 *
 */
public class Media {
	
	protected String creator;
	protected String name;
	protected int duration;
	protected String genre;
	protected double price;
	protected int numRating;
	protected double avgRating;
	protected int id;
	/**
	 * Constructor for a Media object, initialized with all the data defining a media object.
	 * @param creator String name of the creator of this media object
	 * @param name String name of the media object
	 * @param duration Integer duration in seconds
	 * @param genre String name of genre
	 * @param numSold Integer number of media objects sold in the store 
	 * @param price Price in dollars 
	 * @param numRating Integer number of ratings given to the media object
	 * @param avgRating Average rating of the media object, represented as a double
	 * @param id Integer unique id number
	 */
	Media(String creator, String name, int duration, 
			String genre, int numSold, double price,
			int numRating, double avgRating, int id){
		this.creator=creator;
		this.name=name;
		this.duration=duration;
		this.genre=genre;
		this.price=price;
		this.numRating=numRating;
		this.avgRating=avgRating;
		this.id =id;
	}
	/**
	 * Gets the id
	 * @return Integer id of media object
	 */
	public int getId(){
		return this.id;
	}
	/**
	 * Gets the creator	
	 * @return String name of creator
	 */
	public String getCreator() {
		return creator;
	}
	/**
	 * Sets the creator's name
	 * @param creator String new name of the creator
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}
	/**
	 * Gets the name of the media object.
	 * @return String name of the media object.
	 */
	public String getName() {
		return name;
	}
	/**
	 * Sets the name of the media object.
	 * @param name String name of the media object.
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Gets the duration in seconds of the media object
	 * @return Integer duration in seconds
	 */
	public int getDuration() {
		return duration;
	}
	/**
	 * Sets the duration of the media object
	 * @param duration Integer new duration of the media object
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}
	/**
	 * Gets the name of the genre this belongs to
	 * @return String name of genre
	 */
	public String getGenre() {
		return genre;
	}
	/**
	 * Sets the name of the genre this belongs to
	 * @param genre String new name of this's genre
	 */
	public void setGenre(String genre) {
		this.genre = genre;
	}
	/**
	 * Gets the number sold of this media object
	 * @return Integer number sold, -1 on error
	 */
	public int getNumSold() {
		String [] colNames = {"SUM(numSold)"};
		Integer [] conArr = {(Integer)this.id}; // Selecting number of sold of this media item, by id
		int numSold;
		SelectBuilder selectNumSold = DBIO.getSelectBuilder(colNames, "SALES");
		
		try{
			selectNumSold.addIntCondition("mId", "=", conArr, true);
			numSold=DBIO.executeQuery(selectNumSold).getInt(0);
		}catch(SQLException sqlE){
			//TODO: Alert user somehow that there's a 'temporary' error with the db
			numSold=-1;
		}catch(Exception e){
			//TODO: Alert user somehow that there's a bug in code
			numSold =-1;
		}
		
		return numSold;
	}
	/**
	 * Gets the price in dollars 
	 * @return Price in dollars
	 */
	public double getPrice() {
		return price;
	}
	/**
	 * Sets the price in dollars
	 * @param price New price in dollars.
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	/**
	 * Gets the number of ratings given
	 * @return Integer number of ratings given.
	 */
	public int getNumRating() {
		return numRating;
	}
	/**
	 * Sets the number of ratings given.
	 * @param numRating Integer new number of ratings given.
	 */
	public void setNumRating(int numRating) {
		this.numRating = numRating;
	}
	/**
	 * Gets the average rating
	 * @return The average rating as a double
	 */
	public double getAvgRating() {
		return avgRating;
	}
	/**
	 * Sets the average rating
	 * @param avgRating The new average rating as a double
	 */
	public void setAvgRating(double avgRating) {
		this.avgRating = avgRating;
	}
	
	/**
	 * Tests equality of media items based on their identifying information 
	 * of the item. Specifically on the media items creator, name, duration,
	 * and genre.
	 * @param mObj Media object to test equality against 
	 * @return true if objects are equal false otherwise
	 */
	@Override 
	public boolean equals(Object obj){
		Media mObj;
		if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (obj.getClass() != getClass())
            return false;
        mObj = (Media)obj;
		return this.creator.equals(mObj.getCreator()) && 
				this.name.equals(mObj.getName()) && 
				this.duration==mObj.getDuration() && 
				this.genre.equals(mObj.getGenre());
	}
	
	/**
	 * Returns a hash such that equal media items return equal hashes.
	 * @return integer hash code
	 */
	@Override
	public int hashCode(){
		return this.creator.hashCode()+this.genre.hashCode()+this.duration;
		
		
	}
	/**
	 * Gives a string representation of this media object by printing all of its data.
	 * 
	 * @return String representation of this media object
	 */
	@Override
	public String toString() {
		return "Media [creator=" + creator + ", name=" + name + ", duration="
				+ duration + ", genre=" + genre + ", numSold=" + this.getNumSold()
				+ ", price=" + price + ", numRating=" + numRating
				+ ", avgRating=" + avgRating + "]";
	}
	
	/**
	 * Adds another rating to product and updates avgRating
	 * @param rating double rating being added
	 */
	public void addRating(double rating){
		this.avgRating=( (this.numRating*this.avgRating) + rating)/ ++(this.numRating);
	}
}
