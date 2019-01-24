package a6novice;

public class ColorPixel implements Pixel {

	/*
	 * Color Pixel class is an implementation of the Pixel interface
	 * 
	 * This class has three value fields for each aspect of color, green red and
	 * blue
	 */

	private double red;
	private double green;
	private double blue;

	/*
	 * These constants are used to convert the color values into the itensity of
	 * the Pixel
	 */

	private static final double RED_INTENSITY_FACTOR = 0.299;
	private static final double GREEN_INTENSITY_FACTOR = 0.587;
	private static final double BLUE_INTENSITY_FACTOR = 0.114;

	/*
	 * these constants are used for the lighten and darken methods and the pixel
	 * character map is used to represent certain intensities
	 */

	private static final Pixel WHITE_PIXEL = new GrayPixel(1.0);
	private static final Pixel BLACK_PIXEL = new GrayPixel(0.0);
	private static final char[] PIXEL_CHAR_MAP = { '#', 'M', 'X', 'D', '<', '>', 's', ':', '-', ' ', ' ' };

	public ColorPixel(double r, double g, double b) {

		/*
		 * the red green and blue doubles must be within 0 and 1
		 */

		if (r > 1.0 || r < 0.0) {
			throw new RuntimeException("Red out of bounds");
		}
		if (g > 1.0 || g < 0.0) {
			throw new RuntimeException("Green out of bounds");
		}
		if (b > 1.0 || b < 0.0) {
			throw new RuntimeException("Blue out of bounds");
		}
		red = r;
		green = g;
		blue = b;
	}

	@Override
	public double getRed() {
		return red;
	}

	@Override
	public double getBlue() {
		return blue;
	}

	@Override
	public double getGreen() {
		return green;
	}

	/*
	 * The intensity value is calculated using the constants and the double
	 * values
	 */

	@Override
	public double getIntensity() {
		return RED_INTENSITY_FACTOR * getRed() + GREEN_INTENSITY_FACTOR * getGreen()
				+ BLUE_INTENSITY_FACTOR * getBlue();
	}

	/*
	 * this method takes the intensity and casts it to an integer and then
	 * returns a character from the character map depending on the intensity
	 */

	@Override
	public char getChar() {
		int char_idx = (int) (getIntensity() * 10.0);
		return PIXEL_CHAR_MAP[char_idx];
	}

	/*
	 * The lighten method uses factor (between 0 and 1) and blends a white pixel
	 * with the pixel the method
	 */

	@Override
	public Pixel lighten(double factor) {
		if (factor < 0.0 || factor > 1.0) {
			throw new RuntimeException("Lighten factor out of range");
		}
		return WHITE_PIXEL.blend(this, factor);
	}

	/*
	 * the darken method uses a factor (0 to 1) and blends a black pixel with
	 * the pixel the method is called on to the degree of the factor
	 * 
	 */

	@Override
	public Pixel darken(double factor) {
		if (factor < 0.0 || factor > 1.0) {
			throw new RuntimeException("Darken factor out of range");
		}
		return BLACK_PIXEL.blend(this, factor);
	}

	/*
	 * this method uses a parameter pixel p (cannot be null) and a weight
	 * (between 0 and 1) and creates a new Color Pixel with the weight from the
	 * pixel in the parameters and the pixel the method is called on
	 */

	@Override
	public Pixel blend(Pixel p, double weight) {
		if (weight < 0.0 || weight > 1.0) {
			throw new RuntimeException("Blend weight out of range");
		}
		if (p == null) {
			throw new RuntimeException("Blend pixel is null");
		}

		return new ColorPixel(getRed() * weight + p.getRed() * (1.0 - weight),
				getGreen() * weight + p.getGreen() * (1.0 - weight), getBlue() * weight + p.getBlue() * (1.0 - weight));
	}

	/*
	 * the equals method takes in a parameter of a pixel (cannot be null) it
	 * determines if two pixels are equal
	 */

	@Override
	public boolean equals(Pixel p) {
		if (p == null) {
			throw new RuntimeException("Pixel passed to equals method is null");
		}

		double max_intensity = getIntensity() > p.getIntensity() ? getIntensity() : p.getIntensity();
		double equal_bound = max_intensity * 0.10;
		return ((Math.abs(getRed() - p.getRed()) < equal_bound) && (Math.abs(getGreen() - p.getGreen()) < equal_bound)
				&& (Math.abs(getBlue() - p.getBlue()) < equal_bound));
	}

}
