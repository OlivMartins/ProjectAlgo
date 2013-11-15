package fr.upem.algoproject;

import java.util.Queue;

public interface GraphWalker {

	public abstract Queue<Node> getShortestPath(Graph g, Point start, Point end);
}
