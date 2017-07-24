package com.ructor.rmenaa.service;

import com.ructor.rmenaa.service.dto.RestauranteDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Restaurante.
 */
public interface RestauranteService {

    /**
     * Save a restaurante.
     *
     * @param restauranteDTO the entity to save
     * @return the persisted entity
     */
    RestauranteDTO save(RestauranteDTO restauranteDTO);

    /**
     *  Get all the restaurantes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RestauranteDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" restaurante.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RestauranteDTO findOne(String id);

    /**
     *  Delete the "id" restaurante.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
