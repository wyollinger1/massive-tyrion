package servs;

import store.DBIO;
import store.Media;

public class Util {
	/**
	 * Turns a media object into a JSON string.
	 * @param mObj Media object to get the JSON string for
	 * @return String JSON representation of the media object
	 */
	public  static String mediaToJson(Media mObj){
		if(mObj!=null){
			return String.format("{\"name\":\"%s\", \"duration\":%d, "+
				"\"creator\":\"%s\", \"genre\":\"%s\", \"price\":%.2f, " +
				"\"rating\":%.2f, \"id\":%d}",
				mObj.getName(), mObj.getDuration(), mObj.getCreator(),
				mObj.getGenre(), mObj.getPrice(), mObj.getAvgRating(),
				mObj.getId());
		}else{
			return null;
		}
	}
	/**
	 * Turns a string into it's corresponding DBIO.SearchField enum.
	 * 'name'=> NAME, 'genre'=>GENRE, 'creator'=>CREATOR
	 * @param str String to switch on
	 * @return DBIO.SearchField type matching passed in string.
	 */
	public static DBIO.SearchField strToSearchField(String str){
		if(str==null){
			return null;
		}else if(str.equalsIgnoreCase("name")){
			return DBIO.SearchField.NAME;
		}else if(str.equalsIgnoreCase("genre")){
			return DBIO.SearchField.GENRE;
		}else if(str.equalsIgnoreCase("creator")){
			return DBIO.SearchField.CREATOR;
		}else{
			return null;
		}
	}

	/**
	 * Turns a string into it's corresponding DBIO.Types enum.
	 * 'album'=>ALBUM, 'book'=>AUDIOBOOK, 'movie'=>MOVIE
	 * @param str String to switch on
	 * @return DBIO.Types type matching passed in string.
	 */
	public static DBIO.Types strToType(String str){
		if(str==null){
			return null;
		}else if(str.equalsIgnoreCase("album")){
			return DBIO.Types.ALBUM;
		}else if(str.equalsIgnoreCase("book")){
			return DBIO.Types.AUDIOBOOK;
		}else if(str.equalsIgnoreCase("movie")){
			return DBIO.Types.MOVIE;
		}else{
			return null;
		}
	}
}