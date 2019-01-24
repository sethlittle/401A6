package a6jedi;

import java.util.Iterator;

public class ZigZagIterator implements Iterator<Pixel> {

	/*
	 * The ZigZagIterator is an implementation of the Iterator interface
	 * 
	 * This iterates through a Picture in a zig-zag pattern depending on which
	 * diagonal the coordinate of the pixel is, this can be determined by
	 * whether or not the sum of x and y is even or odd
	 */

	private int _x;
	private int _y;
	private Picture _source;

	public ZigZagIterator(Picture p) {
		if (p == null) {
			throw new IllegalArgumentException("The Picture p cannot be null");
		}
		_source = p;
		_x = 0;
		_y = 0;

	}

	/*
	 * This method tests whether or not the Picture has anymore Pixels to cycle
	 * through by checking the value of _x and _y and making sure they are
	 * within the Picture
	 */

	@Override
	public boolean hasNext() {
		return (_x < _source.getWidth() && _y < _source.getHeight());
	}

	/*
	 * This method returns the next zig-zag pixel in the array. Then it iterates
	 * through the Picture by increasing or decreasing either the _x or _y
	 * values
	 */

	@Override
	public Pixel next() {
		Pixel p = _source.getPixel(_x, _y);

		/*
		 * There are a few scenarios, if the pixel is in the middle of an even
		 * diagonal, it will increase the x and decrease the y by 1
		 * 
		 * If the Pixel is in the middle of an odd diagonal, it will decrease
		 * the x and increase the y by 1
		 */

		if ((_x + _y) % 2 == 0) {
			/*
			 * EVEN DIAGONAL
			 * 
			 * For even diagonal pixels, if the iterator is on one of the sides
			 * of the iterator, it cannot change one aspect of the number
			 */
			if (_x == _source.getWidth() - 1) {
				/*
				 * if the x value is at the end, or on the far right, then the y
				 * value should just increase by 1
				 */
				_y = _y + 1;
			} else {
				if (_y != 0) {
					/*
					 * If the y value is 0, it cannot decrease by 1
					 */
					_y = _y - 1;
				}
				_x = _x + 1;
				/*
				 * If the x value can be increased by 1, then it will
				 */
			}
		} else if ((_x + _y) % 2 == 1) {
			/*
			 * ODD DIAGONAL
			 * 
			 * if the iterator is on one of the sides, it cannot change both the
			 * x and the y values, so we must check if it is at the end
			 */
			if (_y == _source.getHeight() - 1) {
				/*
				 * if the y value is at the very bottom of the Picture array,
				 * the x value just increases by 1
				 */
				_x = _x + 1;
			} else {
				if (_x != 0) {
					/*
					 * If the x value is just 0, then it cannot be decreased by
					 * 1
					 */
					_x = _x - 1;
				}
				_y = _y + 1;
				/*
				 * If the y value can be increased by 1 then it needs to be
				 */
			}
		}
		return p;
	}

}
