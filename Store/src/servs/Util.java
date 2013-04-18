package servs;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import store.Media;
import store.*;

public class Util {
  public static String orderToJson(Order oObj) {
    if (oObj != null) {
      store.Media mObj = DBIO.getMedia(oObj.getMedia().getId());
      return String.format("{\"media\":%s, \"numBought\":%d,"
          + "\"date\":\"%s\"}", mediaToJson(mObj), oObj.getNumBought(), oObj
          .getDate().toString());
    } else {
      return "{}";
    }
  }

  /**
   * Turns a media object into a JSON string.
   * 
   * @param mObj
   *          Media object to get the JSON string for
   * @return String JSON representation of the media object
   */
  public static String mediaToJson(Media mObj) {
    String type = "";
    if (mObj instanceof Album) {
      type = "album";
    } else if (mObj instanceof Audiobook) {
      type = "book";
    } else if (mObj instanceof Movie) {
      type = "movie";
    }

    if (mObj != null) {
      return String.format("{\"name\":\"%s\", \"duration\":%d, "
          + "\"creator\":\"%s\", \"genre\":\"%s\", \"type\":\"%s\","
          + "\"price\":%.2f, \"rating\":%.2f, \"id\":%d}", mObj.getName(),
          mObj.getDuration(), mObj.getCreator(), mObj.getGenre(), type,
          mObj.getPrice(), mObj.getAvgRating(), mObj.getId());
    } else {
      return "{}";
    }
  }

  /**
   * Turns a media object into a JSON string.
   * 
   * @param mObj
   *          Media object to get the JSON string for
   * @return String JSON representation of the media object
   */
  public static String userToJson(store.User uObj) {
    String orderArr;
    boolean isManager = false;
    if (uObj != null) {
      // Construct the user's purchase history as a JSON array of JSON
      // Order objs

      orderArr = "[";
      for (Order oObj : uObj.getHistory()) {
        orderArr += orderToJson(oObj) + ",";
      }
      orderArr = orderArr.substring(0, orderArr.length() - 1) + "]";
      // Make the actual user JSON obj
      if (uObj instanceof Manager) {
        isManager = true;
      }
      return String.format("{\"name\":\"%s\", \"city\":\"%s\", "
          + "\"balance\":%.2f, \"numOrders\":%d, \"id\":%d,"
          + "\"isManager\":%b}", uObj.getName(), uObj.getcity(),
          uObj.getBalance(), uObj.getHistory().length, uObj.getID(), isManager);
    } else {
      return "{}";
    }
  }

  /**
   * Turns a string into it's corresponding DBIO.SearchField enum. 'name'=>
   * NAME, 'genre'=>GENRE, 'creator'=>CREATOR
   * 
   * @param str
   *          String to switch on
   * @return DBIO.SearchField type matching passed in string.
   */
  public static DBIO.SearchField strToSearchField(String str) {
    if (str == null) {
      return null;
    } else if (str.equalsIgnoreCase("name")) {
      return DBIO.SearchField.NAME;
    } else if (str.equalsIgnoreCase("genre")) {
      return DBIO.SearchField.GENRE;
    } else if (str.equalsIgnoreCase("creator")) {
      return DBIO.SearchField.CREATOR;
    } else {
      return null;
    }
  }

  /**
   * Turns a string into it's corresponding DBIO.Types enum. 'album'=>ALBUM,
   * 'book'=>AUDIOBOOK, 'movie'=>MOVIE
   * 
   * @param str
   *          String to switch on
   * @return DBIO.Types type matching passed in string.
   */
  public static DBIO.Types strToType(String str) {
    if (str == null) {
      return null;
    } else if (str.equalsIgnoreCase("album")) {
      return DBIO.Types.ALBUM;
    } else if (str.equalsIgnoreCase("book")) {
      return DBIO.Types.AUDIOBOOK;
    } else if (str.equalsIgnoreCase("movie")) {
      return DBIO.Types.MOVIE;
    } else {
      return null;
    }
  }

  public static void closeRes(HttpServletResponse res, PrintWriter w) {
    w.flush();
    w.close();
    try {
      res.flushBuffer();
    } catch (IOException ignore) {
      // TODO:Maybe reset here
    }
  }

  public static boolean validateParams(Map<String, String[]> params,
      String... args) {
    for (int i = 0; i < args.length; i++) {
      if (params.containsKey(args[i]) && params.get(args[i]).length > 0) {
        continue;
      } else {
        return false;
      }
    }
    return true;
  }

  public static store.Media correctType(String creator, String name,
      int duration, String genre, double price, int numRating,
      double avgRating, int id, String type) {
    if (type.equalsIgnoreCase("album")) {
      return new store.Album(creator, name, duration, genre, price, numRating,
          avgRating, id);
    } else if (type.equalsIgnoreCase("movie")) {
      return new store.Movie(creator, name, duration, genre, price, numRating,
          avgRating, id);
    } else if (type.equalsIgnoreCase("book")) {
      return new store.Audiobook(creator, name, duration, genre, price,
          numRating, avgRating, id);

    }

    return null;
  }
}
