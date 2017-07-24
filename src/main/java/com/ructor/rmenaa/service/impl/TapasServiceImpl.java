package com.ructor.rmenaa.service.impl;

import com.ructor.rmenaa.service.TapasService;
import com.ructor.rmenaa.domain.Tapas;
import com.ructor.rmenaa.repository.TapasRepository;
import com.ructor.rmenaa.service.dto.TapasDTO;
import com.ructor.rmenaa.service.mapper.TapasMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * Service Implementation for managing Tapas.
 */
@Service
public class TapasServiceImpl implements TapasService{

    private final Logger log = LoggerFactory.getLogger(TapasServiceImpl.class);

    private final TapasRepository tapasRepository;

    private final TapasMapper tapasMapper;

    public TapasServiceImpl(TapasRepository tapasRepository, TapasMapper tapasMapper) {
        this.tapasRepository = tapasRepository;
        this.tapasMapper = tapasMapper;
    }

    /**
     * Save a tapas.
     *
     * @param tapasDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TapasDTO save(TapasDTO tapasDTO) {
        log.debug("Request to save Tapas : {}", tapasDTO);
        Tapas tapas = tapasMapper.toEntity(tapasDTO);
        tapas = tapasRepository.save(tapas);
        return tapasMapper.toDto(tapas);
    }

    /**
     *  Get all the tapas.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    public Page<TapasDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Tapas");
        return tapasRepository.findAll(pageable)
            .map(tapasMapper::toDto);
    }

    /**
     *  Get one tapas by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public TapasDTO findOne(String id) {
        log.debug("Request to get Tapas : {}", id);
        Tapas tapas = tapasRepository.findOne(id);
        return tapasMapper.toDto(tapas);
    }

    /**
     *  Delete the  tapas by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Tapas : {}", id);
        tapasRepository.delete(id);
    }
}
