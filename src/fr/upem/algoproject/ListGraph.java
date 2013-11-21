package fr.upem.algoproject;

import java.util.ArrayList;
import java.util.List;

public class ListGraph implements Graph {
	
	private final List<Integer> array[];
	
	public ListGraph(int nNodes) {
		this.array = new ArrayList[nNodes];
		for(int i=0; i < nNodes; ++i) {
			array[i] = new ArrayList<>();
		}
	}

	@Override
	public boolean hasPath(int node1, int node2) {
		return array[node1].contains(node2);
	}

	@Override
	public void addPath(int node1, int node2) {
		array[node1].add(node2);
	}

	@Override
	public int nodeCount() {
		return array.length;
	}

	@Override
	public List<Integer> getNeighbours(int n) {
		List<Integer> l = new ArrayList<>();
		l.addAll(this.array[n]);
		return l;
	}

	@Override
	public List<Integer> getAllNodes() {
		List<Integer> l = new ArrayList<>();
		for(int i=0; i < array.length; ++i) {
			l.add(i);
		}
		return l;
	}

}
