package store;

public class Movie extends Media {
	Movie(String creator, String name, int duration, 
			String genre, int numSold, double price,
			int numRating, double avgRating){
		super( creator,  name,  duration, 
				 genre,  numSold,  price,
				 numRating,  avgRating);
	}
}
