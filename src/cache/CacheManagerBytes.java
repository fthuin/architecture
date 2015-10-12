package cache;

public abstract class CacheManagerBytes extends CacheManager {
  private long hittedbytes = 0;
  private long totalbytes = 0;
  protected long curUsedSpace = 0;

  abstract boolean specific_get(RequestedObject requestObject);
  abstract void removeDynamicRessource(RequestedObject requestObject);

  public long getTotalBytes() {
    return this.totalbytes;
  }

  public long getHittedBytes() {
    return this.hittedbytes;
  }


  public boolean get(RequestedObject requestObject) {
    this.totalbytes += requestObject.getSize();
    boolean res = false;
    if (requestObject.getSize() > this.getCapacity()) {
      removeDynamicRessource(requestObject);
    } else {
      res = specific_get(requestObject);
      if (res) {
        hittedbytes += requestObject.getSize();
      }

    }
    return res;
  }

}
