

public class Movie extends Media {
	
	//Initializer for an album object, sets all data members with the Media superclass
	public Movie(String creator, String name, int duration, 
			String genre, int numSold, double price,
			int numRating, double avgRating){
		super( creator,  name,  duration, 
				 genre,  numSold,  price,
				 numRating,  avgRating);
	}
}
