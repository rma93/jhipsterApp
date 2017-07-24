package com.ructor.rmenaa.repository;

import com.ructor.rmenaa.domain.Valoraciones;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Valoraciones entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ValoracionesRepository extends MongoRepository<Valoraciones,String> {
    
}
