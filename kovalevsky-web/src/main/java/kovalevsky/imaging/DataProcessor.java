/*
 * Algorithm.java
 *
 * Created on 9 September 2006, 21:35
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package kovalevsky.imaging;

import java.util.ArrayList;
import java.util.List;

import kovalevsky.imaging.formats.ImageFormat;

/**
 * Provides all algorithms implemented by Kovalewsky.
 * 
 * @author wojtek, algorithm code re-written. Original code supplied by Professor Kovalevsky
 */
public final class DataProcessor {

  public ImageFormat median(ImageFormat image, int hWind) {
    image.setImageData(median(image.getImageData(), image.getWidth(), image.getHeight(), hWind));
    return image;
  }

  public ImageFormat newLaplacian(ImageFormat image) {
    image.setImageData(newLaplacian(image.getImageData(), image.getWidth(), image.getHeight()));
    return image;
  }

  public ImageFormat sobelFilter(ImageFormat image) {
    image.setImageData(sobelFilter(image.getImageData(), image.getWidth(), image.getHeight()));
    return image;
  }

  public ImageFormat sigma(ImageFormat image, int hWind, int sigma) {
    image.setImageData(sigma(image.getImageData(), image.getWidth(), image.getHeight(), hWind, sigma));
    return image;
  }

  public ImageFormat fastAverage(ImageFormat image, int hWind) {
    image.setImageData(fastAverage(image.getImageData(), image.getWidth(), image.getHeight(), hWind));
    return image;
  }

  public ImageFormat fastAverageZeroBoundry(ImageFormat image, int hWind) {
    image.setImageData(fastAverageZeroBoundry(image.getImageData(), image.getWidth(), image.getHeight(), hWind));
    return image;
  }

  public ImageFormat fastAverageZeroPadded(ImageFormat image, int hWind) {
    image.setImageData(fastAverageZeroPadded(image.getImageData(), image.getWidth(), image.getHeight(), hWind));
    return image;
  }

  public ImageFormat fastAverageReflected(ImageFormat image, int hWind) {
    image.setImageData(fastAverageReflected(image.getImageData(), image.getWidth(), image.getHeight(), hWind));
    return image;
  }

  /**
   * Kovalevsky's Sobel filter.
   * 
   * NOTE: For opimal results smooth the image using a smoothing filter before using the Sobel filter.
   * 
   * @param rawData
   *          raw image data.
   * @param NX
   *          width of image.
   * @param NY
   *          height of image.
   * 
   * @return filtered image data.
   */
  private int[] sobelFilter(int[] rawData, int NX, int NY) {
    int[] out = new int[rawData.length];

    int[][] WeightsH = { { -1, 0, 1 }, { -2, 0, 2 }, { -1, 0, 1 } };
    int[][] WeightsV = { { -1, -2, -1 }, { 0, 0, 0 }, { 1, 2, 1 } };

    for (int y = 1; y < NY - 1; y++) {
      for (int x = 1; x < NX - 1; x++) {
        int SumH = 0;
        int SumV = 0;

        for (int k = -1; k <= 1; k++) {
          for (int i = -1; i <= 1; i++) {
            SumH += WeightsH[k + 1][i + 1] * rawData[x + i + NX * (y + k)];
            SumV += WeightsV[k + 1][i + 1] * rawData[x + i + NX * (y + k)];
          }

          out[x + NX * y] = Math.abs(SumH) + Math.abs(SumV);
        }
      }
    }

    // my adjustment. the final value for each pixel cannot exceed 255.
    for (int i = 0; i < out.length; i++) {
      if (out[i] > 255)
        out[i] = 255;
    }

    return out;
  }

