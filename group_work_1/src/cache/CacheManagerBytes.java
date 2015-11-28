package cache;

/*This abstract class define the basic method that cache with
 *capcity in byte
 */
public abstract class CacheManagerBytes extends CacheManager {
  private long hittedbytes = 0;
  private long totalbytes = 0;
  protected long curUsedSpace = 0;
  private boolean isWarm = false;

  abstract boolean specific_get(RequestedObject requestObject);
  abstract void removeDynamicRessource(RequestedObject requestObject);

  public long getTotalBytes() {
    return this.totalbytes;
  }

  public long getHittedBytes() {
    return this.hittedbytes;
  }
  
  public void setWarm() {
    this.isWarm=true;
  }


  public boolean get(RequestedObject requestObject) {
    if(isWarm)
      this.totalbytes += requestObject.getSize();
    boolean res = false;
    if (requestObject.getSize() > this.getCapacity()) {
      removeDynamicRessource(requestObject);
    } else {
      res = specific_get(requestObject);
      if (res && isWarm) {
        hittedbytes += requestObject.getSize();
      }

    }
    return res;
  }

}
