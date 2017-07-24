package com.ructor.rmenaa.service.impl;

import com.ructor.rmenaa.service.ValoracionesService;
import com.ructor.rmenaa.domain.Valoraciones;
import com.ructor.rmenaa.repository.ValoracionesRepository;
import com.ructor.rmenaa.service.dto.ValoracionesDTO;
import com.ructor.rmenaa.service.mapper.ValoracionesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Valoraciones.
 */
@Service
public class ValoracionesServiceImpl implements ValoracionesService{

    private final Logger log = LoggerFactory.getLogger(ValoracionesServiceImpl.class);

    private final ValoracionesRepository valoracionesRepository;

    private final ValoracionesMapper valoracionesMapper;

    public ValoracionesServiceImpl(ValoracionesRepository valoracionesRepository, ValoracionesMapper valoracionesMapper) {
        this.valoracionesRepository = valoracionesRepository;
        this.valoracionesMapper = valoracionesMapper;
    }

    /**
     * Save a valoraciones.
     *
     * @param valoracionesDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ValoracionesDTO save(ValoracionesDTO valoracionesDTO) {
        log.debug("Request to save Valoraciones : {}", valoracionesDTO);
        Valoraciones valoraciones = valoracionesMapper.toEntity(valoracionesDTO);
        valoraciones = valoracionesRepository.save(valoraciones);
        return valoracionesMapper.toDto(valoraciones);
    }

    /**
     *  Get all the valoraciones.
     *
     *  @return the list of entities
     */
    @Override
    public List<ValoracionesDTO> findAll() {
        log.debug("Request to get all Valoraciones");
        return valoracionesRepository.findAll().stream()
            .map(valoracionesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one valoraciones by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public ValoracionesDTO findOne(String id) {
        log.debug("Request to get Valoraciones : {}", id);
        Valoraciones valoraciones = valoracionesRepository.findOne(id);
        return valoracionesMapper.toDto(valoraciones);
    }

    /**
     *  Delete the  valoraciones by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Valoraciones : {}", id);
        valoracionesRepository.delete(id);
    }
}
