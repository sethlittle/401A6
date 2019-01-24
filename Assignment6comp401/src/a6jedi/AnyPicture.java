package a6jedi;

import java.util.Iterator;

public abstract class AnyPicture implements Picture {
	/*
	 * this abstract class is used by all classes of type Picture in order to
	 * provide inheritance, this is the top of the inheritance pyramid if you
	 * will, everything derives from this class.
	 * 
	 * These methods are Overrides because a type of Picture called with these
	 * methods, unless abstract, will use the implementation from this class
	 */

	private int _width;
	private int _height;

	public void setWidth(int width) {
		_width = width;
	}

	public void setHeight(int height) {
		_height = height;
	}

	@Override
	public int getWidth() {
		return _width;
	}

	@Override
	public int getHeight() {
		return _height;
	}

	/*
	 * This is an abstract method to set a Pixel at a given point (x, y). This
	 * method is abstract because the Picture class has direct access to the
	 * double array of pixels, while the SubPicture class does not, and so
	 * therefore it needs two different implementations
	 * 
	 */
	@Override
	abstract public void setPixel(int x, int y, Pixel p);

	/*
	 * This Overload method uses the getters for x and y for the Coordinate
	 * class and calls the above method, or the abstract setPixel method
	 */
	@Override
	public void setPixel(Coordinate c, Pixel p) {
		this.setPixel(c.getX(), c.getY(), p);
	}

	/*
	 * This abstract method returns the pixel currently at the point (x, y).
	 * This is an abstract method because the Picture class has direct access to
	 * the double array of pixels for pictures. This is a private field, so
	 * SubPicture cannot access it and needs to have a different implementation
	 * to do so
	 * 
	 */
	@Override
	abstract public Pixel getPixel(int x, int y);

	/*
	 * this overload method uses the getters for x and y for the Coordinate
	 * class in order to call the abstract method above with parameters of an x
	 * and a y
	 * 
	 */
	@Override
	public Pixel getPixel(Coordinate c) {
		return this.getPixel(c.getX(), c.getY());
	}

	/*
	 * This method counts how many Pixels within the picture are between two
	 * intensity values (low and high : parameters) and returns the total
	 * number.
	 * 
	 * It is an abstract method because the Picture class contains the private
	 * field of the double array of Pixels while the SubPicture class does not.
	 * Therefore, this method needs different implementations depending on the
	 * type of Picture
	 */
	@Override
	abstract public int countRange(double low, double high);

	/*
	 * print() prints out the intensities of all the pixels in any type of
	 * picture, it uses the _height field to know when to print the last
	 * intensity in a line and move down to the next line
	 * 
	 */
	@Override
	public void print() {
		for (int i = 0; i < _width; i++) {
			for (int j = 0; j < _height; j++) {
				if (j < _height - 1) {
					System.out.print(" " + this.getPixel(i, j).getIntensity() + " ");
				} else {
					System.out.println(" " + this.getPixel(i, j).getIntensity() + " ");
				}
			}
		}
	}

	/*
	 * This method returns a SubPicture with sizes given in the parameters
	 * (width and height) and references the Picture with an offset x and an
	 * offset y meaning that the point (0,0) on the SubPicture is a point on the
	 * Picture (x offset, y offset)
	 */
	@Override
	public SubPicture extract(int xOffset, int yOffset, int width, int height) {
		return new SubPictureImpl(this, xOffset, yOffset, width, height);
	}

