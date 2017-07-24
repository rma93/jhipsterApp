package com.ructor.rmenaa.service;

import com.ructor.rmenaa.service.dto.ValoracionesDTO;
import java.util.List;

/**
 * Service Interface for managing Valoraciones.
 */
public interface ValoracionesService {

    /**
     * Save a valoraciones.
     *
     * @param valoracionesDTO the entity to save
     * @return the persisted entity
     */
    ValoracionesDTO save(ValoracionesDTO valoracionesDTO);

    /**
     *  Get all the valoraciones.
     *
     *  @return the list of entities
     */
    List<ValoracionesDTO> findAll();

    /**
     *  Get the "id" valoraciones.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ValoracionesDTO findOne(String id);

    /**
     *  Delete the "id" valoraciones.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
