package cache;

public abstract class CacheManager {
    int size;

    abstract void put(RequestedObject requestObject);
    abstract RequestedObject get();
    abstract void remove();
}
