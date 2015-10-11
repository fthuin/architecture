package cache;

import java.util.HashMap;

public class CacheLFUbytes extends CacheManagerBytes {
    private HashMap<String, RequestedObject> hashMap = new HashMap<String, RequestedObject>();

    public CacheLFUbytes(int capacity) {
        this.capacity = capacity;
    }

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

    public void put(RequestedObject requestObject) {
        while (requestObject.getSize() + curUsedSpace > this.getCapacity()) {
            remove();
        }
        hashMap.put(requestObject.getName(), requestObject);
        this.curUsedSpace += requestObject.getSize();
    }

    public boolean specific_get(RequestedObject requestObject) {
        boolean res = true;
	    RequestedObject request = this.hashMap.get(requestObject.getName());
	    if (request == null) {
	        put(requestObject);
	        res = false;
	    } else {
	        request.incrementCounter();
	    }
        return res;
    }

}
