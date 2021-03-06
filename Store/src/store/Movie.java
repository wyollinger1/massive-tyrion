package store;

/**
 * Name: Jared Bean, Josh Thrush
 * Section: 1
 * Program: Project Phase 1
 * Date: 2/15/2013
 * Description: Movie subclass of Media
 */
/**
 * Movie subclass of Media
 * 
 * @author Jared Bean
 * @author Josh Thrush
 * 
 */
public class Movie extends Media {

  // Initializer for an album object, sets all data members with the Media
  // superclass
  public Movie(String creator, String name, int duration, String genre,
      double price, int numRating, double avgRating, int id) {
    super(creator, name, duration, genre, price, numRating, avgRating, id);
  }
}
