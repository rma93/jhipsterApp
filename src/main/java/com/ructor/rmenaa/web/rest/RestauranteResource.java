package com.ructor.rmenaa.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ructor.rmenaa.service.RestauranteService;
import com.ructor.rmenaa.web.rest.util.HeaderUtil;
import com.ructor.rmenaa.web.rest.util.PaginationUtil;
import com.ructor.rmenaa.service.dto.RestauranteDTO;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Restaurante.
 */
@RestController
@RequestMapping("/api")
public class RestauranteResource {

    private final Logger log = LoggerFactory.getLogger(RestauranteResource.class);

    private static final String ENTITY_NAME = "restaurante";

    private final RestauranteService restauranteService;

    public RestauranteResource(RestauranteService restauranteService) {
        this.restauranteService = restauranteService;
    }

    /**
     * POST  /restaurantes : Create a new restaurante.
     *
     * @param restauranteDTO the restauranteDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new restauranteDTO, or with status 400 (Bad Request) if the restaurante has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/restaurantes")
    @Timed
    public ResponseEntity<RestauranteDTO> createRestaurante(@Valid @RequestBody RestauranteDTO restauranteDTO) throws URISyntaxException {
        log.debug("REST request to save Restaurante : {}", restauranteDTO);
        if (restauranteDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new restaurante cannot already have an ID")).body(null);
        }
        RestauranteDTO result = restauranteService.save(restauranteDTO);
        return ResponseEntity.created(new URI("/api/restaurantes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /restaurantes : Updates an existing restaurante.
     *
     * @param restauranteDTO the restauranteDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated restauranteDTO,
     * or with status 400 (Bad Request) if the restauranteDTO is not valid,
     * or with status 500 (Internal Server Error) if the restauranteDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/restaurantes")
    @Timed
    public ResponseEntity<RestauranteDTO> updateRestaurante(@Valid @RequestBody RestauranteDTO restauranteDTO) throws URISyntaxException {
        log.debug("REST request to update Restaurante : {}", restauranteDTO);
        if (restauranteDTO.getId() == null) {
            return createRestaurante(restauranteDTO);
        }
        RestauranteDTO result = restauranteService.save(restauranteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, restauranteDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /restaurantes : get all the restaurantes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of restaurantes in body
     */
    @GetMapping("/restaurantes")
    @Timed
    public ResponseEntity<List<RestauranteDTO>> getAllRestaurantes(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Restaurantes");
        Page<RestauranteDTO> page = restauranteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/restaurantes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /restaurantes/:id : get the "id" restaurante.
     *
     * @param id the id of the restauranteDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the restauranteDTO, or with status 404 (Not Found)
     */
    @GetMapping("/restaurantes/{id}")
    @Timed
    public ResponseEntity<RestauranteDTO> getRestaurante(@PathVariable String id) {
        log.debug("REST request to get Restaurante : {}", id);
        RestauranteDTO restauranteDTO = restauranteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(restauranteDTO));
    }

    /**
     * DELETE  /restaurantes/:id : delete the "id" restaurante.
     *
     * @param id the id of the restauranteDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/restaurantes/{id}")
    @Timed
    public ResponseEntity<Void> deleteRestaurante(@PathVariable String id) {
        log.debug("REST request to delete Restaurante : {}", id);
        restauranteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
