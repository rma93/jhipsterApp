package com.ructor.rmenaa.service;

import com.ructor.rmenaa.service.dto.UsuarioDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Usuario.
 */
public interface UsuarioService {

    /**
     * Save a usuario.
     *
     * @param usuarioDTO the entity to save
     * @return the persisted entity
     */
    UsuarioDTO save(UsuarioDTO usuarioDTO);

    /**
     *  Get all the usuarios.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<UsuarioDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" usuario.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    UsuarioDTO findOne(String id);

    /**
     *  Delete the "id" usuario.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
