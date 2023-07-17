package com.hit.algorithm;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class IAlgoLRUCacheTest {

	@Test
	void test() {

		IAlgoCache<Integer> cache = new LRUAlgoCache<Integer>(4);
		cache.refer(1);
		cache.refer(2);
		cache.refer(3);
		cache.refer(1);
		cache.refer(4);
		cache.refer(5);
		cache.refer(2);
		cache.refer(2);
		cache.refer(1);

		assertEquals(cache.toString().contains("3"), false);

		System.out.println("************");
		IAlgoCache<String> userIdCache = new LRUAlgoCache<>(4);
		userIdCache.refer("dol");
		userIdCache.refer("hey");
		userIdCache.refer("hey");
		userIdCache.refer("hit");
		userIdCache.refer("fda");
		userIdCache.refer("dol");
		userIdCache.refer("hey");
		userIdCache.refer("lru");
		userIdCache.refer("hey");

		assertEquals(userIdCache.toString().contains("hit"), false);

	}

}
