package fr.upem.algoproject;

public class Rectangle {
	public final Point topleft, bottomright;
	
	public Rectangle(Point bottomright, Point topleft) {
		this.topleft = topleft;
		this.bottomright = bottomright;
	}

	@Override
	public String toString() {
		return "Rectangle [topleft=" + topleft + ", bottomright=" + bottomright
				+ "]";
	}
	

}
