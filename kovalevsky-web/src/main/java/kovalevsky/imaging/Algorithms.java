package kovalevsky.imaging;

public enum Algorithms {
  
  ORIGINAL("Original"),
  LAPLACIAN("Laplacian"),
  SOBEL ("Sobel"),
  SIGMA ("Sigma"),
  MEDIAN ("Median"),
  FAST_AVERAGE ("Fast Average"),
  FAST_AVERAGE_ZERO_PADDED ("Fast Average Zero Padded"),
  FAST_AVERAGE_BOUNDARY ("Fast Average Boundary"),
  FAST_AVERAGE_REFLECTED ("Fast Average Reflected");
  
  private String name;

  private Algorithms(String name) {
    this.setName(name);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
