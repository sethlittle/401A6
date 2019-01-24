package a6jedi;

public class SubPictureImpl extends AnyPicture implements SubPicture {

	/*
	 * The SubPictureImpl class is an implementation of SubPicture
	 * 
	 * It has a Picture field, and an x Offset and a y Offset value, the offset
	 * values determine the beginning of the SubPicture, or the point in the
	 * SubPicture where x = 0 and y = 0
	 */

	private Picture _source;
	private int _xOffset;
	private int _yOffset;

	public SubPictureImpl(Picture source, int xOffset, int yOffset, int width, int height) {
		if (source == null) {
			throw new IllegalArgumentException("The picture source cannot be null");
		}
		if (xOffset + width > source.getWidth() || yOffset + height > source.getHeight()) {
			throw new IllegalArgumentException("The subpicture subarea is greater than the picture source");
		}
		if (width < 0 || height < 0) {
			throw new IllegalArgumentException("Width and height must be positive");
		}
		if (xOffset < 0 || yOffset < 0) {
			throw new IllegalArgumentException("xOffset and yOffset must be positive");
		}

		/*
		 * The height and width values are stored in the parent class AnyPicture
		 * in order to conserve encapsulation
		 */

		_source = source;
		super.setWidth(width);
		super.setHeight(height);
		_xOffset = xOffset;
		_yOffset = yOffset;

	}

	/*
	 * This method counts the amount of Pixels, within the intensity values of
	 * the parameters low and high, and returns the number
	 * 
	 * The low and high parameters must both be between 0 and 1
	 */

	@Override
	public int countRange(double low, double high) {
		int count = 0;
		for (int i = 0; i < super.getWidth() - _xOffset; i++) {
			for (int j = 0; j < super.getHeight() - _yOffset; j++) {
				if (getSource().getPixel(i + _xOffset, j + _yOffset).getIntensity() <= high
						&& getSource().getPixel(i + _xOffset, j + _yOffset).getIntensity() >= low) {
					count++;
				}
			}
		}
		return count;
	}

	/*
	 * This method returns the Pixel at point (x, y) by using the getSource and
	 * getPixel method defined for a PictureImpl
	 */

	@Override
	public Pixel getPixel(int x, int y) {
		if (x > super.getWidth() || y > super.getHeight()) {
			throw new IllegalArgumentException("x and y must be less than the size of the Picture");
		}

		/*
		 * the x value for the getPixel must be added to the x offset to return
		 * the correct pixel for the SubPicture, and the same thing for the y
		 * value
		 */

		return getSource().getPixel(x + getXOffset(), y + getYOffset());
	}

	/*
	 * The setPixel method places a pixel from the parameter (cannot be null) to
	 * the parameters (x,y) (both within the Picture)
	 * 
	 * The x and y values both must have the offsets added to them to reference
	 * the correct pixel in the source pixel array
	 */

	@Override
	public void setPixel(int x, int y, Pixel p) {
		if (x > super.getWidth() || y > super.getHeight()) {
			throw new IllegalArgumentException("x and y must be less than the size of the Picture");
		}
		if (p == null) {
			throw new IllegalArgumentException("Pixel p cannot be null");
		}
		getSource().setPixel(x + getXOffset(), y + getYOffset(), p);
	}

	/*
	 * The following getter and setter methods are needed to keep encapsulation
	 * of the fields of this class
	 */

	public void setSource(Picture s) {
		_source = s;
	}

	public void setXOffset(int xOffset) {
		_xOffset = xOffset;
	}

	public void setYOffset(int yOffset) {
		_yOffset = yOffset;
	}

	@Override
	public Picture getSource() {
		return _source;
	}

	@Override
	public int getXOffset() {
		return _xOffset;
	}

	@Override
	public int getYOffset() {
		return _yOffset;
	}

}
