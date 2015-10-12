package cache;

import java.util.LinkedList;

public class CacheLRUbytes extends CacheManagerBytes {
    private LinkedList<RequestedObject> linkedlist = new LinkedList<RequestedObject>();

    public CacheLRUbytes(int capacity) {
        this.capacity = capacity;
    }

    public void put(RequestedObject requestObject) {
        while (this.curUsedSpace + requestObject.getSize() > this.getCapacity()) {
            RequestedObject ro = linkedlist.remove();
            this.curUsedSpace -= ro.getSize();
        }
        linkedlist.addLast(requestObject);
        this.curUsedSpace += requestObject.getSize();
    }

    public void removeDynamicRessource(RequestedObject requestObject){
      linkedlist.remove(requestObject);
      this.curUsedSpace -= requestObject.getSize();
    }

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

    public String printCache() {
        String res = "";
        for (int i = 0; i < this.linkedlist.size() ; i++) {
             res += this.linkedlist.get(i).getName() + "\n";
        }
        return res;
    }
}
