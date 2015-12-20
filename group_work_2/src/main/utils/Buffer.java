package utils;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Buffer<E> extends ConcurrentLinkedDeque<E> {
	private int size = 50;

	public Buffer(int size) {
		super();
  		this.size = size;
	}

	public synchronized boolean add(E e) {
		if( size() < size ){
			super.add(e);
			return true;
		} else {
			return false;
		}
	}

	public synchronized boolean isFull(){
		return size() == size;
	}

	public synchronized boolean isEmpty() {
		return size()==0;
	}
}
