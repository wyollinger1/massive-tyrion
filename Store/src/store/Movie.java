/**
 * Name: Jared Bean, Josh Thrush
 * Section: 1
 * Program: Project Phase 1
 * Date: 2/15/2013
 * Description: Movie subclass of Media
 */
public class Movie extends Media {
	Movie(String creator, String name, int duration, 
			String genre, int numSold, double price,
			int numRating, double avgRating){
		super( creator,  name,  duration, 
				 genre,  numSold,  price,
				 numRating,  avgRating);
	}
}
