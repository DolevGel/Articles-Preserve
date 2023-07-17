package com.hit.algorithm;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;

public class LRUAlgoCache<T> extends AbsAlgoCache<T> {

	// store keys of cache
	private Deque<T> doublyQueue;

	// store references of key in cache
	private HashSet<T> hashSet;

	public LRUAlgoCache(int capacity) {
		super(capacity);
		doublyQueue = new LinkedList<>();
		hashSet = new HashSet<>();

	}

	/* Refer the page within the LRU cache */
	public void refer(T page) {
		if (!hashSet.contains(page)) {
			if (doublyQueue.size() == size) {
				T last = doublyQueue.removeLast();
				hashSet.remove(last);
			}
		} else {/*
				 * The found page may not be always the last element, even if it's an
				 * intermediate element that needs to be removed and added to the start of the
				 * Queue
				 */
			doublyQueue.remove(page);
		}
		doublyQueue.push(page);
		hashSet.add(page);
	}

	@Override
	public String toString() {
		return doublyQueue.toString();
	}

	@Override
	public T get() {
		// TODO Auto-generated method stub
		if (doublyQueue.size()==0) {
			return null;
		};
		T last = doublyQueue.removeLast();
		hashSet.remove(last);
		return last;
	}
}
