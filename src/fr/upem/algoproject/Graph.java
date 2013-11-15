package fr.upem.algoproject;

public interface Graph {

	public boolean hasPath(Node node1, Node node2);
	public void addPath(Node node1, Node node2);
	public int nodeCount();
	
}
