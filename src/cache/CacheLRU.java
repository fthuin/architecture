package cache;

import java.util.LinkedList;

/* This class implements a LRU cache limited with a number of slots. Here
 * we consider one resource = one slot: the size of the resource has no
 * matter.
 */

public class CacheLRU extends CacheManager {

    private LinkedList<RequestedObject> linkedlist;

    /* Constructor
     * @argument : capacity is the maximum number of slots available in
     * the LRU cache.
     */
    public CacheLRU(int capacity) {
        this.linkedlist = new LinkedList<RequestedObject>();
        this.capacity = capacity;
    }

    /* This method adds RequesstObject in the data structure, removing
     * least recently used element(s) if needed.
     */
    public void put(RequestedObject requestObject) {
        if(this.linkedlist.size()>= this.getCapacity()) {
            linkedlist.removeFirst();
        }
        linkedlist.addLast(requestObject);
    }

    /* This method returns true if the RequestedObject was already in the
     * cache, otherwise it returns false and put the RequestedObject in the
     * LRU cache.
     */
    public boolean get(RequestedObject requestObject) {
        boolean res = false;
        if(this.linkedlist.contains(requestObject)) {
            res = true;
            this.linkedlist.remove(requestObject);
        }
      	put(requestObject);
        return res;
    }
}
