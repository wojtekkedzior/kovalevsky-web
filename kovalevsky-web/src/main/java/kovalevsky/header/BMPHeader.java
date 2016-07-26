package kovalevsky.header;



public class BMPHeader extends Header {

	//Windows Bitmap headers are always 1078, so this can be hard coded.

	//=======Keys===============================================================
	public static final String SIGNATURE = "Signature";
	public static final String SIZE = "Size";
	public static final String OFFSET = "Offset";
	public static final String RESERVED1 = "Reserved 1";
	public static final String RESERVED2 = "Reserved 2";
	public static final String DATA = "Data";
	public static final String BIT_MAP_STRUCT = "Bitmap Structre";
	public static final String WIDTH = "Width";
	public static final String HEIGHT = "Height";
	public static final String PLANES = "Planes";
	public static final String BITS_PER_PIXEL = "Bits per Pixel";
	public static final String COMPRESSION = "Compression";
	public static final String IMAGE_DATA = "Size of Data (with padding)";
	public static final String HORIZONTAL_RES_IN_PIXELS = "Horizontal Resolution in Pixels";
	public static final String VERTICAL_RES_IN_PIXELS = "Vertical Resolution in Pixels";
	public static final String COLORS = "Number of Colors";
	public static final String IMPORTANT_COLORS = "Number of Important Colors";

	//=======Offsets============================================================
	public static final int SIGNATURE_OFFSET = 0;
	public static final int SIZE_OFFSET = 2;
	public static final int RESERVED1_OFFSET = 6;
	public static final int RESERVED2_OFFSET = 8;
	public static final int DATA_OFFSET = 10;
	public static final int BITMAP_STRUCT_OFFSET = 14;
	public static final int WIDTH_OFFSET = 18;
	public static final int HEIGHT_OFFSET = 22;
	public static final int PLANES_OFFSET = 26;
	public static final int BITS_PER_PIXEL_OFFSET = 28;
	public static final int COMPRESSION_OFFSET = 30;
	public static final int IMAGE_DATA_OFFSET = 34;
	public static final int HORIZONTAL_RES_OFFSET = 38;
	public static final int VERTICAL_RES_OFFSET = 42;
	public static final int COLORS_OFFSET = 46;
	public static final int IMPORT_COLORS_OFFSET = 50;

	//=======Sizes==============================================================
	public static final int SIGNATURE_BYTES = 2;
	public static final int SIZE_BYTES = 4;
	public static final int RESERVED1_BYTES = 2;
	public static final int RESERVED2_BYTES = 2;
	public static final int DATA_BYTES = 4;
	public static final int BITMAP_STRUC_BYTES = 4;
	public static final int WIDTH_BYTES = 4;
	public static final int HEIGHT_BYTES = 4;
	public static final int PLANES_BYTES = 2;
	public static final int BITS_PER_PIXEL_BYTES = 2;
	public static final int COMPRESSION_BYTES = 4;
	public static final int IMAGE_DATA_BYTES = 4;
	public static final int HORIZONTAL_RES_BYTES = 4;
	public static final int VERTICAL_RES_BYTES = 4;
	public static final int COLORS_BYTES = 4;
	public static final int IMPORT_COLORS_BYTES = 4;
	
	private String signature;
	private int size;
	private int reserved;
	private int reserved2;
	private int dataOffset;
	private int bitmapStructure;
	private int width;
	private int height;
	private int planes;
	private int bitsPerPixel;
	private int compression;
	private int imageData;
	private int horizontal;
	private int vertical;
	private int colors;
	private int importColors;
	
	public BMPHeader() {
		super();
	}

