/**
 * Name: Jared Bean, Josh Thrush
 * Section: 1
 * Program: Project Phase 1
 * Date: 2/15/2013
 * Description: Album subclass of Media
 */

public class Album extends Media {

	//Initializer for an album object, sets all data members with the Media superclass
	public Album(String creator, String name, int duration, String genre,
			int numSold, double price, int numRating, double avgRating) {
		super(creator, name, duration, genre, numSold, price, numRating,
				avgRating);
	}

}