  /**
   * Kovalevsky's Fast Average filter using the Zero Boundary method. This method removes the top and bottom rows and the left and right columns. This results
   * in the image being smaller. This method assumes that the high = width ie NX=NY. This function only changes the data provided. It DOES NOT modify any
   * headers. Modification of headers is left for the user to implement.
   * 
   * @param rawData
   *          raw image data.
   * @param NX
   *          width of image.
   * @param NY
   *          height of image.
   * @param hWind
   *          window size.
   * @return filtered image data.
   */
	public int[] fastAverageZeroBoundry(int[] rawData, int NX, int NY, int hWind) {
		if (NX != NY) {
			//TODO no. need to handle this
			// error an return. 
		}

		ArrayList<int[]> firstList = new ArrayList<int[]>(300000);

		int[] zeroBoundry = fastAverage(rawData, NX, NY, hWind);

		// split the image into rows, there should be NX amount of rows. and
		// then ignore the first and last byte of each row
		//TODO not so sure if we can just ignore the first and last row.  I would imagine that we need to ingore the same amount of pixels as the window size.
		for (int i = 1; i < NX - 1; i++) {
			int x = 1;
			int[] row = new int[NX];

			while (x != (NX - 1)) {
				row[x] = zeroBoundry[(i * NX) + x];
				x++;
			}

			firstList.add(row);
		}

		List<Integer> modifiedDataAsList = new ArrayList<Integer>();

		for (int[] row : firstList) {
			for (int i : row) {
				modifiedDataAsList.add(i);
			}
		}

		int[] arr = modifiedDataAsList.stream().mapToInt(i -> i).toArray();
		//TODO need to log the sizes (including the window size and NX,NY)
		// System.err.println(arr.length);

		return arr;
	}

  // use 0 for al missing pixels
  // Use of this function does not require modification of any image format
  // headers as the
  // output data is the same size as the input data.
  private int[] fastAverageZeroPadded(int[] rawData, int NX, int NY, int hWind) {
    ArrayList<int[]> originalRows = new ArrayList<int[]>();
    int[] row;

    for (int i = 0; i < NY; i++) {
      int x = 0;
      row = new int[NX];

      while (x != NX) {
        {
          row[x] = rawData[(i * NX) + x];
          x++;
        }
      }

      originalRows.add(row);
    }

    // now we need to add 2 rows, 1 at the top and one at the bottom
    int[] topRow = new int[NX];
    int[] bottomRow = new int[NX];

    for (int i = 0; i < topRow.length; i++) {
      topRow[i] = bottomRow[i] = 0;
    }

    originalRows.add(0, topRow);
    originalRows.add(bottomRow);

    // add a 0 to every start position in a row and a 0 at the end.
    int[] biggerRow;

    for (int i = 0; i < originalRows.size(); i++) {
      biggerRow = new int[NX + 2];
      int[] tmp = originalRows.get(i);

      // first and last spaces are 0
      biggerRow[0] = 0;
      biggerRow[biggerRow.length - 1] = 0;

      for (int j = 1; j < (tmp.length + 1); j++) {
        biggerRow[j] = tmp[j - 1];
      }

      originalRows.remove(i);
      originalRows.add(i, biggerRow);
    }

    // put the rows into one array.
    int[] modifiedData = new int[(NY + 2) * (NX + 2)];

    for (int i = 0; i < originalRows.size(); i++) {
      int[] tmp = originalRows.get(i);

      int size = 0;

      while (size < tmp.length) {
        modifiedData[i * tmp.length + size] = tmp[size];
        size++;
      }
    }

    int[] output = fastAverage(modifiedData, (NX + 2), (NY + 2), hWind);

    // Back into rows
    ArrayList<int[]> modifiedRows = new ArrayList<int[]>();
    int[] rowMod;

    for (int i = 0; i < (NY + 2); i++) {
      int x = 0;
      rowMod = new int[NX + 2];

      while (x != NX + 2) {
        rowMod[x] = output[(i * (NX + 2)) + x];
        x++;
      }

      modifiedRows.add(rowMod);
    }

    // remove top and bottom rows
    modifiedRows.remove(0);
    modifiedRows.remove(modifiedRows.size() - 1);

    // remove 1st vale and the last value of each row.
    ArrayList<int[]> backToOriginalSize = new ArrayList<int[]>();

    for (int i = 0; i < modifiedRows.size(); i++) {
      int[] ar = new int[modifiedRows.get(i).length - 2];
      int[] tmp = modifiedRows.get(i);

      for (int j = 0; j < (tmp.length - 2); j++) {
        ar[j] = tmp[j + 1];
      }

      backToOriginalSize.add(ar);
    }

    // Put all the rows into one array
    int[] finalOutPut = new int[NX * NY];

    for (int i = 0; i < backToOriginalSize.size(); i++) {
      int[] tmp = backToOriginalSize.get(i);

      int size = 0;

      while (size < tmp.length) {
        finalOutPut[i * tmp.length + size] = tmp[size];
        size++;
      }
    }

    return finalOutPut;
  }

