package store;

public class Audiobook extends Media {

	public Audiobook(String creator, String name, int duration, String genre,
			int numSold, double price, int numRating, double avgRating) {
		super(creator, name, duration, genre, numSold, price, numRating,
				avgRating);
	}

}
