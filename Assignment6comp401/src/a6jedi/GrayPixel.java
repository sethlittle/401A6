package a6jedi;

public class GrayPixel implements Pixel {

	/*
	 * This is an implementation of the Pixel interface
	 * 
	 * This class has Red, Green and Blue intensities equal to the overall
	 * intensity of the Pixel
	 * 
	 */

	private double intensity;

	/*
	 * These constants will be used in this class to lighten/darken and get
	 * Character for a set of characters, where the intensity determines what
	 * character is assigned
	 * 
	 */

	private static final Pixel WHITE_PIXEL = new GrayPixel(1.0);
	private static final Pixel BLACK_PIXEL = new GrayPixel(0.0);
	private static final char[] PIXEL_CHAR_MAP = { '#', 'M', 'X', 'D', '<', '>', 's', ':', '-', ' ', ' ' };

	public GrayPixel(double intensity) {

		/*
		 * Intensity values must be within 0 and 1
		 */

		if (intensity < 0.0 || intensity > 1.0) {
			throw new RuntimeException("Intensity value out of range");
		}
		this.intensity = intensity;
	}

	@Override
	public double getRed() {
		return intensity;
	}

	@Override
	public double getBlue() {
		return intensity;
	}

	@Override
	public double getGreen() {
		return intensity;
	}

	@Override
	public double getIntensity() {
		return intensity;
	}

	/*
	 * The getChar() method uses the Intensity and multiplies it by 10 and then
	 * uses the integer value of it to return a character from the constant
	 * character array
	 * 
	 */

	@Override
	public char getChar() {
		return PIXEL_CHAR_MAP[(int) (getIntensity() * 10.0)];
	}

	/*
	 * This lighten method uses the blend method with the constant White Pixel
	 * to return a new Pixel with an average intensity
	 * 
	 * the factor parameter must be within 0 and 1
	 */

	@Override
	public Pixel lighten(double factor) {
		if (factor < 0.0 || factor > 1.0) {
			throw new RuntimeException("Lighten factor out of range");
		}
		return WHITE_PIXEL.blend(this, factor);
	}

	/*
	 * This darken method uses the blend method with the constant Black Pixel to
	 * return a new Pixel with an average intensity of the black pixel and the
	 * pixel used
	 * 
	 * The factor parameter must be within 0 and 1
	 */

	@Override
	public Pixel darken(double factor) {
		if (factor < 0.0 || factor > 1.0) {
			throw new RuntimeException("Darken factor out of range");
		}
		return BLACK_PIXEL.blend(this, factor);
	}

	/*
	 * The blend method takes in a pixel and a weight (between 0 and 1) and
	 * returns a new Pixel with a combined weight of the pixel parameter and the
	 * Pixel the method was called on
	 * 
	 * The pixel cannot be null
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
	 * The equals method takes in a Pixel and uses the max intensity and returns
	 * a boolean value if the pixel and the parameter intensity values have an
	 * absolute value less than the max intensity divided by 10
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
