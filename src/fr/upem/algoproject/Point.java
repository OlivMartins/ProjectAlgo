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
	
	public static Point fromValue(int value, int width){
		return new Point(value % width, value / width);
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
}
