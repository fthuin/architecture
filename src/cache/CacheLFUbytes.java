package cache;

import java.util.HashMap;

/* This class implement the LFU cache size-based. It has a capacity
 * express in bytes
 */
public class CacheLFUbytes extends CacheManagerBytes {
    private HashMap<String, RequestedObject> hashMap = new HashMap<String, RequestedObject>();

    /* Constructor
     * @argument : capacity is the maximum number of byte available in
     * the LFU cache.
     */
    public CacheLFUbytes(int capacity) {
        this.capacity = capacity;
    }

    /*This method remove the element with the minimum access
     * counter.
     */
    public void remove() {
        RequestedObject current = null;
        int min = Integer.MAX_VALUE;
        for (RequestedObject request : this.hashMap.values()) {
            if (request.getAccessCounter() < min) {
                min = request.getAccessCounter();
                current = request;
            }
        }
        hashMap.remove(current.getName());
        this.curUsedSpace -= current.getSize();
    }

    /*This method remove an element already present in the cache
     * but which is requested with a new size.
     */
    public void removeDynamicRessource(RequestedObject requestObject) {
      if(this.hashMap.containsKey(requestObject.getName())){
       hashMap.remove(requestObject.getName());
       this.curUsedSpace -= requestObject.getSize();
      }
    }

    /*This method add a new element in the cache
     */

    public void put(RequestedObject requestObject) {
        while (requestObject.getSize() + curUsedSpace > this.getCapacity()) {
            remove();
        }
        hashMap.put(requestObject.getName(), requestObject);
        this.curUsedSpace += requestObject.getSize();
    }

    /*This method is used to get an element in the cache and if it's not 
     * in the cache, it will be added
     */
    public boolean specific_get(RequestedObject requestObject) {
        boolean res = true;
	    RequestedObject request = this.hashMap.get(requestObject.getName());
	    if (request == null) {
	        put(requestObject);
	        res = false;
	    } else {
	    	if (request.getSize() != requestObject.getSize()) {
	    		hashMap.remove(request.getName());
	    		this.curUsedSpace -= request.getSize();
	    		put(requestObject);
	    		res = false;
	    	} else {
	    		request.incrementCounter();
	    	}
	    }
        return res;
    }

    /*This method print the content of the cache, it's used to write the content
     * in a file
     */
    public String printCache() {
         String res = "";
         for (RequestedObject ro : this.hashMap.values()) {
              res += ro.getName() + "\n";
         }
         return res;
    }

}
