package fr.upem.algoproject;

import java.util.List;

public interface Graph {

	public boolean hasPath(Node node1, Node node2);
	public void addPath(Node node1, Node node2);
	public int nodeCount();
	public List<Node> getNodeList();
	public List<Node> getNeighbours(Node n);
	
}