  private int[] fastAverageReflected(int[] rawData, int NX, int NY, int hWind) {
    ArrayList<int[]> originalRows = new ArrayList<int[]>();
    int[] row;

    for (int i = 0; i < NY; i++) {
      int x = 0;
      row = new int[NX];

      while (x != NX) {
        row[x] = rawData[(i * NX) + x];
        x++;
      }

      originalRows.add(row);
    }

    // now we need to add 2 rows, 1 at the top and one at the bottom
    int[] topRow = new int[NX];
    int[] bottomRow = new int[NX];

    for (int i = 0; i < topRow.length; i++) {
      topRow[i] = bottomRow[i] = 0;
    }

    originalRows.add(0, topRow);
    originalRows.add(bottomRow);

    // add a 0 to every start position in a row and a 0 at the end.
    int[] biggerRow;

    for (int i = 1; i < originalRows.size(); i++) {
      biggerRow = new int[NX + 2];
      int[] tmp = originalRows.get(i);

      // first and last spaces are 0
      biggerRow[0] = tmp[tmp.length - 1];
      biggerRow[biggerRow.length - 1] = tmp[0];

      for (int j = 1; j < (tmp.length + 1); j++) {
        biggerRow[j] = tmp[j - 1];
      }

      originalRows.remove(i);
      originalRows.add(i, biggerRow);
    }

    // do the first row
    int[] newBottomRow = new int[NX + 2];
    int[] originalBottomRow = originalRows.get(1);

    for (int i = 1; i < originalBottomRow.length - 1; i++) {
      newBottomRow[i] = originalBottomRow[i];
    }

    // do the bottom row
    int[] newTopRow = new int[NX + 2];
    int[] originalTopRow = originalRows.get(originalRows.size() - 2);

    for (int i = 1; i < originalTopRow.length - 1; i++) {
      newTopRow[i] = originalTopRow[i];
    }

    // Giving pixels on the corners values
    // Top feft corner
    newBottomRow[0] = rawData[NX - 1];

    // Top right corner
    newBottomRow[newBottomRow.length - 1] = rawData[0];

    // Bottom left corner
    newTopRow[0] = rawData[rawData.length - 1];

    // Bottom right corner
    newTopRow[newTopRow.length - 1] = rawData[rawData.length - NX];

    originalRows.remove(0);
    originalRows.remove(originalRows.size() - 1);

    originalRows.add(0, newTopRow);
    originalRows.add(newBottomRow);

    // take new rows and merge into 1 array

    int[] output = new int[(NX + 2) * (NY + 2)];

    for (int i = 0; i < originalRows.size(); i++) {
      int[] tmp = originalRows.get(i);

      int size = 0;

      while (size < tmp.length) {
        output[i * tmp.length + size] = tmp[size];
        size++;
      }
    }

    output = fastAverage(output, (NX + 2), (NY + 2), hWind);

    // split the output into rows again
    ArrayList<int[]> initRow = new ArrayList<int[]>();
    int[] rowMod;

    for (int i = 0; i < (NY + 2); i++) {
      int x = 0;
      rowMod = new int[NX + 2];

      while (x != NX + 2) {
        rowMod[x] = output[(i * (NX + 2)) + x];
        x++;
      }

      initRow.add(rowMod);
    }

    // remove the first and second bytes from each row
    ArrayList<int[]> backToOriginalSize = new ArrayList<int[]>();

    for (int i = 0; i < initRow.size(); i++) {
      int[] ar = new int[initRow.get(i).length - 2];
      int[] tmp = initRow.get(i);

      for (int j = 0; j < (tmp.length - 2); j++) {
        ar[j] = tmp[j + 1];
      }

      backToOriginalSize.add(ar);
    }

    // put the rows back into one array, but remove the top and bottom rows.
    backToOriginalSize.remove(0);
    backToOriginalSize.remove(backToOriginalSize.size() - 1);

    int[] finalOutput = new int[NX * NY];

    for (int i = 0; i < backToOriginalSize.size(); i++) {
      int[] tmp = backToOriginalSize.get(i);

      int size = 0;

      while (size < tmp.length) {
        finalOutput[i * tmp.length + size] = tmp[size];
        size++;
      }
    }

    return finalOutput;
  }

