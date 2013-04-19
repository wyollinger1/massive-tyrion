package store;

import java.util.Date;
/**
 * Class represents an Order, already placed by a user or to be placed in the future
 * @author jbean
 *
 */
public final class Order {
  private final int  mId;
  private final int  numBought;
  private final Date date;

  Order(int mId, int numBought, Date date) {
    this.mId = mId;
    this.numBought = numBought;
    this.date = date;
  }

  public Media getMedia() {
    return DBIO.getMedia(mId);
  }

  public Date getDate() {
    return date;
  }

  public int getNumBought() {
    return numBought;
  }
}
