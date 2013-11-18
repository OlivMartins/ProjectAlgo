package fr.upem.algoproject;

import java.util.Hashtable;
import java.util.Queue;

public interface GraphWalker {

	public abstract Hashtable<Node, Node> getShortestPath(Graph g, Point start, Point end);
}
