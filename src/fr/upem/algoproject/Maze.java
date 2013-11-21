package fr.upem.algoproject;

public class Maze {

	private final Point start;
	private final Point end;
	private final Graph g;
	private final int width, height;

	public Maze(Point start, Point end, Graph g, int width, int height) {
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

	public Graph getGraph() {
		return g;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	
}
