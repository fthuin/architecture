package cache;

/* This abstract class defines basic methods every cache should have.
 */
public abstract class CacheManager {
    int size;

    abstract void put(RequestedObject requestObject);
    abstract boolean get(RequestedObject requestObject);
}
