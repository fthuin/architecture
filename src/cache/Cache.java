package cache;

import java.util.HashMap;

public class Cache {
	private HashMap<String,RequestedObject> hashmap;
	private int capacity;
	
	public Cache(HashMap<String,RequestedObject> hashmap,int capacity)
	{
		this.hashmap = hashmap;
		this.capacity = capacity;
	}
	
	public void put(RequestedObject request)
	{
		if(hashmap.size()>= capacity)
		{
			remove_lfu();
		}
    hashmap.put(request.getName(),request);
    capacity++;
	}
	
	private void remove_lfu() {
    RequestedObject current = null; 
    int counter = Integer.MAX;


		// TODO Auto-generated method stub
		
	}

	public boolean get(RequestedObject request)
	{
    boolean res = true;
		if(hashmap.get(request.getName())==null)
		{
      put(request); 	 	
      res = false;
		}
    return res;
	}

}
