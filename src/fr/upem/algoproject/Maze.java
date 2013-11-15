package fr.upem.algoproject;

interface Maze {

	public abstract boolean isWalkable(int i, int j);
	public abstract Point getStart();
	public abstract Point getEnd();

}