package com.ructor.rmenaa.web.rest;

import com.ructor.rmenaa.Prueba3App;

import com.ructor.rmenaa.domain.Restaurante;
import com.ructor.rmenaa.repository.RestauranteRepository;
import com.ructor.rmenaa.service.RestauranteService;
import com.ructor.rmenaa.service.dto.RestauranteDTO;
import com.ructor.rmenaa.service.mapper.RestauranteMapper;
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
 * Test class for the RestauranteResource REST controller.
 *
 * @see RestauranteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Prueba3App.class)
public class RestauranteResourceIntTest {

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_UBICACION_LAT = "AAAAAAAAAA";
    private static final String UPDATED_UBICACION_LAT = "BBBBBBBBBB";

    private static final String DEFAULT_UBICACION_LONG = "AAAAAAAAAA";
    private static final String UPDATED_UBICACION_LONG = "BBBBBBBBBB";

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private RestauranteMapper restauranteMapper;

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restRestauranteMockMvc;

    private Restaurante restaurante;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RestauranteResource restauranteResource = new RestauranteResource(restauranteService);
        this.restRestauranteMockMvc = MockMvcBuilders.standaloneSetup(restauranteResource)
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
    public static Restaurante createEntity() {
        Restaurante restaurante = new Restaurante()
            .direccion(DEFAULT_DIRECCION)
            .nombre(DEFAULT_NOMBRE)
            .ubicacionLat(DEFAULT_UBICACION_LAT)
            .ubicacionLong(DEFAULT_UBICACION_LONG);
        return restaurante;
    }

    @Before
    public void initTest() {
        restauranteRepository.deleteAll();
        restaurante = createEntity();
    }

