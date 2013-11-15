package fr.upem.algoproject;

import java.util.List;


class ArrayMaze implements Maze {
	public final int size;
	private final boolean[][] array;
	public final Point start, end;

	public ArrayMaze(int size, List<Rectangle> obstacles, Point start,
			Point end) {
		this.size = size;
		array = new boolean[size][size];
		for (int i = 0; i < size; ++i) {
			for (int j = 0; j < size; ++j) {
				for (Rectangle r : obstacles) {
					if (Rectangles.contains(r.bottomright, r.topleft,
							new Point(i, j))) {
						array[i][j] = false;
					} else
						array[i][j] = true;
				}
			}
		}
		this.start = start;
		this.end = end;
	}

	@Override
	public String toString() {
		return "ArrayMaze [size=" + size + ", start=" + start + ", end="
				+ end + "]";
	}

	/* (non-Javadoc)
	 * @see fr.upem.algoproject.Maze#isWalkable(int, int)
	 */
	@Override
	public boolean isWalkable(int i, int j) {
		return array[i][j];
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