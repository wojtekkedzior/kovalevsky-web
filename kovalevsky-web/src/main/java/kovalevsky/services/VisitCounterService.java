package kovalevsky.services;

import java.util.Date;

import kovalevsky.domain.VisitCounter;
import kovalevsky.repositories.VisitCounterRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class VisitCounterService {

  @Autowired
  private VisitCounterRepository visitCounterRepository;
  
  public void incrementVisitCounter() {
    VisitCounter findOne = visitCounterRepository.findOne(1L);
    
    findOne.setVisitCount(findOne.getVisitCount() + 1);
    findOne.setLastVisited(new Date());
    
    visitCounterRepository.save(findOne);
  }
}
