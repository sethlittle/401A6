package a6novice;

public class Coordinate {

	/*
	 * This Coordinate is a rectangular Coordinate, an object that contains an x
	 * and a y value, more commonly referred to as a Point on an x-y plane
	 * 
	 */

	private int _x;
	private int _y;

	public Coordinate(int x, int y) {
		_x = x;
		_y = y;
	}

	public int getX() {
		return _x;
	}

	public int getY() {
		return _y;
	}

}
