

public class Media {
	
	protected String creator;
	protected String name;
	protected int duration;
	protected String genre;
	protected int numSold;
	protected double price;
	protected int numRating;
	protected double avgRating;
	
	Media(String creator, String name, int duration, 
			String genre, int numSold, double price,
			int numRating, double avgRating){
		this.creator=creator;
		this.name=name;
		this.duration=duration;
		this.genre=genre;
		this.numSold=numSold;
		this.price=price;
		this.numRating=numRating;
		this.avgRating=avgRating;
	}
	
	/*
	 * Auto-generated getters/setters
	 */
	
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public int getNumSold() {
		return numSold;
	}
	public void setNumSold(int numSold) {
		this.numSold = numSold;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getNumRating() {
		return numRating;
	}
	public void setNumRating(int numRating) {
		this.numRating = numRating;
	}
	public double getAvgRating() {
		return avgRating;
	}
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
	public boolean equals(Media mObj){
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
	
	@Override
	public String toString() {
		return "Media [creator=" + creator + ", name=" + name + ", duration="
				+ duration + ", genre=" + genre + ", numSold=" + numSold
				+ ", price=" + price + ", numRating=" + numRating
				+ ", avgRating=" + avgRating + "]";
	}
}
