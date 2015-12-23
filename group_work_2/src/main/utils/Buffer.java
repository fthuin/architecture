package utils;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

/*
	This class represent the queue used by the server.
  	It's a FCFS based queue where the capacity can be used
  	The queue is thread safe
*/

public class Buffer<E> extends LinkedBlockingDeque<E> {
	private int size = 50;

	public Buffer(int size) {
		super();
  		this.size = size;
	}
	/**
		This method add an element to the queue if the
		the queue is not full
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
