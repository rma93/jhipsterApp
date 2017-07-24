package com.ructor.rmenaa.service;

import com.ructor.rmenaa.service.dto.TapasDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Tapas.
 */
public interface TapasService {

    /**
     * Save a tapas.
     *
     * @param tapasDTO the entity to save
     * @return the persisted entity
     */
    TapasDTO save(TapasDTO tapasDTO);

    /**
     *  Get all the tapas.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TapasDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" tapas.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TapasDTO findOne(String id);

    /**
     *  Delete the "id" tapas.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
