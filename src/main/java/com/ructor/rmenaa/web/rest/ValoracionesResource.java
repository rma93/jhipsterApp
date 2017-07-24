package com.ructor.rmenaa.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ructor.rmenaa.service.ValoracionesService;
import com.ructor.rmenaa.web.rest.util.HeaderUtil;
import com.ructor.rmenaa.service.dto.ValoracionesDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Valoraciones.
 */
@RestController
@RequestMapping("/api")
public class ValoracionesResource {

    private final Logger log = LoggerFactory.getLogger(ValoracionesResource.class);

    private static final String ENTITY_NAME = "valoraciones";

    private final ValoracionesService valoracionesService;

    public ValoracionesResource(ValoracionesService valoracionesService) {
        this.valoracionesService = valoracionesService;
    }

    /**
     * POST  /valoraciones : Create a new valoraciones.
     *
     * @param valoracionesDTO the valoracionesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new valoracionesDTO, or with status 400 (Bad Request) if the valoraciones has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/valoraciones")
    @Timed
    public ResponseEntity<ValoracionesDTO> createValoraciones(@Valid @RequestBody ValoracionesDTO valoracionesDTO) throws URISyntaxException {
        log.debug("REST request to save Valoraciones : {}", valoracionesDTO);
        if (valoracionesDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new valoraciones cannot already have an ID")).body(null);
        }
        ValoracionesDTO result = valoracionesService.save(valoracionesDTO);
        return ResponseEntity.created(new URI("/api/valoraciones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /valoraciones : Updates an existing valoraciones.
     *
     * @param valoracionesDTO the valoracionesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated valoracionesDTO,
     * or with status 400 (Bad Request) if the valoracionesDTO is not valid,
     * or with status 500 (Internal Server Error) if the valoracionesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/valoraciones")
    @Timed
    public ResponseEntity<ValoracionesDTO> updateValoraciones(@Valid @RequestBody ValoracionesDTO valoracionesDTO) throws URISyntaxException {
        log.debug("REST request to update Valoraciones : {}", valoracionesDTO);
        if (valoracionesDTO.getId() == null) {
            return createValoraciones(valoracionesDTO);
        }
        ValoracionesDTO result = valoracionesService.save(valoracionesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, valoracionesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /valoraciones : get all the valoraciones.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of valoraciones in body
     */
    @GetMapping("/valoraciones")
    @Timed
    public List<ValoracionesDTO> getAllValoraciones() {
        log.debug("REST request to get all Valoraciones");
        return valoracionesService.findAll();
    }

    /**
     * GET  /valoraciones/:id : get the "id" valoraciones.
     *
     * @param id the id of the valoracionesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the valoracionesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/valoraciones/{id}")
    @Timed
    public ResponseEntity<ValoracionesDTO> getValoraciones(@PathVariable String id) {
        log.debug("REST request to get Valoraciones : {}", id);
        ValoracionesDTO valoracionesDTO = valoracionesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(valoracionesDTO));
    }

    /**
     * DELETE  /valoraciones/:id : delete the "id" valoraciones.
     *
     * @param id the id of the valoracionesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/valoraciones/{id}")
    @Timed
    public ResponseEntity<Void> deleteValoraciones(@PathVariable String id) {
        log.debug("REST request to delete Valoraciones : {}", id);
        valoracionesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
