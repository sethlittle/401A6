package a6jedi;

public interface Pixel {

	/*
	 * Pixel interface defines methods all Pixel classes must declare and
	 * implement, including get methods for the characteristics of Pixels
	 */

	public double getRed();

	public double getBlue();

	public double getGreen();

	public double getIntensity();

	public char getChar();

	public Pixel blend(Pixel p, double weight);

	public Pixel lighten(double factor);

	public Pixel darken(double factor);

	public boolean equals(Pixel p);
}
