package cache;
import java.util.LinkedList;

public class CacheLRU extends CacheManager{
  private LinkedList<RequestedObject> linkedlist;
  private int capacity;

  public CacheLRU(int capacity)
  {
    this.linkedlist = new LinkedList<RequestedObject>();
    this.capacity = capacity;
  }
  
  public void put(RequestedObject requestObject)
  {
    if(this.linkedlist.size()>=capacity)
    {
      linkedlist.removeFirst();
    }
    linkedlist.addLast(requestObject);
  }


  public boolean get(RequestedObject requestObject)
  {
    boolean res = false;
    if(this.linkedlist.contains(requestObject)) 
    {
      this.linkedlist.remove(requestObject);
      res = true; 
    }
    put(requestObject);
    return res;
  }
  
}
