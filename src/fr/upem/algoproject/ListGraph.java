package fr.upem.algoproject;

import java.util.ArrayList;
import java.util.List;

public class ListGraph implements Graph {

	private final List<Node> array[];

	public ListGraph(int nNodes) {
		array = new List[nNodes];
		for (int i = 0; i < nNodes; ++i) {
			array[i] = new ArrayList<Node>(nNodes);
		}
	}

	@Override
	public void addPath(Node n1, Node n2) {
		if (!hasPath(n1, n2))
			array[n1.hashCode() % array.length].add(n2);
	}

	@Override
	public boolean hasPath(Node node1, Node node2) {
		return array[node1.hashCode() % array.length].contains(node2);
	}

	@Override
	public int nodeCount() {
		return array.length;
	}

	@Override
	public List<Node> getNodeList() {
		ArrayList<Node> allNodes = new ArrayList<>(nodeCount());
		for(int i=0; i < nodeCount() ; ++i) {
			allNodes.addAll(array[i]);
		}
		return allNodes;
	}

}