	/*
	 * Overload method of extract
	 * 
	 * has multiple different scenarios for the placement of the two coordinates
	 * 
	 * Then the method uses the extract method to create a SubPicture using the
	 * top left point of the dimension specified by the if statements above
	 * 
	 * The x offset and the y offset is the x and y value, respectively, of the
	 * top left point
	 * 
	 * The height and width must have plus one added to them in order to create
	 * the correct sized dimensions of a SubPicture. Because two pixels would
	 * produce a width of (2-1) = 1 and would produce a width of only 1 even
	 * though the SubPicture has a width of 2
	 * 
	 */
	@Override
	public SubPicture extract(Coordinate corner_a, Coordinate corner_b) {

		/*
		 * The Coordinate x's and y's must be positive and within the Dimensions
		 * of the picture in order to extract a SubPicture
		 */

		if (corner_a.getX() < 0 || corner_a.getY() < 0) {
			throw new IllegalArgumentException(
					"Corner a needs to be inside of the Picture, both positive x and y values");
		}

		if (corner_b.getX() < 0 || corner_b.getY() < 0) {
			throw new IllegalArgumentException(
					"Corner b needs to be inside of the Picture, both positive x and y values");
		}

		if (corner_a.getX() > _width || corner_a.getY() > _height) {
			throw new IllegalArgumentException("Corner a needs to be within the Picture");
		}

		if (corner_b.getX() > _width || corner_b.getY() > _height) {
			throw new IllegalArgumentException("Corner b needs to be within the Picture");
		}

		/*
		 * The following section is the if statements that determine the values
		 * of the x offset, and y offset for the extract method
		 */

		if (corner_a.getX() == corner_b.getX() && corner_a.getY() == corner_b.getY()) {

			return this.extract(corner_a.getX(), corner_b.getY(), 1, 1);

		} else if (corner_a.getX() <= corner_b.getX() && corner_a.getY() >= corner_b.getY()) {

			return this.extract(corner_a.getX(), corner_a.getY() - 1, ((corner_b.getX() - corner_a.getX()) + 1),
					((corner_a.getY() - corner_b.getY()) + 1));

		} else if (corner_a.getX() <= corner_b.getX() && corner_a.getY() <= corner_b.getY()) {

			return this.extract(corner_a.getX(), corner_b.getY() - 1, ((corner_b.getX() - corner_a.getX()) + 1),
					((corner_b.getY() - corner_a.getY()) + 1));

		} else if (corner_a.getX() >= corner_b.getX() && corner_a.getY() >= corner_b.getY()) {

			return this.extract(corner_b.getX(), corner_a.getY() - 1, ((corner_a.getX() - corner_b.getX()) + 1),
					((corner_a.getY() - corner_b.getY()) + 1));

		} else {

			return this.extract(corner_b.getX(), corner_b.getY() - 1, ((corner_a.getX() - corner_b.getX()) + 1),
					((corner_b.getY() - corner_a.getY()) + 1));
		}

	}

	/*
	 * The Iterator method produces a RowMajorPixelIterator to cycle through the
	 * x and y points in a row major order by calling the method next() on the
	 * RowMajorPixelIterator
	 * 
	 */

	@Override
	public Iterator<Pixel> iterator() {
		return new RowMajorPixelIterator(this);
	}

	/*
	 * The sample method is an iterator that samples through the pixels starting
	 * at the initial (x, y) and samples pixels ever dx apart and every dy apart
	 * 
	 */

	@Override
	public Iterator<Pixel> sample(int init_x, int init_y, int dx, int dy) {
		return new SampleIterator(this, init_x, init_y, dx, dy);
	}

	/*
	 * The Window method produces a subPicture iterator, this produces several
	 * SubPictures as if you were sliding a window over the Picture and
	 * extracting the subPicture over that area
	 * 
	 */

	@Override
	public Iterator<SubPicture> window(int window_width, int window_height) {
		return new WindowIterator(this, window_width, window_height);
	}

	/*
	 * The tile method produces subPictures as if the original picture was cut
	 * into multiple tiles with the given parameters as height and width
	 */

	@Override
	public Iterator<SubPicture> tile(int tile_width, int tile_height) {
		return new TileIterator(this, tile_width, tile_height);
	}

	/*
	 * This method iterates through a Picture and returns the Pixels in a zigzag
	 * order, starting at the top left and then going right and then down left
	 */

	@Override
	public Iterator<Pixel> zigzag() {
		return new ZigZagIterator(this);
	}

}
