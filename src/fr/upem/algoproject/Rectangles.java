package fr.upem.algoproject;

public class Rectangles {

	public static boolean contains(Point topleft, Point bottomright, Point p) {
		
		if((p.x > topleft.x && p.y > topleft.y) && (p.x < bottomright.x && p.y < bottomright.y))
			return true;
		return false;
	}
	
}
