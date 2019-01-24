package a6jedi;

public class PictureImpl extends AnyPicture implements Picture {

	/*
	 * The PictureImpl class is an implementation of the Picture interface, it
	 * stores a double Pixel Array
	 */

	private Pixel[][] _pixels;

	public PictureImpl(int width, int height) {

		/*
		 * The height and width of a Picture can not be negative
		 */

		if (width < 0 || height < 0) {
			throw new IllegalArgumentException("The width and height must be positive");
		}

		/*
		 * The width and heights need to be stored in the parent class,
		 * AnyPicture to conserve encapsulation
		 */

		super.setWidth(width);
		super.setHeight(height);
		_pixels = new Pixel[width][height];

		/*
		 * When a new Picture is created, all of the Pixels are set as Gray
		 * Colored Pixels
		 */

		for (int i = 0; i < super.getWidth(); i++) {
			for (int j = 0; j < super.getHeight(); j++) {
				_pixels[i][j] = new ColorPixel(0.5, 0.5, 0.5);
			}
		}
	}

	/*
	 * This method takes a Pixel and puts it in the array at the parameters (x,
	 * y)
	 * 
	 * The x and y values must be positive and within the Picture, and the pixel
	 * cannot be null
	 * 
	 */

	public void setPixel(int x, int y, Pixel p) {

		if (x < 0 || x >= super.getWidth()) {
			throw new IllegalArgumentException("The x values must be within the Picture");
		}

		if (y < 0 || y >= super.getHeight()) {
			throw new IllegalArgumentException("The y values must be within the Picture");
		}

		if (p != null) {
			_pixels[x][y] = p;
		} else {
			throw new RuntimeException("Pixel p cannot have null as the value");
		}
	}

	/*
	 * This method returns the pixel at parameters (x,y)
	 * 
	 * x and y must be positive and within the Pixel
	 */

	public Pixel getPixel(int x, int y) {

		if (x < 0 || x >= super.getWidth()) {
			throw new IllegalArgumentException("The x values must be within the Picture");
		}

		if (y < 0 || y >= super.getHeight()) {
			throw new IllegalArgumentException("The y values must be within the Picture");
		}

		// changed x and y

		return _pixels[x][y];
	}

	/*
	 * This method counts the amount of Pixels, within the intensity values of
	 * the parameters low and high, and returns the number
	 * 
	 * The low and high parameters must both be between 0 and 1
	 */

	public int countRange(double low, double high) {
		int count = 0;
		if (low <= 1.0 && low >= 0.0 && high <= 1.0 && high >= 0.0) {
			for (int i = 0; i < super.getWidth(); i++) {
				for (int j = 0; j < super.getHeight(); j++) {
					if (_pixels[i][j].getIntensity() <= high && _pixels[i][j].getIntensity() >= low) {
						count++;
					}
				}
			}
			return count;
		} else {
			throw new RuntimeException("Low and high must be between 0 and 1");
		}
	}

}
