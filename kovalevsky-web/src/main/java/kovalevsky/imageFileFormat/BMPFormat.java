/*
 * BMPFormat.java
 *
 * Created on 12 August 2006, 18:58
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package kovalevsky.imageFileFormat;

import kovalevsky.header.BMPHeader;
import kovalevsky.header.Header;

/**
 * Provides support for the Window Bitmap format.
 *
 * @author wojtek
 */
public final class BMPFormat extends ImageFormat {
	
	private int[] colorPalette;
	private int offSet;
	private Header header;
	
	public BMPFormat(int[] rawFileData) {
		header = new BMPHeader(); 
		extractHeader(rawFileData);
		colorPalette = extractPallette(rawFileData);
		extractData(rawFileData);
	}
	
	public byte[] getCompleteImageAsByteArray() {
	  int[] completeImage = getCompleteImage(rawImageData);
	  byte[] bytes = new byte[completeImage.length];

      for (int i = 0; i < completeImage.length; i++) {
        bytes[i] = (byte) completeImage[i];
      }
      
      return bytes;
	}

	public int[] getCompleteImage(int[] rawImageData) {
		int[] completeImage = new int[1078 + rawImageData.length + 2];

		String signature;
		int size = 0;
		int offset = 0;
		int width = 0;
		int height = 0;
		int planes = 0;
		int bitsPerPixel = 0;
		int compression = 0;
		int sizeOfData = 0;
		int horizontalResInPixels = 0;
		int verticalResInPixels = 0;
		int numOfOriColors = 0;
		int numOfImpColors = 0;

		//==================================================================
		//SIGNATURE                                                        
		//==================================================================
		signature = getHeader().getSignature();
		if (signature.equals("424d")) {
			completeImage[0] = 66;
			completeImage[1] = 77;
		}

		//==================================================================
		//SIZE
		//==================================================================
		size = getHeader().getSize();
		completeImage[2] = getFirstPlaceHolder(size);
		completeImage[3] = getSecondPlaceHolder(size);
		completeImage[4] = getThirdPlaceHolder(size);
		completeImage[5] = 0; //huge number, not required (65536 * 65536)

		//==================================================================
		//RESERVED
		//==================================================================
		for (int i = 6; i < 10; i++) {
			completeImage[i] = 0;
		}

		//==================================================================
		//OFFEST
		//==================================================================
		offset = getHeader().getDataOffset();
		completeImage[10] = getFirstPlaceHolder(offset);
		completeImage[11] = getSecondPlaceHolder(offset);
		completeImage[12] = getThirdPlaceHolder(offset);
		completeImage[13] = 0; //huge number, not required (65536 * 65536)

		//==================================================================
		//BITMAPINFOHEADER
		//==================================================================              
		completeImage[14] = 40;
		completeImage[15] = 0;
		completeImage[16] = 0;
		completeImage[17] = 0;

		//==================================================================
		//Width in pixels
		//==================================================================
		width = getHeader().getWidth();
		completeImage[18] = getFirstPlaceHolder(width);
		completeImage[19] = getSecondPlaceHolder(width);
		completeImage[20] = 0;
		completeImage[21] = 0;

		//==================================================================
		//Height in pixels
		//================================================================== 
		height = getHeader().getHeight();
		completeImage[22] = getFirstPlaceHolder(height);
		completeImage[23] = getSecondPlaceHolder(height);
		completeImage[24] = 0;
		completeImage[25] = 0;

		//==================================================================
		//Number of planes
		//==================================================================
		planes = getHeader().getPlanes();
		completeImage[26] = getFirstPlaceHolder(planes);
		completeImage[27] = getSecondPlaceHolder(planes);

		//==================================================================
		//Number of bits per pixel
		//================================================================== 
		bitsPerPixel = getHeader().getBitsPerPixel();
		completeImage[28] = getFirstPlaceHolder(bitsPerPixel);
		completeImage[29] = getSecondPlaceHolder(bitsPerPixel);

		//==================================================================
		//COMPRESSION
		//================================================================== 
		compression = getHeader().getCompression();
		completeImage[30] = compression;
		completeImage[31] = 0;
		completeImage[32] = 0;
		completeImage[33] = 0;

		//==================================================================
		//Size of data in bytes
		//================================================================== 
		sizeOfData = getHeader().getImageData();
		completeImage[34] = getFirstPlaceHolder(sizeOfData);
		completeImage[35] = getSecondPlaceHolder(sizeOfData);
		completeImage[36] = getThirdPlaceHolder(sizeOfData);
		completeImage[37] = 0; //huge number

		//==================================================================
		//Horizontal Resolution in Pixels
		//==================================================================
		horizontalResInPixels = getHeader().getHorizontal();
		completeImage[38] = getFirstPlaceHolder(horizontalResInPixels);
		completeImage[39] = getSecondPlaceHolder(horizontalResInPixels);
		completeImage[40] = getThirdPlaceHolder(horizontalResInPixels);
		completeImage[41] = 0;

		//==================================================================
		//Vertical Resolution in Pixels
		//==================================================================     
		verticalResInPixels = getHeader().getVertical();
		completeImage[42] = getFirstPlaceHolder(verticalResInPixels);
		completeImage[43] = getSecondPlaceHolder(verticalResInPixels);
		completeImage[44] = getThirdPlaceHolder(verticalResInPixels);
		completeImage[45] = 0;

		//==================================================================
		//Number of Original Colors
		//==================================================================
		numOfOriColors = getHeader().getColors();
		completeImage[46] = getFirstPlaceHolder(numOfOriColors);
		completeImage[47] = getSecondPlaceHolder(numOfOriColors);
		completeImage[48] = getThirdPlaceHolder(numOfOriColors);
		completeImage[49] = 0;

		//==================================================================
		//Number of Important Colors
		//==================================================================         
		numOfImpColors = getHeader().getImportColors();
		completeImage[50] = getFirstPlaceHolder(numOfImpColors);
		completeImage[51] = getSecondPlaceHolder(numOfImpColors);
		completeImage[52] = getThirdPlaceHolder(numOfImpColors);
		completeImage[53] = 0;

		addPalete(completeImage);
		addData(rawImageData, completeImage);

		//set last two bits to 0 for support with Adobe Photoshop CZ2
		completeImage[completeImage.length - 2] = 0;
		completeImage[completeImage.length - 1] = 0;

		return completeImage;
	}

