package kovalevsky.services;

import kovalevsky.app.Algorithms;
import kovalevsky.domain.AlgorithmUsage;
import kovalevsky.repositories.AlgorithmUsageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AlgorithmUsageService {

  @Autowired
  private AlgorithmUsageRepository algorithmUsageRepository;
  
  public void incrementAlgorithmUsage(Algorithms algorithm) {
    AlgorithmUsage findByAlgorithm = algorithmUsageRepository.findByAlgorithm(algorithm.getName());
    findByAlgorithm.setUsageCount(findByAlgorithm.getUsageCount() + 1);
    algorithmUsageRepository.save(findByAlgorithm);
  }
}
