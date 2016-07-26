package kovalevsky.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class VisitCounter {
  
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private long id;
  
  @Column(name="visit_count")
  private int visitCount;
  
  @Column(name="last_visited")
  @Temporal(TemporalType.TIMESTAMP)
  private Date lastVisited;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public int getVisitCount() {
    return visitCount;
  }

  public void setVisitCount(int visitCount) {
    this.visitCount = visitCount;
  }

  public Date getLastVisited() {
    return lastVisited;
  }

  public void setLastVisited(Date lastVisited) {
    this.lastVisited = lastVisited;
  }

  
  
  
}
