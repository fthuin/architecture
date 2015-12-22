package utils;

import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBuffer<E> extends PriorityBlockingQueue<E>{
    private int size = 50;

	public PriorityBuffer(int size) {
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