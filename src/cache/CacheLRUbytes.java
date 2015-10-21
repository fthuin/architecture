package cache;

import java.util.LinkedList;

/* This class implement the LRU cache size-based. It has a capacity
 * express in bytes
 */
public class CacheLRUbytes extends CacheManagerBytes {
    private LinkedList<RequestedObject> linkedlist = new LinkedList<RequestedObject>();
    
    /* Constructor
     * @argument : capacity is the maximum number of byte available in
     * the LFU cache.
     */
    public CacheLRUbytes(int capacity) {
        this.capacity = capacity;
    }

    /*This method add an element to the cache, if there is not enough space, the 
     * oldest element in the cache will be removed.
     */
    public void put(RequestedObject requestObject) {
        while (this.curUsedSpace + requestObject.getSize() > this.getCapacity()) {
            RequestedObject ro = linkedlist.removeFirst();
            this.curUsedSpace -= ro.getSize();
        }
        linkedlist.addLast(requestObject);
        this.curUsedSpace += requestObject.getSize();
    }

    /*This method remove an element already present in the cache
     * but which is requested with a new size.
     */
    public void removeDynamicRessource(RequestedObject requestObject){
      if(this.linkedlist.contains(requestObject)){
        linkedlist.remove(requestObject);
        this.curUsedSpace -= requestObject.getSize();
      }
    }

    /*This method is used to get an element in the cache and if it's not 
     * in the cache, it will be added
     */
    public boolean specific_get(RequestedObject requestObject) {
        boolean res = false;
		if (this.linkedlist.contains(requestObject)) {
			RequestedObject ro = linkedlist.get(linkedlist.indexOf(requestObject));
			if (requestObject.getSize() != ro.getSize()) {
				res = false;
			} else {
				res = true;
			}
            this.linkedlist.remove(ro);
            this.curUsedSpace -= ro.getSize();
        }
        put(requestObject);
        return res;
    }

    /*This method print the content of the cache, it's used to write the content
     * in a file
     */
    public String printCache() {
        String res = "";
        for (int i = 0; i < this.linkedlist.size() ; i++) {
             res += this.linkedlist.get(i).getName() + "\n";
        }
        return res;
    }
}