	private void addData(int[] data, int[] output) {
		for (int i = 0; i < data.length; i++) {
			output[i + 1078] = data[i];
		}
	}

	private void addPalete(int[] output) {
		for (int i = 0; i < colorPalette.length; i++) {
			output[(i + 54)] = colorPalette[i];
		}
	}
	
	@Override
	public BMPHeader getHeader() {
		return  (BMPHeader) header;
	}

	/**
	 * Returns the color palette found in a bitmap header.
	 *
	 * @return a int array, which makes up the color palette.
	 */
	public int[] getColorPalette() {
		return colorPalette;
	}

	/**
	 * Extrats the bytes that make up the header.  First 54 bytes make up the      
	 * header, the remaining 1024 bytes make up the color palete.  This method 
	 * populates two fields; a array and a hashmap, both hold the same data, 
	 * but the hash map provides an easier way to access and modify the header 
	 * data.
	 * @param wholeFile 
	 */
	private void extractHeader(int[] wholeFile) {
		String signature;
		int size = 0;
		int bitMapInfoHeader = 0;
		int planes = 0;
		int bitsPerPixel = 0;
		int compression = 0;
		int sizeOfDataAndPadding = 0;
		int horizontalResInPixels = 0;
		int verticalResInPixels = 0;
		int numOfColors = 0;
		int numOfImportantColors = 0;

		//Signature
		signature = Integer.toHexString(wholeFile[0]) + Integer.toHexString(wholeFile[1]);
		getHeader().setSignature(signature);

		//UNRELIABLE    
		//Size
		size = wholeFile[2] + (wholeFile[3] * 256) + (wholeFile[4] * 65536);
		getHeader().setSize(size);

		//not needed
		getHeader().setReserved(0);
		getHeader().setReserved2(0);

		//Data Offset
		offSet = wholeFile[10] + (wholeFile[11] * 256) + (wholeFile[12] * 65536);
		getHeader().setDataOffset(offSet);

		//BITMAPINFOHEADER, must equal 40
		for (int i = 14; i < 18; i++) {
			bitMapInfoHeader = bitMapInfoHeader + wholeFile[i];
		}
		getHeader().setBitmapStructure(bitMapInfoHeader);

		//Width in pixels
		width = wholeFile[18] + (wholeFile[19] * 256) + (wholeFile[20] * 65536);
		getHeader().setWidth(width);

		//Height in pixels     
		height = wholeFile[22] + (wholeFile[23] * 256) + (wholeFile[24] * 65536);
		getHeader().setHeight(height);
		
		//Number of planes
		for (int i = 26; i < 28; i++) {
			planes = planes + wholeFile[i];
		}
		getHeader().setPlanes(planes);

		//Number of bits per pixel
		for (int i = 28; i < 30; i++) {
			bitsPerPixel = bitsPerPixel + wholeFile[i];
		}
		getHeader().setBitsPerPixel(bitsPerPixel);

		//Compression
		for (int i = 30; i < 34; i++) {
			compression = compression + wholeFile[i];
		}
		getHeader().setCompression(compression);

		//Size of data in bytes
		sizeOfDataAndPadding = wholeFile[34] = (wholeFile[35] * 256) + (wholeFile[36] * 65536);
		getHeader().setImageData(sizeOfDataAndPadding);

		//unreliable-------------
		//Horizontal res in pixels
		horizontalResInPixels = wholeFile[38] + (wholeFile[39] * 256) + (wholeFile[40] * 65536);
		getHeader().setHorizontal(horizontalResInPixels);

		//Vertical res in pixels
		verticalResInPixels = wholeFile[42] + (wholeFile[43] * 256) + (wholeFile[44] * 65536);
		getHeader().setVertical(verticalResInPixels);

		//Number of original Colors
		numOfColors = wholeFile[46] + (wholeFile[47] * 256) + (wholeFile[48] * 65536);
		getHeader().setColors(numOfColors);

		//Number of important original Colors
		numOfImportantColors = wholeFile[50] + (wholeFile[51] * 256) + (wholeFile[52] * 65536);
		getHeader().setImportColors(numOfImportantColors);
	}

