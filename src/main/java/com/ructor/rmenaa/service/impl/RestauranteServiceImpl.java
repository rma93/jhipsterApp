package com.ructor.rmenaa.service.impl;

import com.ructor.rmenaa.service.RestauranteService;
import com.ructor.rmenaa.domain.Restaurante;
import com.ructor.rmenaa.repository.RestauranteRepository;
import com.ructor.rmenaa.service.dto.RestauranteDTO;
import com.ructor.rmenaa.service.mapper.RestauranteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * Service Implementation for managing Restaurante.
 */
@Service
public class RestauranteServiceImpl implements RestauranteService{

    private final Logger log = LoggerFactory.getLogger(RestauranteServiceImpl.class);

    private final RestauranteRepository restauranteRepository;

    private final RestauranteMapper restauranteMapper;

    public RestauranteServiceImpl(RestauranteRepository restauranteRepository, RestauranteMapper restauranteMapper) {
        this.restauranteRepository = restauranteRepository;
        this.restauranteMapper = restauranteMapper;
    }

    /**
     * Save a restaurante.
     *
     * @param restauranteDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RestauranteDTO save(RestauranteDTO restauranteDTO) {
        log.debug("Request to save Restaurante : {}", restauranteDTO);
        Restaurante restaurante = restauranteMapper.toEntity(restauranteDTO);
        restaurante = restauranteRepository.save(restaurante);
        return restauranteMapper.toDto(restaurante);
    }

    /**
     *  Get all the restaurantes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    public Page<RestauranteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Restaurantes");
        return restauranteRepository.findAll(pageable)
            .map(restauranteMapper::toDto);
    }

    /**
     *  Get one restaurante by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public RestauranteDTO findOne(String id) {
        log.debug("Request to get Restaurante : {}", id);
        Restaurante restaurante = restauranteRepository.findOne(id);
        return restauranteMapper.toDto(restaurante);
    }

    /**
     *  Delete the  restaurante by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Restaurante : {}", id);
        restauranteRepository.delete(id);
    }
}
