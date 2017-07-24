package com.ructor.rmenaa.repository;

import com.ructor.rmenaa.domain.Tapas;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Tapas entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TapasRepository extends MongoRepository<Tapas,String> {
    
}
