package utils;

import java.util.Queue;
import java.util.ArrayDeque;

public class Buffer<E> extends ArrayDeque<E> {
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
}
