package com.ructor.rmenaa.web.rest;

import com.ructor.rmenaa.Prueba3App;

import com.ructor.rmenaa.domain.Valoraciones;
import com.ructor.rmenaa.repository.ValoracionesRepository;
import com.ructor.rmenaa.service.ValoracionesService;
import com.ructor.rmenaa.service.dto.ValoracionesDTO;
import com.ructor.rmenaa.service.mapper.ValoracionesMapper;
import com.ructor.rmenaa.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ValoracionesResource REST controller.
 *
 * @see ValoracionesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Prueba3App.class)
public class ValoracionesResourceIntTest {

    private static final Long DEFAULT_PUNTUACION = 1L;
    private static final Long UPDATED_PUNTUACION = 2L;

    private static final String DEFAULT_COMENTARIO = "AAAAAAAAAA";
    private static final String UPDATED_COMENTARIO = "BBBBBBBBBB";

    private static final String DEFAULT_UBICACION_LAT_REST = "AAAAAAAAAA";
    private static final String UPDATED_UBICACION_LAT_REST = "BBBBBBBBBB";

    private static final String DEFAULT_UBICACION_LONG_REST = "AAAAAAAAAA";
    private static final String UPDATED_UBICACION_LONG_REST = "BBBBBBBBBB";

    @Autowired
    private ValoracionesRepository valoracionesRepository;

    @Autowired
    private ValoracionesMapper valoracionesMapper;

    @Autowired
    private ValoracionesService valoracionesService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restValoracionesMockMvc;

