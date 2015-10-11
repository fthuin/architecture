package cache;

public abstract class CacheManagerBytes extends CacheManager {
	public int hittedbytes = 0;
    public int totalbytes = 0;
    public int curUsedSpace = 0;
    
    abstract boolean specific_get(RequestedObject requestObject);
    
    public boolean get(RequestedObject requestObject) {
        this.totalbytes += requestObject.getSize();
    	boolean res = false;
   	    if (requestObject.getSize() > this.getCapacity()) {
   	    	System.out.println("CacheManagerBytes - You tried to add a file ("+requestObject.getName()+") larger than cache size.");
   	    } else {
   	    	res = specific_get(requestObject);
   	    	if (res) {
   	    		hittedbytes += requestObject.getSize();
   	    	}
   	    	
   	    }
   	    return res;
    }
    
} 
