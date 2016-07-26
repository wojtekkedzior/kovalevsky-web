package kovalevsky.repositories;

import kovalevsky.domain.AlgorithmUsage;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlgorithmUsageRepository extends CrudRepository<AlgorithmUsage, Long> {

  AlgorithmUsage findByAlgorithm(String algorithm);
  
}