    @Test
    public void createRestaurante() throws Exception {
        int databaseSizeBeforeCreate = restauranteRepository.findAll().size();

        // Create the Restaurante
        RestauranteDTO restauranteDTO = restauranteMapper.toDto(restaurante);
        restRestauranteMockMvc.perform(post("/api/restaurantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(restauranteDTO)))
            .andExpect(status().isCreated());

        // Validate the Restaurante in the database
        List<Restaurante> restauranteList = restauranteRepository.findAll();
        assertThat(restauranteList).hasSize(databaseSizeBeforeCreate + 1);
        Restaurante testRestaurante = restauranteList.get(restauranteList.size() - 1);
        assertThat(testRestaurante.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testRestaurante.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testRestaurante.getUbicacionLat()).isEqualTo(DEFAULT_UBICACION_LAT);
        assertThat(testRestaurante.getUbicacionLong()).isEqualTo(DEFAULT_UBICACION_LONG);
    }

    @Test
    public void createRestauranteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = restauranteRepository.findAll().size();

        // Create the Restaurante with an existing ID
        restaurante.setId("existing_id");
        RestauranteDTO restauranteDTO = restauranteMapper.toDto(restaurante);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRestauranteMockMvc.perform(post("/api/restaurantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(restauranteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Restaurante> restauranteList = restauranteRepository.findAll();
        assertThat(restauranteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkDireccionIsRequired() throws Exception {
        int databaseSizeBeforeTest = restauranteRepository.findAll().size();
        // set the field null
        restaurante.setDireccion(null);

        // Create the Restaurante, which fails.
        RestauranteDTO restauranteDTO = restauranteMapper.toDto(restaurante);

        restRestauranteMockMvc.perform(post("/api/restaurantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(restauranteDTO)))
            .andExpect(status().isBadRequest());

        List<Restaurante> restauranteList = restauranteRepository.findAll();
        assertThat(restauranteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = restauranteRepository.findAll().size();
        // set the field null
        restaurante.setNombre(null);

        // Create the Restaurante, which fails.
        RestauranteDTO restauranteDTO = restauranteMapper.toDto(restaurante);

        restRestauranteMockMvc.perform(post("/api/restaurantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(restauranteDTO)))
            .andExpect(status().isBadRequest());

        List<Restaurante> restauranteList = restauranteRepository.findAll();
        assertThat(restauranteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkUbicacionLatIsRequired() throws Exception {
        int databaseSizeBeforeTest = restauranteRepository.findAll().size();
        // set the field null
        restaurante.setUbicacionLat(null);

        // Create the Restaurante, which fails.
        RestauranteDTO restauranteDTO = restauranteMapper.toDto(restaurante);

        restRestauranteMockMvc.perform(post("/api/restaurantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(restauranteDTO)))
            .andExpect(status().isBadRequest());

        List<Restaurante> restauranteList = restauranteRepository.findAll();
        assertThat(restauranteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkUbicacionLongIsRequired() throws Exception {
        int databaseSizeBeforeTest = restauranteRepository.findAll().size();
        // set the field null
        restaurante.setUbicacionLong(null);

        // Create the Restaurante, which fails.
        RestauranteDTO restauranteDTO = restauranteMapper.toDto(restaurante);

        restRestauranteMockMvc.perform(post("/api/restaurantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(restauranteDTO)))
            .andExpect(status().isBadRequest());

        List<Restaurante> restauranteList = restauranteRepository.findAll();
        assertThat(restauranteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllRestaurantes() throws Exception {
        // Initialize the database
        restauranteRepository.save(restaurante);

        // Get all the restauranteList
        restRestauranteMockMvc.perform(get("/api/restaurantes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(restaurante.getId())))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION.toString())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].ubicacionLat").value(hasItem(DEFAULT_UBICACION_LAT.toString())))
            .andExpect(jsonPath("$.[*].ubicacionLong").value(hasItem(DEFAULT_UBICACION_LONG.toString())));
    }

    @Test
    public void getRestaurante() throws Exception {
        // Initialize the database
        restauranteRepository.save(restaurante);

        // Get the restaurante
        restRestauranteMockMvc.perform(get("/api/restaurantes/{id}", restaurante.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(restaurante.getId()))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION.toString()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.ubicacionLat").value(DEFAULT_UBICACION_LAT.toString()))
            .andExpect(jsonPath("$.ubicacionLong").value(DEFAULT_UBICACION_LONG.toString()));
    }

    @Test
    public void getNonExistingRestaurante() throws Exception {
        // Get the restaurante
        restRestauranteMockMvc.perform(get("/api/restaurantes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateRestaurante() throws Exception {
        // Initialize the database
        restauranteRepository.save(restaurante);
        int databaseSizeBeforeUpdate = restauranteRepository.findAll().size();

        // Update the restaurante
        Restaurante updatedRestaurante = restauranteRepository.findOne(restaurante.getId());
        updatedRestaurante
            .direccion(UPDATED_DIRECCION)
            .nombre(UPDATED_NOMBRE)
            .ubicacionLat(UPDATED_UBICACION_LAT)
            .ubicacionLong(UPDATED_UBICACION_LONG);
        RestauranteDTO restauranteDTO = restauranteMapper.toDto(updatedRestaurante);

        restRestauranteMockMvc.perform(put("/api/restaurantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(restauranteDTO)))
            .andExpect(status().isOk());

        // Validate the Restaurante in the database
        List<Restaurante> restauranteList = restauranteRepository.findAll();
        assertThat(restauranteList).hasSize(databaseSizeBeforeUpdate);
        Restaurante testRestaurante = restauranteList.get(restauranteList.size() - 1);
        assertThat(testRestaurante.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testRestaurante.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testRestaurante.getUbicacionLat()).isEqualTo(UPDATED_UBICACION_LAT);
        assertThat(testRestaurante.getUbicacionLong()).isEqualTo(UPDATED_UBICACION_LONG);
    }

    @Test
    public void updateNonExistingRestaurante() throws Exception {
        int databaseSizeBeforeUpdate = restauranteRepository.findAll().size();

        // Create the Restaurante
        RestauranteDTO restauranteDTO = restauranteMapper.toDto(restaurante);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRestauranteMockMvc.perform(put("/api/restaurantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(restauranteDTO)))
            .andExpect(status().isCreated());

        // Validate the Restaurante in the database
        List<Restaurante> restauranteList = restauranteRepository.findAll();
        assertThat(restauranteList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteRestaurante() throws Exception {
        // Initialize the database
        restauranteRepository.save(restaurante);
        int databaseSizeBeforeDelete = restauranteRepository.findAll().size();

        // Get the restaurante
        restRestauranteMockMvc.perform(delete("/api/restaurantes/{id}", restaurante.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Restaurante> restauranteList = restauranteRepository.findAll();
        assertThat(restauranteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Restaurante.class);
        Restaurante restaurante1 = new Restaurante();
        restaurante1.setId("id1");
        Restaurante restaurante2 = new Restaurante();
        restaurante2.setId(restaurante1.getId());
        assertThat(restaurante1).isEqualTo(restaurante2);
        restaurante2.setId("id2");
        assertThat(restaurante1).isNotEqualTo(restaurante2);
        restaurante1.setId(null);
        assertThat(restaurante1).isNotEqualTo(restaurante2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RestauranteDTO.class);
        RestauranteDTO restauranteDTO1 = new RestauranteDTO();
        restauranteDTO1.setId("id1");
        RestauranteDTO restauranteDTO2 = new RestauranteDTO();
        assertThat(restauranteDTO1).isNotEqualTo(restauranteDTO2);
        restauranteDTO2.setId(restauranteDTO1.getId());
        assertThat(restauranteDTO1).isEqualTo(restauranteDTO2);
        restauranteDTO2.setId("id2");
        assertThat(restauranteDTO1).isNotEqualTo(restauranteDTO2);
        restauranteDTO1.setId(null);
        assertThat(restauranteDTO1).isNotEqualTo(restauranteDTO2);
    }
}
