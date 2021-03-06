package cache;

import java.util.HashMap;

/* This class implements a size-based cache Removing the Largest File in
 * the cache first.
 */

public class CacheRLF extends CacheManagerBytes {
    private HashMap<String, RequestedObject> hashMap = new HashMap<String, RequestedObject>();

    public CacheRLF(int capacity) {
    	this.capacity = capacity;
    }

    /*This method add an element to the cache, if there is no space
     *the largest file will be remove.
     */
    public void put(RequestedObject requestObject) {
    	while (requestObject.getSize() + curUsedSpace > capacity) {
    		int maxSize = Integer.MIN_VALUE;
    		RequestedObject ro = null;
            for(RequestedObject request : this.hashMap.values()) {
                if(request.getSize() > maxSize) {
                    maxSize = request.getSize();
                    ro = request;
                }
            }
            hashMap.remove(ro.getName());
            curUsedSpace -= ro.getSize();
    	}
    	hashMap.put(requestObject.getName(), requestObject);
    	curUsedSpace += requestObject.getSize();
    }

    /*This method remove an element already present in the cache
     * but which is requested with a new size.
     */
    public void removeDynamicRessource(RequestedObject requestObject){
      if(this.hashMap.containsKey(requestObject.getName())) {
        this.hashMap.remove(requestObject.getName());
        this.curUsedSpace -= requestObject.getSize();
      }
   }

     /*This method is used to get an element in the cache and if it's not 
     * in the cache, it will be added
     */
    public boolean specific_get(RequestedObject requestObject) {
        boolean result = false;
    	RequestedObject ro = hashMap.get(requestObject.getName());
    	if (ro == null) {
 			put(requestObject);
    	} else {
    		if (ro.getSize() != requestObject.getSize()) {
    			hashMap.remove(ro);
    			this.curUsedSpace -= ro.getSize();
    			put(requestObject);
    		} else {
	    		result = true;
	    	}
    	}
    	return result;
    }

    public String printCache() {
         String res = "";
         for (RequestedObject ro : this.hashMap.values()) {
              res += ro.getName() + "\n";
         }
         return res;
    }
}