	private int[] extractPallette(int[] wholeFile) {
		colorPalette = new int[1024];
		for (int i = 54; i < 1078; i++) {
			colorPalette[i - 54] = wholeFile[i];
		}
		return colorPalette;
	}

	/**
	 * Extracts the data representing the image itself.  The extraction start point 
	 * is normally the 1079th byte.  
	 * The last three bytes are also removed as two of them are 0s and the very 
	 * last byte is a -1, which indicated end of file.
	 * @param wholeFile 
	 */
	private void extractData(int[] wholeFile) {
		//-3 to get rid of the last 3 bytes, which are not part of the image data
		rawImageData = new int[wholeFile.length - offSet - 3];

		for (int i = 0; i < rawImageData.length; i++) {
			rawImageData[i] = wholeFile[i + offSet];
		}
	}

	/**
	 * Calculates the least significant byte.  If viewing data read in from a 
	 * bitmap file this is the left most byte of each component.  
	 */
	private int getFirstPlaceHolder(int value) {
		int result = (value % 65536) % 256;
		return result;
	}

	/**
	 * Calculates the second least significant byte.
	 */
	private int getSecondPlaceHolder(int value) {
		int result = (value % 65536) / 256;
		return result;
	}

	/**
	 * Calculates the third least significant byte.
	 */
	private int getThirdPlaceHolder(int value) {
		int result = value / 65536;
		return result;
	}
}