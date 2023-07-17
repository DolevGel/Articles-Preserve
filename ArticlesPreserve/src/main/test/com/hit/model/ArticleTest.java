package com.hit.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hit.algorithm.FIFOAlgoCache;
import com.hit.service.Service;

public class ArticleTest {

	Service service = null;
	Article article1 = null;
	Article article2 = null;
	@Before
	public void setUp() throws Exception {
		service = new Service(new FIFOAlgoCache<Article>(50));
		article1 = new Article("Pushdown Automation", "an automation that employs stack");
		article2 = new Article("Finit State", " its too difficult");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testHashCode() {
		assertEquals(article1.hashCode() != article1.hashCode(), true);
	}



	@Test
	public void testGetTitle() {
		assertEquals(article1.getTitle().equals("Pushdown Automation"), true);
	}



}
