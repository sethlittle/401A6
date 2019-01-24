package a6adept;

public class TransparentColorPixel implements TransparentPixel {

	/*
	 * This class is an implementation of a Transparent Pixel interface
	 * 
	 * This pixel has another field called transparency (0 to 1 scale) that
	 * determines the percent transparency of a Pixel
	 * 
	 */

	private double red;
	private double green;
	private double blue;
	private double transparency;

	/*
	 * There are 3 constants used as the intensity factors, these were given to
	 * us in the assignment description
	 * 
	 */

	private static final double RED_INTENSITY_FACTOR = 0.299;
	private static final double GREEN_INTENSITY_FACTOR = 0.587;
	private static final double BLUE_INTENSITY_FACTOR = 0.114;

	/*
	 * These constants will be used for the lighten and darken methods, and the
	 * character map will determine a character that represents certain
	 * intensities
	 */

	private static final Pixel WHITE_PIXEL = new GrayPixel(1.0);
	private static final Pixel BLACK_PIXEL = new GrayPixel(0.0);
	private static final char[] PIXEL_CHAR_MAP = { '#', 'M', 'X', 'D', '<', '>', 's', ':', '-', ' ', ' ' };

	public TransparentColorPixel(double r, double g, double b, double t) {

		/*
		 * The parameters must all have values between 0 and 1
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
		if (t > 1.0 || t < 0.0) {
			throw new RuntimeException("Transparency out of bounds");
		}

		red = r;
		green = g;
		blue = b;
		transparency = t;
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
	 * The intensity uses the constants of the intensity factors and multiplies
	 * them times the red green and blue values to get an intensity value
	 * 
	 */

	@Override
	public double getIntensity() {
		return RED_INTENSITY_FACTOR * getRed() + GREEN_INTENSITY_FACTOR * getGreen()
				+ BLUE_INTENSITY_FACTOR * getBlue();
	}

	/*
	 * The getChar() method returns the character that represents an intensity.
	 * It casts an integer value on a double to determine the index of the array
	 * of characters
	 */

	@Override
	public char getChar() {
		int char_idx = (int) (getIntensity() * 10.0);
		return PIXEL_CHAR_MAP[char_idx];
	}

	/*
	 * This method uses the blend method to return a new TransparentColorPixel
	 * with the lightened values of red green and blue
	 * 
	 * The factor value determines the amount of white that the pixel will
	 * change
	 */

	@Override
	public Pixel lighten(double factor) {
		if (factor < 0.0 || factor > 1.0) {
			throw new RuntimeException("Lighten factor out of range");
		}
		Pixel lightened_base = WHITE_PIXEL.blend(this, factor);
		return new TransparentColorPixel(lightened_base.getRed(), lightened_base.getBlue(), lightened_base.getGreen(),
				getTransparency());
	}

	/*
	 * The darken method is similar to lighten except it uses a black Pixel and
	 * blends. This produces a darker pixel
	 * 
	 * The factor determines the degree of darkness added to the Pixel
	 */

	@Override
	public Pixel darken(double factor) {
		if (factor < 0.0 || factor > 1.0) {
			throw new RuntimeException("Darken factor out of range");
		}
		Pixel darkened_base = BLACK_PIXEL.blend(this, factor);
		return new TransparentColorPixel(darkened_base.getRed(), darkened_base.getBlue(), darkened_base.getGreen(),
				getTransparency());
	}

	/*
	 * The blend method takes in a pixel (cannot be null) and a weight (0 to 1
	 * scale) and changes the weight slightly by multiplying it by 1 -
	 * transparency
	 * 
	 * Then a new Color Pixel is created using the new values of the red green
	 * and blue fields
	 * 
	 * This Color pixel will need to be transformed back into a transparent
	 * pixel by using the same transparency value as the transparent pixel
	 * called
	 * 
	 */

	@Override
	public Pixel blend(Pixel p, double weight) {
		if (weight < 0.0 || weight > 1.0) {
			throw new RuntimeException("Blend weight out of range");
		}
		if (p == null) {
			throw new RuntimeException("Blend pixel is null");
		}

		weight = weight * (1.0 - getTransparency());

		return new ColorPixel(getRed() * weight + p.getRed() * (1.0 - weight),
				getGreen() * weight + p.getGreen() * (1.0 - weight), getBlue() * weight + p.getBlue() * (1.0 - weight));
	}

	public TransparentPixel blend(TransparentPixel p, double weight) {
		Pixel blended_base = blend((Pixel) p, weight);

		return new TransparentColorPixel(blended_base.getRed(), blended_base.getGreen(), blended_base.getBlue(),
				getTransparency() * p.getTransparency());
	}

	/*
	 * The equals method takes in a pixel p (cannot be null) and determines if
	 * the pixel the method was called on and the pixel parameter are equal by
	 * comparing the absolute differences of the green blue and red fields
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

	@Override
	public double getTransparency() {
		return transparency;
	}

}
