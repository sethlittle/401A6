package a6adept;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class WindowIterator implements Iterator<SubPicture> {

	/*
	 * This WindowIterator is an implementation of the Iterator interface
	 * 
	 * It iterates through a Picture and gives subPictures starting from the top
	 * left and going right and then down, incrementing by 1
	 */

	private int _x;
	private int _y;
	private int _width;
	private int _height;
	private Picture _source;

	public WindowIterator(Picture p, int window_width, int window_height) {
		if (p == null) {
			throw new IllegalArgumentException("Picture p cannot be null");
		}
		if ((window_width < 0 && window_width < p.getWidth()) || (window_height < 0 && window_height < p.getHeight())) {
			throw new IllegalArgumentException("The window height and widths must be positive");
		}

		/*
		 * _x and _y start out at 0
		 */

		_source = p;
		_width = window_width;
		_height = window_height;
		_x = 0;
		_y = 0;
	}

	/*
	 * This method tests whether or not the Picture has anymore Pixels to cycle
	 * through by checking the value of _x added with the _width and _y added to
	 * the _height and making sure they are less than or equal to the dimensions
	 * of the Picture
	 */

	@Override
	public boolean hasNext() {
		return (_x + _width <= _source.getWidth() && _y + _height <= _source.getHeight());
	}

	/*
	 * This method returns the next SubPicture in the iterator. Then it iterates
	 * through the Picture by increasing either the _x or _y values by just 1,
	 * because the "window" moves by 1
	 * 
	 * throws an exception if hasNext is false
	 */

	@Override
	public SubPicture next() {
		if (hasNext()) {
			SubPicture sub = _source.extract(_x, _y, _width, _height);
			if (_x + _width < _source.getWidth()) {
				_x = _x + 1;
			} else {
				_y = _y + 1;
				_x = 0;
			}
			return sub;
		} else {
			throw new NoSuchElementException("The next() method cannot be called when hasNext() is false");
		}
	}

}
