package com.ructor.rmenaa.repository;

import com.ructor.rmenaa.domain.Restaurante;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Restaurante entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RestauranteRepository extends MongoRepository<Restaurante,String> {
    
}
