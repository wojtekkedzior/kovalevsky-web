package kovalevsky.repositories;

import kovalevsky.domain.VisitCounter;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitCounterRepository extends CrudRepository<VisitCounter, Long> {

  
}