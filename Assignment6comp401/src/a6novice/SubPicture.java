package a6novice;

public interface SubPicture extends Picture {

	/*
	 * This interface adds on to the Picture interface, SubPicture is a subclass
	 * of Picture
	 * 
	 * SubPictures must declare and implement all the methods listed in the
	 * Picture interface, and all the methods listed below
	 * 
	 */

	public Picture getSource();

	public int getXOffset();

	public int getYOffset();

}
