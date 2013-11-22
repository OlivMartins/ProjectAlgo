package fr.upem.algoproject;

import java.util.Collection;
import java.util.List;

public class Rectangles {

	public static boolean contains(Point topleft, Point bottomright, Point p) {

		if ((p.x >= topleft.x && p.y >= topleft.y)
				&& (p.x <= bottomright.x && p.y <= bottomright.y))
			return true;
		return false;
	}

	public static boolean contains(Point topleft, Point bottomright, int x,
			int y) {
		if ((x >= topleft.x && y >= topleft.y)
				&& (x <= bottomright.x && y <= bottomright.y))
			return true;
		return false;
	}

	public static boolean contains(Rectangle r, int x, int y) {
		return contains(r.topleft, r.bottomright, x, y);
	}

	public static boolean contains(Collection<Rectangle> l, Point p) {
		boolean foundObstacle = false;
		for (Rectangle r : l) {
			if (contains(r, p.x, p.y)) {
				foundObstacle = true;
				break;
			}
		}
		return foundObstacle;
	}

	public static boolean contains(List<Rectangle> l, int x, int y) {
		boolean foundObstacle = false;
		for (Rectangle r : l) {
			if (contains(r, x, y)) {
				foundObstacle = true;
				break;
			}
		}
		return foundObstacle;
	}

}
