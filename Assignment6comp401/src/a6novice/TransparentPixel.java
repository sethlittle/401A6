package a6novice;

public interface TransparentPixel extends Pixel {

	/*
	 * This interface is a sub interface of Pixel
	 * 
	 * To be a transparent pixel, a pixel must implement the following methods
	 * in addition to all of the methods given in the Pixel interface class
	 * 
	 */

	public double getTransparency();

	public TransparentPixel blend(TransparentPixel p, double weight);
}
