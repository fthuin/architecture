package cache;

public class RequestedObject {
	private String name;
	private int size;
	private int access_counter;

  public RequestedObject(String name,int size)
  {
    this.name = name;
    this.size = size;
    this.access_counter = 0;
  }  

  public String getName()
  {
    return this.name;
  }

  public int get_access_counter()
  {
    return this.access_counter; 
  }


}
