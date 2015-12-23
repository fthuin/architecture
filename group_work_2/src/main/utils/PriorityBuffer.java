package utils;

import java.util.concurrent.PriorityBlockingQueue;

/* This class represent the a priority queue where the
*  customer are sorted in natural order (smallest job first)
*  PriorityBlockingQueue is thread safe
*/

public class PriorityBuffer<E> extends PriorityBlockingQueue<E>{
    private int size = 50;

	public PriorityBuffer(int size) {
		super();
  		this.size = size;
	}

    /**
    * This method add an element to the queue if there is enough space
    * available
    */
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