  /**
   * Kovalevsky's Fast Average filter.
   * 
   * @param rawData
   *          raw image data.
   * @param NX
   *          width of image.
   * @param NY
   *          height of image.
   * @param hWind
   *          window size.
   * @return filtered image data.
   */
  private int[] fastAverage(int[] rawData, int NX, int NY, int hWind) {
    int avr[];

    avr = new int[rawData.length];

    int originalDataSum[] = new int[NX];
    int nC[] = new int[NX];

    // the function will be faster if computing nS=nX*nY through differences
    // as nX=x-xsub
    for (int i = 0; i < NX; i++) {
      originalDataSum[i] = nC[i] = 0;
    }

    int nS = 0;
    int Sum = 0;

    // for(int y = 0; y < NY + hWind; y++) // y for the input
    for (int y = 0; y < NY + hWind; y++) {
      int yout = y - hWind;

      /**
       * Originally this statment read:
       * 
       * int ysub = y - (2 * hWind) - 1;
       * 
       * Mulptiplying the window size (hWind) cause the bluring to be more intensive. The blur effect using a 3 x 3 window would create an image, which look as
       * if it were blured by a 5 x5 window. This was proved using Matlab:
       * 
       * Ifiltered = filter2(fspeial('average' ,5),I)/255;
       * 
       * Where: average - filter type, 5 - window size, I - original image, 255 - the output is provided in double so divide by 255 to obtain an integer value.
       * 
       */
      int ysub = y - (2 * hWind) - 1;

      Sum = 0;
      nS = 0;

      // for (int x = 0; x < (NX + hWind); x++)
      for (int x = 0; x < (NX + hWind); x++) {
        int xout = x - hWind;

        /**
         * Like wise this statment read:
         * 
         * int xsub = x - (1 * hWind) - 1;
         * 
         * The was has been removed for the sake of clarity, because multiplying by one has no effect on the output.
         */
        int xsub = x - (2 * hWind) - 1; // 1. and 2. addition

        if (y < NY && x < NX) {
          originalDataSum[x] += rawData[x + (NX * y)];
          nC[x] = nC[x] + 1; // 3. and 4. addition
        }

        if (ysub >= 0 && x < NX) {
          originalDataSum[x] -= rawData[x + (NX * ysub)];
          nC[x]--;
        }

        if (yout >= 0 && x < NX) {
          Sum += originalDataSum[x];
          nS += nC[x];
        }

        if (yout >= 0 && xsub >= 0) {
          Sum -= originalDataSum[xsub];
          nS -= nC[xsub];
        }

        if (xout >= 0 && yout >= 0) {
          avr[xout + NX * yout] = ((Sum + (nS / 2)) / nS);
        }
      }
    }

    return avr;
  }

  /**
   * Kovalevsky's Median filter.
   * 
   * @param rawData
   *          raw image data.
   * @param NX
   *          width of image.
   * @param NY
   *          height of image.
   * @param hWind
   *          window size.
   * 
   * @return filtered image data.
   */
  private int[] median(int[] rawData, int NX, int NY, int hWind) {
    // The median filter for 1 byte grey level images..
    int[] hist = new int[256];
    int PixInWin = 0;
    ;
    int[] avr = new int[rawData.length];

    for (int y = 0; y < NY; y++) {
      int gv;
      int y1;
      int yStart = Math.max(y - hWind, 0);
      int yEnd = Math.min(y + hWind, NY - 1);
      int gvMin = 255;
      int gvMax = 0;

      for (int x = 0; x < NX; x++) {
        if (x == 0) {
          for (gv = 0; gv < 256; gv++) {
            hist[gv] = 0;
          }

          PixInWin = 0;

          for (y1 = yStart; y1 <= yEnd; y1++) {
            for (int k = 0; k <= hWind; k++) {
              gv = rawData[k + y1 * NX];
              hist[gv]++;
              PixInWin++;

              if (gv < gvMin)
                gvMin = gv;

              if (gv > gvMax)
                gvMax = gv;
            }
          }
        } else {
          int x1 = x + hWind;
          int x2 = x - hWind - 1;

          if (x1 <= NX - 1) {
            for (y1 = yStart; y1 <= yEnd; y1++) {
              gv = rawData[x1 + y1 * NX];
              hist[gv]++;
              PixInWin++;

              if (gv < gvMin)
                gvMin = gv;

              if (gv > gvMax)
                gvMax = gv;
            }
          }

          if (x2 >= 0)
            for (y1 = yStart; y1 <= yEnd; y1++) {
              gv = rawData[x2 + y1 * NX];
              hist[gv]--;
              PixInWin--;

              if (hist[gvMin] == 0) {
                for (gv = gvMin + 1; gv <= gvMax; gv++) {
                  if (hist[gv] > 0) {
                    gvMin = gv;
                    break;
                  }
                }
              }

              if (hist[gvMax] == 0) {
                for (gv = gvMax - 1; gv >= 0; gv--) {
                  if (hist[gv] > 0) {
                    gvMax = gv;
                    break;
                  }
                }
              }
            }
        }

        int nPixel = 0;

        for (gv = gvMin; gv <= gvMax; gv++) {
          nPixel += hist[gv];
          if (nPixel >= PixInWin / 2)
            break;
        }

        avr[x + NX * y] = gv;
      }
    }

    return avr;
  }

