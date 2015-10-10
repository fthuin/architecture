package cache;

public class RequestedObject {
	private String name;
	private int size;
	private int access_counter;

    public RequestedObject(String name,int size) {
        this.name = name;
        this.size = size;
        this.access_counter = 0;
    }

    public String getName() {
        return this.name;
    }

    public int getAccessCounter() {
        return this.access_counter;
    }

    public void incrementCounter() {
        this.access_counter++;
    }

    public int getSize() {
        return this.size;
    }

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
