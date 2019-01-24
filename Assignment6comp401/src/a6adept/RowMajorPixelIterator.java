package a6adept;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RowMajorPixelIterator implements Iterator<Pixel> {

	/*
	 * This class is an implementation of Iterator, it cycles through Pixels of
	 * a Picture by calling the next method, this changes the x and y values,
	 * and returns the next pixel
	 */

	private Picture _source;
	private int _x;
	private int _y;

	public RowMajorPixelIterator(Picture p) {
		_source = p;
		_x = 0;
		_y = 0;

	}

	/*
	 * This method determines if there are any more pixels in the Picture,
	 * testing whether or not the x and y values have reached the width and
	 * height
	 */
	public boolean hasNext() {
		return (_x < _source.getWidth() && _y < _source.getHeight());
	}

	/*
	 * The next method throws a NoSuchElementException if hasNext() is false, if
	 * it is true, it returns the pixel at the current x and y value and then
	 * increments the x and y value, determining if the placement is at the end
	 * of a row
	 */

	public Pixel next() {
		if (hasNext()) {
			Pixel p = _source.getPixel(_x, _y);
			if (_x < _source.getWidth() - 1) {
				_x = _x + 1;
				;
			} else {
				_x = 0;
				_y = _y + 1;
			}
			return p;
		} else {
			throw new NoSuchElementException("There are no more elements in the Picture");
		}
	}

	/*
	 * The iterator cannot remove an item from the pixel array, so it throws an
	 * UnsupportedOperationException error
	 */

	public void remove() {
		throw new UnsupportedOperationException("You cannot remove using an iterator");
	}

}