  /**
   * Kovalevsky's Sigma filter
   * 
   * @param rawData
   *          raw image data.
   * @param NX
   *          width of image.
   * @param NY
   *          height of image.
   * @param hWind
   *          window size.
   * 
   * @return filtered image data.
   */
  private int[] sigma(int[] rawData, int NX, int NY, int hWind, int sigma) {
    // The sigma filter for 1 byte grey level images..
    int[] hist = new int[256];
    int[] avr = new int[rawData.length];

    int deb = 0;
    for (int y = 0; y < NY; y++) {
      int gv;
      int y1;
      int yStart = Math.max(y - hWind, 0);
      int yEnd = Math.min(y + hWind, NY - 1);

      for (int x = 0; x < NX; x++) {
        if (x == 0) {
          for (gv = 0; gv < 256; gv++) {
            hist[gv] = 0;
          }

          for (y1 = yStart; y1 <= yEnd; y1++) {
            for (int j = 0; j <= hWind; j++) {
              hist[rawData[j + y1 * NX]]++;
            }
          }
        } else {
          int x1 = x + hWind;
          int x2 = x - hWind - 1;

          if (x1 < NX - 1) {
            for (y1 = yStart; y1 <= yEnd; y1++)
              hist[rawData[x1 + y1 * NX]]++;
          }
          if (x2 >= 0) {
            for (y1 = yStart; y1 <= yEnd; y1++) {
              hist[rawData[x2 + y1 * NX]]--;

              if ((deb > 0) && (hist[rawData[x2 + y1 * NX]] < 0)) {
                return null;
              }
            }
          }
        }

        int Sum = 0;
        int nPixel = 0;
        int gvMin = Math.max(0, rawData[x + NX * y] - sigma);
        int gvMax = Math.min(255, rawData[x + NX * y] + sigma);

        for (gv = gvMin; gv <= gvMax; gv++) {
          Sum += gv * hist[gv];
          nPixel += hist[gv];
        }

        if (nPixel > 0)
          avr[x + NX * y] = (Sum + nPixel / 2) / nPixel; // division
        // with
        // rounding
        else
          avr[x + NX * y] = 0;
      }
    }

    return avr;
  }

  /**
   * Calculates the "new Laplacian" by Kovalevsky and saves the results in "out".
   * 
   * @param rawData
   *          raw image data.
   * @param NX
   *          width of image.
   * @param NY
   *          height of image.
   * 
   * @return filtered image data.
   */
  private int[] newLaplacian(int[] rawData, int NX, int NY) {
    int[] out = new int[NX * NY];

    int bias = 0; // scale factor, simiral to brightness
    int gv;
    int DX1;
    int DX2;
    int DXY;
    int DY1;
    int DY2;
    // int Decrement = 50; //this is not used anywhere in this function.
    double GB; // absolut value of the gradient

    for (int y = 1; y < NY - 1; y++) {
      for (int x = 1; x < NX - 1; x++) {
        DX1 = (rawData[x + 1 + NX * y] - rawData[x - 1 + NX * y] + 1) / 2;
        DY1 = (rawData[x + NX * (y + 1)] - rawData[x + NX * (y - 1)] + 1) / 2;
        DX2 = (rawData[x + 1 + NX * y] - 2 * rawData[x + NX * y] + rawData[x - 1 + NX * y]);
        DY2 = (rawData[x + NX * (y + 1)] - 2 * rawData[x + NX * y] + rawData[x + NX * (y - 1)]);
        DXY = (rawData[x + 1 + NX * (y + 1)] + rawData[x - 1 + NX * (y - 1)] - rawData[x - 1 + NX * (y + 1)] - rawData[x + 1 + NX * (y - 1)]) / 4;

        GB = Math.hypot((double) DX1, (double) DY1);

        // GB=hypot(double(DX1),double(DY1));
        // (R'x��R"xx+2�R'x�R'y�R"xy+R'y��R"yy)
        gv = (int) Math.round((DX1 * DX1 * DX2 + 2 * DX1 * DY1 * DXY + DY1 * DY1 * DY2) / GB) + bias;

        if (gv < 0)
          gv = 0;

        if (gv > 255)
          gv = 255;

        out[x + NX * y] = gv;
      }
    }
    return out;
  }
}