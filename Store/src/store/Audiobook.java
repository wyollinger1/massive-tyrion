/**
 * Name: Jared Bean, Josh Thrush
 * Section: 1
 * Program: Project Phase 1
 * Date: 2/15/2013
 * Description: Audiobook subclass of Media
 */
/**
 * Audiobook subclass of Media
 * @author Jared Bean
 * @author Josh Thrush
 *
 */
public class Audiobook extends Media {

	//Initializer for an album object, sets all data members with the Media superclass
	public Audiobook(String creator, String name, int duration, String genre,
			int numSold, double price, int numRating, double avgRating, int id) {
		super(creator, name, duration, genre, numSold, price, numRating,
				avgRating, id);
	}

}
