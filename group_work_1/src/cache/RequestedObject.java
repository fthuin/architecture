package cache;

public class RequestedObject {
	private String name;
	private int size;
	private int access_counter;

    /* Constructor
     * Initializes a RequestedObject with a name and a size.
     */
    public RequestedObject(String name,int size) {
        this.name = name;
        this.size = size;
        this.access_counter = 0;
    }

    /* Returns the name of the RequestedObject
     */
    public String getName() {
        return this.name;
    }

    /* Returns the access counter of the RequestedObject
     */
    public int getAccessCounter() {
        return this.access_counter;
    }

    /* Increments the access counter of the RequestedObject
     */
    public void incrementCounter() {
        this.access_counter++;
    }

    /* Return the size of the RequestedObject
     */
    public int getSize() {
        return this.size;
    }

    /* This method overrides the equals from Object to allow us to compare two RequesteDObject
     */
    public boolean equals(Object o) {
        if (o instanceof RequestedObject) {
            RequestedObject other = (RequestedObject)o;
            return this.getName().equals(other.getName());
        }
        else {
            return false;
        }
    }

}
