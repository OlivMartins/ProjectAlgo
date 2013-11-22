package fr.upem.algoproject;

public class Maze {

	private final Point start;
	private final Point end;
	private final ListGraph g;
	private final int width, height;
	private int[] shortestPath;

	public int[] getShortestPath() {
		return shortestPath;
	}

	public void setShortestPath(int[] shortestPath) {
		this.shortestPath = shortestPath;
	}

	public Maze(Point start, Point end, ListGraph g, int width, int height) {
		this.start = start;
		this.end = end;
		this.g = g;
		this.width = width;
		this.height = height;
	}

	public Point getStart() {
		return start;
	}

	public Point getEnd() {
		return end;
	}

	public ListGraph getGraph() {
		return g;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	
}
