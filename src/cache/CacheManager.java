package cache;

public abstract class CacheManager {
    int size;

    abstract void put(RequestedObject requestObject);
    abstract boolean get(RequestedObject requestObject);
}
