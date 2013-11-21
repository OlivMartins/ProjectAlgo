package fr.upem.algoproject;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ListGraphTest {
	
	Graph g;

	@Before
	public void setUp() {
		this.g = new ListGraph(10);
	}
	@After
	public void tearDown() {
		this.g = null;
	}
	
	@Test
	public void testStorage() {
		g.addPath(0, 1);
		assertTrue(g.hasPath(0, 1));
		g.addPath(1,  0);
		assertTrue(g.hasPath(1, 0));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testTooLargeStorage() {
		for(int i=0; i<11; ++i) {
			g.addPath(i, i);
		}
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testIllegalNodeAccessed() {
		g.addPath(11, 11);
	}

}