    private Valoraciones valoraciones;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ValoracionesResource valoracionesResource = new ValoracionesResource(valoracionesService);
        this.restValoracionesMockMvc = MockMvcBuilders.standaloneSetup(valoracionesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Valoraciones createEntity() {
        Valoraciones valoraciones = new Valoraciones()
            .puntuacion(DEFAULT_PUNTUACION)
            .comentario(DEFAULT_COMENTARIO)
            .ubicacionLatRest(DEFAULT_UBICACION_LAT_REST)
            .ubicacionLongRest(DEFAULT_UBICACION_LONG_REST);
        return valoraciones;
    }

    @Before
    public void initTest() {
        valoracionesRepository.deleteAll();
        valoraciones = createEntity();
    }

    @Test
    public void createValoraciones() throws Exception {
        int databaseSizeBeforeCreate = valoracionesRepository.findAll().size();

        // Create the Valoraciones
        ValoracionesDTO valoracionesDTO = valoracionesMapper.toDto(valoraciones);
        restValoracionesMockMvc.perform(post("/api/valoraciones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(valoracionesDTO)))
            .andExpect(status().isCreated());

        // Validate the Valoraciones in the database
        List<Valoraciones> valoracionesList = valoracionesRepository.findAll();
        assertThat(valoracionesList).hasSize(databaseSizeBeforeCreate + 1);
        Valoraciones testValoraciones = valoracionesList.get(valoracionesList.size() - 1);
        assertThat(testValoraciones.getPuntuacion()).isEqualTo(DEFAULT_PUNTUACION);
        assertThat(testValoraciones.getComentario()).isEqualTo(DEFAULT_COMENTARIO);
        assertThat(testValoraciones.getUbicacionLatRest()).isEqualTo(DEFAULT_UBICACION_LAT_REST);
        assertThat(testValoraciones.getUbicacionLongRest()).isEqualTo(DEFAULT_UBICACION_LONG_REST);
    }

    @Test
    public void createValoracionesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = valoracionesRepository.findAll().size();

        // Create the Valoraciones with an existing ID
        valoraciones.setId("existing_id");
        ValoracionesDTO valoracionesDTO = valoracionesMapper.toDto(valoraciones);

        // An entity with an existing ID cannot be created, so this API call must fail
        restValoracionesMockMvc.perform(post("/api/valoraciones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(valoracionesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Valoraciones> valoracionesList = valoracionesRepository.findAll();
        assertThat(valoracionesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkPuntuacionIsRequired() throws Exception {
        int databaseSizeBeforeTest = valoracionesRepository.findAll().size();
        // set the field null
        valoraciones.setPuntuacion(null);

        // Create the Valoraciones, which fails.
        ValoracionesDTO valoracionesDTO = valoracionesMapper.toDto(valoraciones);

        restValoracionesMockMvc.perform(post("/api/valoraciones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(valoracionesDTO)))
            .andExpect(status().isBadRequest());

        List<Valoraciones> valoracionesList = valoracionesRepository.findAll();
        assertThat(valoracionesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllValoraciones() throws Exception {
        // Initialize the database
        valoracionesRepository.save(valoraciones);

        // Get all the valoracionesList
        restValoracionesMockMvc.perform(get("/api/valoraciones?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(valoraciones.getId())))
            .andExpect(jsonPath("$.[*].puntuacion").value(hasItem(DEFAULT_PUNTUACION.intValue())))
            .andExpect(jsonPath("$.[*].comentario").value(hasItem(DEFAULT_COMENTARIO.toString())))
            .andExpect(jsonPath("$.[*].ubicacionLatRest").value(hasItem(DEFAULT_UBICACION_LAT_REST.toString())))
            .andExpect(jsonPath("$.[*].ubicacionLongRest").value(hasItem(DEFAULT_UBICACION_LONG_REST.toString())));
    }

    @Test
    public void getValoraciones() throws Exception {
        // Initialize the database
        valoracionesRepository.save(valoraciones);

        // Get the valoraciones
        restValoracionesMockMvc.perform(get("/api/valoraciones/{id}", valoraciones.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(valoraciones.getId()))
            .andExpect(jsonPath("$.puntuacion").value(DEFAULT_PUNTUACION.intValue()))
            .andExpect(jsonPath("$.comentario").value(DEFAULT_COMENTARIO.toString()))
            .andExpect(jsonPath("$.ubicacionLatRest").value(DEFAULT_UBICACION_LAT_REST.toString()))
            .andExpect(jsonPath("$.ubicacionLongRest").value(DEFAULT_UBICACION_LONG_REST.toString()));
    }

    @Test
    public void getNonExistingValoraciones() throws Exception {
        // Get the valoraciones
        restValoracionesMockMvc.perform(get("/api/valoraciones/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateValoraciones() throws Exception {
        // Initialize the database
        valoracionesRepository.save(valoraciones);
        int databaseSizeBeforeUpdate = valoracionesRepository.findAll().size();

        // Update the valoraciones
        Valoraciones updatedValoraciones = valoracionesRepository.findOne(valoraciones.getId());
        updatedValoraciones
            .puntuacion(UPDATED_PUNTUACION)
            .comentario(UPDATED_COMENTARIO)
            .ubicacionLatRest(UPDATED_UBICACION_LAT_REST)
            .ubicacionLongRest(UPDATED_UBICACION_LONG_REST);
        ValoracionesDTO valoracionesDTO = valoracionesMapper.toDto(updatedValoraciones);

        restValoracionesMockMvc.perform(put("/api/valoraciones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(valoracionesDTO)))
            .andExpect(status().isOk());

        // Validate the Valoraciones in the database
        List<Valoraciones> valoracionesList = valoracionesRepository.findAll();
        assertThat(valoracionesList).hasSize(databaseSizeBeforeUpdate);
        Valoraciones testValoraciones = valoracionesList.get(valoracionesList.size() - 1);
        assertThat(testValoraciones.getPuntuacion()).isEqualTo(UPDATED_PUNTUACION);
        assertThat(testValoraciones.getComentario()).isEqualTo(UPDATED_COMENTARIO);
        assertThat(testValoraciones.getUbicacionLatRest()).isEqualTo(UPDATED_UBICACION_LAT_REST);
        assertThat(testValoraciones.getUbicacionLongRest()).isEqualTo(UPDATED_UBICACION_LONG_REST);
    }

    @Test
    public void updateNonExistingValoraciones() throws Exception {
        int databaseSizeBeforeUpdate = valoracionesRepository.findAll().size();

        // Create the Valoraciones
        ValoracionesDTO valoracionesDTO = valoracionesMapper.toDto(valoraciones);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restValoracionesMockMvc.perform(put("/api/valoraciones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(valoracionesDTO)))
            .andExpect(status().isCreated());

        // Validate the Valoraciones in the database
        List<Valoraciones> valoracionesList = valoracionesRepository.findAll();
        assertThat(valoracionesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteValoraciones() throws Exception {
        // Initialize the database
        valoracionesRepository.save(valoraciones);
        int databaseSizeBeforeDelete = valoracionesRepository.findAll().size();

        // Get the valoraciones
        restValoracionesMockMvc.perform(delete("/api/valoraciones/{id}", valoraciones.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Valoraciones> valoracionesList = valoracionesRepository.findAll();
        assertThat(valoracionesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Valoraciones.class);
        Valoraciones valoraciones1 = new Valoraciones();
        valoraciones1.setId("id1");
        Valoraciones valoraciones2 = new Valoraciones();
        valoraciones2.setId(valoraciones1.getId());
        assertThat(valoraciones1).isEqualTo(valoraciones2);
        valoraciones2.setId("id2");
        assertThat(valoraciones1).isNotEqualTo(valoraciones2);
        valoraciones1.setId(null);
        assertThat(valoraciones1).isNotEqualTo(valoraciones2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ValoracionesDTO.class);
        ValoracionesDTO valoracionesDTO1 = new ValoracionesDTO();
        valoracionesDTO1.setId("id1");
        ValoracionesDTO valoracionesDTO2 = new ValoracionesDTO();
        assertThat(valoracionesDTO1).isNotEqualTo(valoracionesDTO2);
        valoracionesDTO2.setId(valoracionesDTO1.getId());
        assertThat(valoracionesDTO1).isEqualTo(valoracionesDTO2);
        valoracionesDTO2.setId("id2");
        assertThat(valoracionesDTO1).isNotEqualTo(valoracionesDTO2);
        valoracionesDTO1.setId(null);
        assertThat(valoracionesDTO1).isNotEqualTo(valoracionesDTO2);
    }
}
