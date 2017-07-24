package com.ructor.rmenaa.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ructor.rmenaa.service.TapasService;
import com.ructor.rmenaa.web.rest.util.HeaderUtil;
import com.ructor.rmenaa.web.rest.util.PaginationUtil;
import com.ructor.rmenaa.service.dto.TapasDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Tapas.
 */
@RestController
@RequestMapping("/api")
public class TapasResource {

    private final Logger log = LoggerFactory.getLogger(TapasResource.class);

    private static final String ENTITY_NAME = "tapas";

    private final TapasService tapasService;

    public TapasResource(TapasService tapasService) {
        this.tapasService = tapasService;
    }

    /**
     * POST  /tapas : Create a new tapas.
     *
     * @param tapasDTO the tapasDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tapasDTO, or with status 400 (Bad Request) if the tapas has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tapas")
    @Timed
    public ResponseEntity<TapasDTO> createTapas(@RequestBody TapasDTO tapasDTO) throws URISyntaxException {
        log.debug("REST request to save Tapas : {}", tapasDTO);
        if (tapasDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new tapas cannot already have an ID")).body(null);
        }
        TapasDTO result = tapasService.save(tapasDTO);
        return ResponseEntity.created(new URI("/api/tapas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tapas : Updates an existing tapas.
     *
     * @param tapasDTO the tapasDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tapasDTO,
     * or with status 400 (Bad Request) if the tapasDTO is not valid,
     * or with status 500 (Internal Server Error) if the tapasDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tapas")
    @Timed
    public ResponseEntity<TapasDTO> updateTapas(@RequestBody TapasDTO tapasDTO) throws URISyntaxException {
        log.debug("REST request to update Tapas : {}", tapasDTO);
        if (tapasDTO.getId() == null) {
            return createTapas(tapasDTO);
        }
        TapasDTO result = tapasService.save(tapasDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tapasDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tapas : get all the tapas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tapas in body
     */
    @GetMapping("/tapas")
    @Timed
    public ResponseEntity<List<TapasDTO>> getAllTapas(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Tapas");
        Page<TapasDTO> page = tapasService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tapas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tapas/:id : get the "id" tapas.
     *
     * @param id the id of the tapasDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tapasDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tapas/{id}")
    @Timed
    public ResponseEntity<TapasDTO> getTapas(@PathVariable String id) {
        log.debug("REST request to get Tapas : {}", id);
        TapasDTO tapasDTO = tapasService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tapasDTO));
    }

    /**
     * DELETE  /tapas/:id : delete the "id" tapas.
     *
     * @param id the id of the tapasDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tapas/{id}")
    @Timed
    public ResponseEntity<Void> deleteTapas(@PathVariable String id) {
        log.debug("REST request to delete Tapas : {}", id);
        tapasService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
