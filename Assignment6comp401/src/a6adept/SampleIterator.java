package a6adept;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SampleIterator implements Iterator<Pixel> {

	/*
	 * This class is a subClass of the Iterator, it will hold an x and y value
	 * and traverse through a Picture array of pixels and return the pixels at
	 * every dx and dy distance apart
	 */

	private int _init_x;
	private int _init_y;
	private int _x;
	private int _y;
	private int _dx;
	private int _dy;
	private Picture _source;

	/*
	 * The constructor takes in a picture, initial x and y, and the dx and dy
	 */

	public SampleIterator(Picture p, int init_x, int init_y, int dx, int dy) {
		if (p == null) {
			throw new IllegalArgumentException("Picture p cannot be null");
		}
		if ((init_x < 0 && init_x < _source.getWidth()) || (init_y < 0 && init_y < _source.getHeight())) {
			throw new IllegalArgumentException("The x and y values cannot be negative, and must be within the Picture");
		}
		if (dx < 0 || dy < 0) {
			throw new IllegalArgumentException("Dx and dy must be positive");
		}

		/*
		 * The _x and _y will be used to store the current value of the "cursor"
		 * and the _init_x and _init_y will be used to store the initial value
		 * so when the picture traverses another row, it can go back to the
		 * original value
		 */

		_source = p;
		_init_x = init_x;
		_init_y = init_y;
		_x = init_x;
		_y = init_y;
		_dx = dx;
		_dy = dy;
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
	 * This method returns the following pixel in the array. Then it iterates
	 * through the Picture by increasing either the _x or _y values
	 */

	@Override
	public Pixel next() {
		if (hasNext()) {
			Pixel p = _source.getPixel(_x, _y);
			if (_x + _dx < _source.getWidth()) {
				_x = _x + _dx;
			} else {
				_x = _init_x;
				_y = _y + _dy;
			}
			return p;
		} else {
			throw new NoSuchElementException("There are no more elements in the Picture");
		}
	}

}
