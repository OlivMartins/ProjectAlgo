package fr.upem.algoproject;

import java.util.List;

public interface Graph {
	public boolean hasPath(int node1, int node2);
	public void addPath(int node1, int node2);
	public int nodeCount();
	public List<Integer> getNeighbours(int n);
	public List<Integer> getAllNodes();
	
}
