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

  public Cache(int capacity)
  {
    this.hashmap = new HashMap<String,RequestedObject>(capacity);
    this.capacity = capacity;
  }

	public void put(RequestedObject requestObject)
	{
    System.out.println("Size: "+this.hashmap.size());
    System.out.println("Capacity:"+this.capacity);
		if(this.hashmap.size()>= this.capacity)
		{
      System.out.println("CACHE FULL REMOVING ELEM");
			remove();
		}
    hashmap.put(requestObject.getName(),requestObject);
	}

	private void remove() {
    RequestedObject current = null;
    int min = Integer.MAX_VALUE;
    for(RequestedObject request : this.hashmap.values())
    {
      if(request.getAccessCounter() < min)
      {
        min = request.getAccessCounter();
        current = request;
      }
    }
    hashmap.remove(current.getName());
	}

	public boolean get(RequestedObject requestObject)
	{
    boolean res = true;
		if(this.hashmap.get(requestObject.getName())==null)
		{
      System.out.println("FAIL");
      put(requestObject);
      res = false;
		}
    return res;
	}

}
