/*
 * ImageFormat.java
 *
 * Created on 12 August 2006, 18:43
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 * 
 * 
 */

package kovalevsky.imageFileFormat;

import kovalevsky.app.CorruptHeaderException;
import kovalevsky.header.Header;

/**
 * Super class to all image format classes.  Defines methods required by all 
 * sub classes.
 *
 * @author wojtek
 */
public abstract class ImageFormat {
	protected int[] rawImageData;
	protected int width;
	protected int height;
//	protected Header header;
	
	abstract public Header getHeader();
	abstract public int[] getCompleteImage(int[] data) throws CorruptHeaderException;
	
	public int[] getImageData() {
		return rawImageData;
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
  public void setImageData(int[] rawImageData) {
    this.rawImageData = rawImageData;
    
  }
  public abstract  byte[] getCompleteImageAsByteArray();
}