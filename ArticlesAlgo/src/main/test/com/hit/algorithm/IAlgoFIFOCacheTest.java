package com.hit.algorithm;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class IAlgoFIFOCacheTest {

	@Test
	void test() {
		FIFOAlgoCache<Integer> f = new FIFOAlgoCache<>(5);
		// Adds elements {0, 1, 2, 3, 4} to queue
		for (int i = 0; i < 8; i++)
			f.refer(i);

		// Display contents of the queue.
		System.out.println("Elements of queue-" + f);

		System.out.println(f);
		assertEquals(f.toString().contains("0"),false);
	}

}


