/*
 * JPEGFormat.java
 *
 * Created on 20 August 2006, 15:31
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package kovalevsky.imageFileFormat;

/**
 * Provides support for the JPEG format.  NOT FUNCTIONAL.
 * @author wojtek
 */
public class JPEGFormat {

	/** Creates a new instance of JPEGFormat */
	public JPEGFormat(int[] data) {
		this.data = data;
		header = new int[10];
		extractHeader();
	}

	public void extractHeader() {
		//JPG SOI maker
		header[0] = data[0];
		header[1] = data[1];

		//image width
		header[2] = data[2];
		header[3] = data[3];

		//image height
		header[4] = data[4];
		header[5] = data[5];

		//number of components 
		header[6] = data[6];

		//horizontal/vertical sampling factors for component 1
		header[7] = data[7];

		//sampling factors for component 2 (if RGB)
		header[8] = data[8];

		//sampling factors for component 3 (if RGB)
		header[9] = data[9];
	}

	public int[] getHeader() {
		return header;
	}

	public String getFormat() {
		return "Windows Bitmap";
	}

	public int[] getData() {
		return extractedData;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	private int[] header;
	private int[] data;
	private int[] extractedData;

	private int width = 0;
	private int height = 0;

}
