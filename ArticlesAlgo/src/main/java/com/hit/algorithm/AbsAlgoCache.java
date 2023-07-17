package com.hit.algorithm;

public abstract class AbsAlgoCache<T> implements IAlgoCache<T> {
	protected int size;

	public AbsAlgoCache(int size) {
		this.size = size;
	}
}
