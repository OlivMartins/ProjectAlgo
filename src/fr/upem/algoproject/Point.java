package fr.upem.algoproject;

public class Point {
	public final int x, y;
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Point(Point point) {
		this.x = point.x;
		this.y = point.y;
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
}
