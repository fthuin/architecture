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
		if(this.hashmap.size()>= capacity)
		{
			remove();
		}
    hashmap.put(requestObject.getName(),requestObject);
    this.capacity++;
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
    this.capacity--;
	}

	public boolean get(RequestedObject requestObject)
	{
    boolean res = true;
		if(this.hashmap.get(requestObject.getName())==null)
		{
      put(requestObject);
      res = false;
		}
    return res;
	}

}
