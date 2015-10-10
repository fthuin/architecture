public abstract class CacheManager {
    int size;

    abstract void put(RequestObject requestObject);
    abstract RequestObject get();
    abstract void remove();
}
