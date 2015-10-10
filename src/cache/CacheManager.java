package cache;

public abstract class CacheManager {
    int size;

    abstract void put(RequestedObject requestObject);
    abstract RequestedObject get(RequestedObject requestObject);
    abstract void remove();
}
