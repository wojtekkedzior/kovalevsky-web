package kovalevsky.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AlgorithmUsage {
  
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private long id;
  
  private String algorithm;
  
  private int usageCount;

  
  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getAlgorithm() {
    return algorithm;
  }

  public void setAlgorithm(String algorithm) {
    this.algorithm = algorithm;
  }

  public int getUsageCount() {
    return usageCount;
  }

  public void setUsageCount(int usageCount) {
    this.usageCount = usageCount;
  }
  
  
  

}
