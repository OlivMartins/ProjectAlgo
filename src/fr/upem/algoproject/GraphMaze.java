package fr.upem.algoproject;

public class GraphMaze implements Maze{
	
	private final Graph g;
	private final Point start;
	private final Point end;
	
	public GraphMaze(Graph g, Point start, Point end) {
		this.g = g;
		this.start = start;
		this.end = end;
	}

	@Override
	public boolean isWalkable(int i, int j) {
		Node n = new Node(new Point(i, j));
		return g.hasPath(n, n);
	}

	@Override
	public Point getStart() {
		return new Point(start);
	}

	@Override
	public Point getEnd() {
		return new Point(end);
	}
}
