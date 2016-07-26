package kovalevsky.imaging;

public enum Algorithms {
  
  ORIGINAL("Original"),
  LAPLACIAN("Laplacian"),
  SOBEL ("Sobel"),
  SIGMA ("Sigma"),
  MEDIAN ("Median"),
  FAST_AVERAGE ("Fast Average"),
  FAST_AVERAGE_ZERO_PADDED ("Fast Average Zero Padded"),
  FAST_AVERAGE_BOUNDARY ("Fast Average Zero Boundary"),
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
  
  public static Algorithms fromString(String text) {
    if (text != null) {
      for (Algorithms b : Algorithms.values()) {
        if (text.equalsIgnoreCase(b.getName())) {
          return b;
        }
      }
    }
    return null;
  }
  


}
