package a6novice;

public interface Picture extends Iterable<Pixel> {

	/*
	 * This interface provides methods that all Pictures must declare and
	 * implement
	 * 
	 * Two methods are overloaded, the setPixel, extract, and getPixel methods,
	 * meaning they have two different sets of parameters
	 */

	public int getWidth();

	public int getHeight();

	public void setPixel(int x, int y, Pixel p);

	public Pixel getPixel(int x, int y);

	public int countRange(double low, double high);

	public void print();

	public SubPicture extract(int xOffset, int yOffset, int width, int height);

	public void setPixel(Coordinate c, Pixel p);

	public Pixel getPixel(Coordinate c);

	public SubPicture extract(Coordinate corner_a, Coordinate corner_b);

}
