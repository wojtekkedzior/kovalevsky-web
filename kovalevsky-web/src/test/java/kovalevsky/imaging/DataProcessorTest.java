package kovalevsky.imaging;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import kovalevsky.imaging.formats.BMPFormat;
import kovalevsky.imaging.formats.ImageFormat;

public class DataProcessorTest {
	
	DataProcessor process;
	private ImageFormat image;
	private int[] rawFileData;
	private int hWind;
	
	@Before
	public void setup() {
		process = new DataProcessor();
		
		hWind = 3;
		rawFileData = new int[]{1, 1, 1, 1, 1,
								2, 2, 2, 2, 2,
								3, 3, 3, 3, 3,
								4, 4, 4, 4, 4,
								5, 5, 5, 5, 5};
		
		
		
//		image= new BMPFormat(rawFileData); 
		
	}

	@Test
	public void testMedian() {
		fail("Not yet implemented");
	}

	@Test
	public void testNewLaplacian() {
		fail("Not yet implemented");
	}

	@Test
	public void testSobelFilter() {
		fail("Not yet implemented");
	}

	@Test
	public void testSigma() {
		fail("Not yet implemented");
	}

	@Test
	public void testFastAverage() {
		fail("Not yet implemented");
	}

//	@Test
//	public void testFastAverageZeroBoundry() {
//		ImageFormat fastAverageZeroBoundry = process.fastAverageZeroBoundry(image, hWind);
//		System.err.println(fastAverageZeroBoundry);
//		
//	}
	
	@Test
	public void testFastAverageZeroBoundry() {
		int[] fastAverageZeroBoundry = process.fastAverageZeroBoundry(rawFileData, 5, 5,1);
		System.err.println(fastAverageZeroBoundry);
	}

	@Test
	public void testFastAverageZeroPadded() {
		fail("Not yet implemented");
	}

	@Test
	public void testFastAverageReflected() {
		fail("Not yet implemented");
	}

}
