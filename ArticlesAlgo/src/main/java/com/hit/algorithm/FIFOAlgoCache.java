package com.hit.algorithm;

import java.util.LinkedList;
import java.util.Queue;

public class FIFOAlgoCache<T> extends AbsAlgoCache<T> {

	private Queue<T> q = new LinkedList<>();

	public FIFOAlgoCache(int size) {
		super(size);
	}

	@Override
	public void refer(T page) {
		// TODO Auto-generated method stub
		if (q.size() == size) {
			q.remove();
		}
		q.add(page);
	}

	@Override
	public String toString() {
		return q.toString();
	}

	@Override
	public T get() {
		// TODO Auto-generated method stub
		if (q.size()==0) {
			return null;
		};
		return q.remove();
	}
}
