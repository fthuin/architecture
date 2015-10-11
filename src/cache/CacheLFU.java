package cache;

import java.util.HashMap;

/* This class contains the implementation of a LFU cache limited in a
 * number of slots. Here we consider one resource = one slot, the size of a
 * resource has no matter.
 */

public class CacheLFU extends CacheManager {
	private HashMap<String,RequestedObject> hashmap;

    /* Constructor
     * @argument : capacity is the maximum number of slots available in
     * the LFU cache.
     */
    public CacheLFU(int capacity) {
        this.hashmap = new HashMap<String,RequestedObject>(capacity);
        this.capacity = capacity;
    }

    /* This method adds a RequestedObject in the data structure,
     * removing the least frequently used element(s) if needed.
     */
	public void put(RequestedObject requestObject) {
		if(this.hashmap.size()>= this.getCapacity()) {
			this.remove();
		}
        hashmap.put(requestObject.getName(),requestObject);
	}

    /* This method removes the element with the least frequency usage
     * contained in the data structure.
     */
    private void remove() {
        RequestedObject current = null;
        int min = Integer.MAX_VALUE;
        for(RequestedObject request : this.hashmap.values()) {
            if(request.getAccessCounter() < min) {
                min = request.getAccessCounter();
                current = request;
            }
        }
        hashmap.remove(current.getName());
	}

    /* This method returns true if the RequestedObject was already in the
     * cache, otherwise it returns false and put the RequestedObject in the
     * LFU cache.
     */
	public boolean get(RequestedObject requestObject){
        boolean res = true;
        RequestedObject request = this.hashmap.get(requestObject.getName());
        if(request==null) {
        	if (! sizeChangingReq.contains(requestObject.getName())) {
        		put(requestObject);
        	}
            res = false;
		} else {
			if (request.getSize() != requestObject.getSize()) {
				sizeChangingReq.add(requestObject.getName());
				res = false;
			} else {
                request.incrementCounter();
            }
        }
        return res;
	}
}
