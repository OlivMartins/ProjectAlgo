package fr.upem.algoproject;

class Node {
	private boolean end = false;

	private final int x, y;

	public Node(Point p) {
		this.x = p.x;
		this.y = p.y;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Node other = (Node) obj;
		if (end != other.end) {
			return false;
		}
		if (x != other.x) {
			return false;
		}
		if (y != other.y) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (end ? 1231 : 1237);
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	public boolean isEnd() {
		return end;
	}

	public void isEnd(boolean isEnd) {
		this.end = isEnd;
	}
}