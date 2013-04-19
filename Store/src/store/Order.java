package store;

import java.util.Date;
/**
 * Class represents an Order, already placed by a user or to be placed in the future
 * Used for the (non-existant) shopping cart and the order history
 * This is a final class because editing a placed order should not happen.
 * 
 * @author jbean
 *
 */
public final class Order {
  private final int  mId; //Id of the media object ordered
  private final int  numBought; //Number bought
  private final Date date; //Date the order was placed

  /**
   * Simple initialization constructor
   * @param mId Integer id of the media object ordered
   * @param numBought Integer number bought
   * @param date Date this order was placed
   */
  Order(int mId, int numBought, Date date) {
    this.mId = mId;
    this.numBought = numBought;
    this.date = date;
  }

  /**
   * Get's Media object id
   * @return Integer id of the Media that was ordered
   */
  public Media getMedia() {
    return DBIO.getMedia(mId);
  }

  /**
   * Get's the date the order was placed
   * @return Date object when the order was placed
   */
  public Date getDate() {
    return date;
  }

  /**
   * Get's the number bought
   * @return  Integer number bought
   */
  public int getNumBought() {
    return numBought;
  }
}
