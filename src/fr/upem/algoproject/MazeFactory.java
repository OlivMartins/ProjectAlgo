package fr.upem.algoproject;

public class MazeFactory {

	private Node createNodeFromCoordinates(int i, int j) {

		return null;
	}

	public static Maze fromArrayMaze(ArrayMaze maze) {
		Graph g = new ListGraph(maze.size);
		Point start = maze.getStart();
		Point end = maze.getEnd();

		for (int i = 0; i < maze.size; ++i) {
			for (int j = 0; j < maze.size; ++j) {

				if (maze.isWalkable(i, j)) {
					Point p = new Point(i, j);
					Node n1 = new Node(p);
					if (p.equals(end))
						n1.isEnd(true);

					for (int dx = -1; dx <= 1; ++dx) {
						for (int dy = -1; dy <= 1; ++dy) {

							if (i + dx > 0 && i + dx < maze.size && j + dy > 0
									&& j + dy < maze.size) {
								if (maze.isWalkable(i + dx, j + dy)) {

									Point p2 = new Point(i + dx, j + dy);
									Node n2 = new Node(p2);
									if (n2.equals(end))
										n2.isEnd(true);
									g.addPath(n1, n2);
								}
							}
						}
					}
				}
			}
		}
		return new GraphMaze(g, start, end);
	}

}
