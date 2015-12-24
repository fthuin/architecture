package utils;

import java.util.concurrent.PriorityBlockingQueue;

/* This class represent the a priority queue where the
*  customer are sorted in natural order (smallest job first)
*  PriorityBlockingQueue is thread safe
*/

public class PriorityBuffer<E> extends PriorityBlockingQueue<E>{
    private int size = 50;

    /**
     * Returns a new PriorityBuffer of a given size.
     * @param  size The maximum size of the buffer
     * @return      a new PriorityBuffer of size 'size'
     */
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

    /**
     * Returns true if the buffer is full
     * @return true if the number of elements equals the maximum size of the
     * buffer, false otherwise.
     */
	public synchronized boolean isFull(){
		return size() == size;
	}

    /**
     * Returns true if the buffer is empty
     * @return true if the buffer does not contain any element, false otherwise.
     */
	public synchronized boolean isEmpty() {
		return size()==0;
	}
}
