package utils;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
public class Buffer<E> extends ConcurrentLinkedDeque<E> {
	private int size = 1;

	public Buffer(int size) {
		super();
  		this.size = size;
	}

	public boolean add(E e) {
		if(size()<size){
			super.add(e);
			return true;
		}else{
			return false;
		}
	}

	public boolean isFull(){
		return size()==size;
	}

	public boolean isEmpty() {
		return size()==0;
	}
}
