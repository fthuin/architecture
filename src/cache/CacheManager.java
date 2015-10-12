package cache;

import java.util.ArrayList;
import java.util.List;

/* This abstract class defines basic methods and variables every cache should have.
 */
public abstract class CacheManager {
    public int capacity = 0;
    public List<String> sizeChangingReq = new ArrayList<String>();

    abstract void put(RequestedObject requestObject);
    abstract boolean get(RequestedObject requestObject);
    abstract String printCache();

    public int getCapacity() {
        return this.capacity;
    }
}