	public BMPHeader(String signature, int size, int reserved, int reserved2, int dataOffset, int bitmapStructure, int width, int height, int planes, int bitsPerPixel, int compression, int imageData, int horizontal, int vertical, int colors, int importColors) {
		super();
		this.signature = signature;
		this.size = size;
		this.reserved = reserved;
		this.reserved2 = reserved2;
		this.dataOffset = dataOffset;
		this.bitmapStructure = bitmapStructure;
		this.width = width;
		this.height = height;
		this.planes = planes;
		this.bitsPerPixel = bitsPerPixel;
		this.compression = compression;
		this.imageData = imageData;
		this.horizontal = horizontal;
		this.vertical = vertical;
		this.colors = colors;
		this.importColors = importColors;
		
		addHeaderComponent(SIGNATURE, signature, SIGNATURE_OFFSET, SIGNATURE_BYTES);
		addHeaderComponent(SIZE, size + "", SIZE_OFFSET, SIZE_BYTES);
		addHeaderComponent(RESERVED1, reserved + "", RESERVED1_OFFSET, RESERVED1_BYTES);
		addHeaderComponent(RESERVED2, reserved2 + "", RESERVED2_OFFSET, RESERVED2_BYTES);
		addHeaderComponent(DATA, dataOffset + "", DATA_OFFSET, DATA_BYTES);
		addHeaderComponent(BIT_MAP_STRUCT, bitmapStructure + "", BITMAP_STRUCT_OFFSET, BITMAP_STRUC_BYTES);
		addHeaderComponent(WIDTH, width + "", WIDTH_OFFSET, WIDTH_BYTES);
		addHeaderComponent(HEIGHT, height + "", HEIGHT_OFFSET, HEIGHT_BYTES);
		addHeaderComponent(PLANES, planes + "", PLANES_OFFSET, PLANES_BYTES);
		addHeaderComponent(BITS_PER_PIXEL, bitsPerPixel + "", BITS_PER_PIXEL_OFFSET, BITS_PER_PIXEL_BYTES);
		addHeaderComponent(COMPRESSION, compression + "", COMPRESSION_OFFSET, COMPRESSION_BYTES);
		addHeaderComponent(IMAGE_DATA, imageData + "", IMAGE_DATA_OFFSET, IMAGE_DATA_BYTES);
		addHeaderComponent(HORIZONTAL_RES_IN_PIXELS, horizontal + "", HORIZONTAL_RES_OFFSET, HORIZONTAL_RES_BYTES);
		addHeaderComponent(VERTICAL_RES_IN_PIXELS, vertical + "", VERTICAL_RES_OFFSET, VERTICAL_RES_BYTES);
		addHeaderComponent(COLORS, colors + "", COLORS_OFFSET, COLORS_BYTES);
		addHeaderComponent(IMPORTANT_COLORS, importColors + "", IMPORT_COLORS_OFFSET, IMPORT_COLORS_BYTES);
	}
	
	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		addHeaderComponent(SIGNATURE, signature, SIGNATURE_OFFSET, SIGNATURE_BYTES);
		this.signature = signature;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		addHeaderComponent(SIZE, size + "", SIZE_OFFSET, SIZE_BYTES);
		this.size = size;
	}

	public int getReserved() {
		return reserved;
	}

	public void setReserved(int reserved) {
		addHeaderComponent(RESERVED1, reserved + "", RESERVED1_OFFSET, RESERVED1_BYTES);
		this.reserved = reserved;
	}

	public int getReserved2() {
		return reserved2;
	}

	public void setReserved2(int reserved2) {
		addHeaderComponent(RESERVED2, reserved2 + "", RESERVED2_OFFSET, RESERVED2_BYTES);
		this.reserved2 = reserved2;
	}

	public int getDataOffset() {
		return dataOffset;
	}

	public void setDataOffset(int dataOffset) {
		addHeaderComponent(DATA, dataOffset + "", DATA_OFFSET, DATA_BYTES);
		this.dataOffset = dataOffset;
	}

	public int getBitmapStructure() {
		return bitmapStructure;
	}

	public void setBitmapStructure(int bitmapStructure) {
		addHeaderComponent(BIT_MAP_STRUCT, bitmapStructure + "", BITMAP_STRUCT_OFFSET, BITMAP_STRUC_BYTES);
		this.bitmapStructure = bitmapStructure;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		addHeaderComponent(WIDTH, width + "", WIDTH_OFFSET, WIDTH_BYTES);
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		addHeaderComponent(HEIGHT, height + "", HEIGHT_OFFSET, HEIGHT_BYTES);
		this.height = height;
	}

	public int getPlanes() {
		return planes;
	}

	public void setPlanes(int planes) {
		addHeaderComponent(PLANES, planes + "", PLANES_OFFSET, PLANES_BYTES);
		this.planes = planes;
	}

	public int getBitsPerPixel() {
		return bitsPerPixel;
	}

	public void setBitsPerPixel(int bitsPerPixel) {
		addHeaderComponent(BITS_PER_PIXEL, bitsPerPixel + "", BITS_PER_PIXEL_OFFSET, BITS_PER_PIXEL_BYTES);
		this.bitsPerPixel = bitsPerPixel;
	}

	public int getCompression() {
		return compression;
	}

	public void setCompression(int compression) {
		addHeaderComponent(COMPRESSION, compression + "", COMPRESSION_OFFSET, COMPRESSION_BYTES);
		this.compression = compression;
	}

	public int getImageData() {
		return imageData;
	}

	public void setImageData(int imageData) {
		addHeaderComponent(IMAGE_DATA, imageData + "", IMAGE_DATA_OFFSET, IMAGE_DATA_BYTES);
		this.imageData = imageData;
	}

	public int getHorizontal() {
		return horizontal;
	}

	public void setHorizontal(int horizontal) {
		addHeaderComponent(HORIZONTAL_RES_IN_PIXELS, horizontal + "", HORIZONTAL_RES_OFFSET, HORIZONTAL_RES_BYTES);
		this.horizontal = horizontal;
	}

	public int getVertical() {
		return vertical;
	}

	public void setVertical(int vertical) {
		addHeaderComponent(VERTICAL_RES_IN_PIXELS, vertical + "", VERTICAL_RES_OFFSET, VERTICAL_RES_BYTES);
		this.vertical = vertical;
	}

	public int getColors() {
		return colors;
	}

	public void setColors(int colors) {
		addHeaderComponent(COLORS, colors + "", COLORS_OFFSET, COLORS_BYTES);
		this.colors = colors;
	}

	public int getImportColors() {
		return importColors;
	}

	public void setImportColors(int importColors) {
		addHeaderComponent(IMPORTANT_COLORS, importColors + "", IMPORT_COLORS_OFFSET, IMPORT_COLORS_BYTES);
		this.importColors = importColors;
	}
}