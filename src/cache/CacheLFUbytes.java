package cache;

import java.util.HashMap;

public class CacheLFUbytes extends CacheManager {
    private HashMap<String, RequestedObject> hashMap = new HashMap<String, RequestedObject>();
    private int maxCapacity = 0;
    private int curUsedSpace = 0;

    public CacheLFUbytes(int capacity) {
        this.maxCapacity = capacity;
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
        while (requestObject.getSize() + curUsedSpace > maxCapacity) {
            remove();
        }
        hashMap.put(requestObject.getName(), requestObject);
        this.curUsedSpace += requestObject.getSize();
    }

    public boolean get(RequestedObject requestObject) {
        boolean res = true;
        if (requestObject.getSize() > maxCapacity) {
        	System.out.println("We can't add the file " + requestObject.getName() + " in the provided cache because the given cache size is too small...");
        	res = false;
        } else {
		    RequestedObject request = this.hashMap.get(requestObject.getName());
		    if (request == null) {
		        put(requestObject);
		        res = false;
		    } else {
		        request.incrementCounter();
		    }
		}
        return res;
    }

}
