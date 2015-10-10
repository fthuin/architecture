package cache;

import java.util.LinkedList;

public class CacheLRUbytes extends CacheManager {
    private LinkedList<RequestedObject> linkedlist = new LinkedList<RequestedObject>();
    private int capacity = 0;
    private int curUsedCapacity = 0;

    public CacheLRUbytes(int capacity) {
        this.capacity = capacity;
    }

    public void put(RequestedObject requestObject) {
        while (this.curUsedCapacity + requestObject.getSize() > this.capacity) {
            RequestedObject ro = linkedlist.remove();
            this.curUsedCapacity -= ro.getSize();
        }
        linkedlist.addLast(requestObject);
        this.curUsedCapacity += requestObject.getSize();
    }

    public boolean get(RequestedObject requestObject) {
        boolean res = false;
		if (this.linkedlist.contains(requestObject)) {
            this.linkedlist.remove(requestObject);
            this.curUsedCapacity -= requestObject.getSize();
            res = true;
        }
        if (requestObject.getSize() > capacity) {
        	System.out.println("LRU cache - We can't add the file " + requestObject.getName() + " in the provided cache because the given size is too small...");
        } else {
        	put(requestObject);
        }
        return res;
    }
}
