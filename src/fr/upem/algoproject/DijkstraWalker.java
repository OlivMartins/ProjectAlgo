package fr.upem.algoproject;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.logging.Logger;

public class DijkstraWalker {

	public static int[] walk(Graph g, int start, int end) {
		Logger logger = Logger.getLogger("fr.upem.algoproject");
		int nNodes = g.nodeCount();
		final int dist[] = new int[nNodes];
		boolean visited[] = new boolean[nNodes];
		int previous[] = new int[nNodes];

		for (int node : g.getAllNodes()) {
			dist[node] = -1;
			visited[node] = false;
			previous[node] = -1;
		}

		PriorityQueue<Integer> Q = new PriorityQueue<>(nNodes / 10,
				new Comparator<Integer>() {
					@Override
					public int compare(Integer o1, Integer o2) {
						return dist[o1] - dist[o2];
					}
				});
		dist[start] = 0;
		Q.offer(start);

		while (!Q.isEmpty()) {
			int u = Q.poll();
			if (u == end) {
				logger.info("We found the end of the maze!");
				return previous;
			}
			visited[u] = true;
			for (Integer v : g.getNeighbours(u)) {
				int alt = dist[u] + 1;
				if (dist[v] < 1 || (alt < dist[v] && !visited[v])) {
					dist[v] = alt;
					previous[v] = u;
					Q.add(v);
				}
			}
		}
		throw new NoSuchElementException(
				"No path exist between the given start and end");
	}
}
