package com.hit.algorithm;

public interface IAlgoCache<T> {
	public abstract void refer(T page);
	public abstract T get();
}
