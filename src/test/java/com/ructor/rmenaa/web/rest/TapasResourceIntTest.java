package com.ructor.rmenaa.web.rest;

import com.ructor.rmenaa.Prueba3App;

import com.ructor.rmenaa.domain.Tapas;
import com.ructor.rmenaa.repository.TapasRepository;
import com.ructor.rmenaa.service.TapasService;
import com.ructor.rmenaa.service.dto.TapasDTO;
import com.ructor.rmenaa.service.mapper.TapasMapper;
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

import com.ructor.rmenaa.domain.enumeration.Alergenos;
/**
 * Test class for the TapasResource REST controller.
 *
 * @see TapasResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Prueba3App.class)
public class TapasResourceIntTest {

    private static final Integer DEFAULT_IDTAPA = 1;
    private static final Integer UPDATED_IDTAPA = 2;

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_INGREDIENTE = "AAAAAAAAAA";
    private static final String UPDATED_INGREDIENTE = "BBBBBBBBBB";

    private static final Alergenos DEFAULT_ALERGENOS = Alergenos.LACTEOS;
    private static final Alergenos UPDATED_ALERGENOS = Alergenos.CRUSTACEOS;

    @Autowired
    private TapasRepository tapasRepository;

    @Autowired
    private TapasMapper tapasMapper;

    @Autowired
    private TapasService tapasService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restTapasMockMvc;

    private Tapas tapas;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TapasResource tapasResource = new TapasResource(tapasService);
        this.restTapasMockMvc = MockMvcBuilders.standaloneSetup(tapasResource)
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
    public static Tapas createEntity() {
        Tapas tapas = new Tapas()
            .idtapa(DEFAULT_IDTAPA)
            .nombre(DEFAULT_NOMBRE)
            .ingrediente(DEFAULT_INGREDIENTE)
            .alergenos(DEFAULT_ALERGENOS);
        return tapas;
    }

    @Before
    public void initTest() {
        tapasRepository.deleteAll();
        tapas = createEntity();
    }

    @Test
    public void createTapas() throws Exception {
        int databaseSizeBeforeCreate = tapasRepository.findAll().size();

        // Create the Tapas
        TapasDTO tapasDTO = tapasMapper.toDto(tapas);
        restTapasMockMvc.perform(post("/api/tapas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tapasDTO)))
            .andExpect(status().isCreated());

        // Validate the Tapas in the database
        List<Tapas> tapasList = tapasRepository.findAll();
        assertThat(tapasList).hasSize(databaseSizeBeforeCreate + 1);
        Tapas testTapas = tapasList.get(tapasList.size() - 1);
        assertThat(testTapas.getIdtapa()).isEqualTo(DEFAULT_IDTAPA);
        assertThat(testTapas.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testTapas.getIngrediente()).isEqualTo(DEFAULT_INGREDIENTE);
        assertThat(testTapas.getAlergenos()).isEqualTo(DEFAULT_ALERGENOS);
    }

    @Test
    public void createTapasWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tapasRepository.findAll().size();

        // Create the Tapas with an existing ID
        tapas.setId("existing_id");
        TapasDTO tapasDTO = tapasMapper.toDto(tapas);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTapasMockMvc.perform(post("/api/tapas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tapasDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Tapas> tapasList = tapasRepository.findAll();
        assertThat(tapasList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllTapas() throws Exception {
        // Initialize the database
        tapasRepository.save(tapas);

        // Get all the tapasList
        restTapasMockMvc.perform(get("/api/tapas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tapas.getId())))
            .andExpect(jsonPath("$.[*].idtapa").value(hasItem(DEFAULT_IDTAPA)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].ingrediente").value(hasItem(DEFAULT_INGREDIENTE.toString())))
            .andExpect(jsonPath("$.[*].alergenos").value(hasItem(DEFAULT_ALERGENOS.toString())));
    }

    @Test
    public void getTapas() throws Exception {
        // Initialize the database
        tapasRepository.save(tapas);

        // Get the tapas
        restTapasMockMvc.perform(get("/api/tapas/{id}", tapas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tapas.getId()))
            .andExpect(jsonPath("$.idtapa").value(DEFAULT_IDTAPA))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.ingrediente").value(DEFAULT_INGREDIENTE.toString()))
            .andExpect(jsonPath("$.alergenos").value(DEFAULT_ALERGENOS.toString()));
    }

    @Test
    public void getNonExistingTapas() throws Exception {
        // Get the tapas
        restTapasMockMvc.perform(get("/api/tapas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateTapas() throws Exception {
        // Initialize the database
        tapasRepository.save(tapas);
        int databaseSizeBeforeUpdate = tapasRepository.findAll().size();

        // Update the tapas
        Tapas updatedTapas = tapasRepository.findOne(tapas.getId());
        updatedTapas
            .idtapa(UPDATED_IDTAPA)
            .nombre(UPDATED_NOMBRE)
            .ingrediente(UPDATED_INGREDIENTE)
            .alergenos(UPDATED_ALERGENOS);
        TapasDTO tapasDTO = tapasMapper.toDto(updatedTapas);

        restTapasMockMvc.perform(put("/api/tapas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tapasDTO)))
            .andExpect(status().isOk());

        // Validate the Tapas in the database
        List<Tapas> tapasList = tapasRepository.findAll();
        assertThat(tapasList).hasSize(databaseSizeBeforeUpdate);
        Tapas testTapas = tapasList.get(tapasList.size() - 1);
        assertThat(testTapas.getIdtapa()).isEqualTo(UPDATED_IDTAPA);
        assertThat(testTapas.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testTapas.getIngrediente()).isEqualTo(UPDATED_INGREDIENTE);
        assertThat(testTapas.getAlergenos()).isEqualTo(UPDATED_ALERGENOS);
    }

    @Test
    public void updateNonExistingTapas() throws Exception {
        int databaseSizeBeforeUpdate = tapasRepository.findAll().size();

        // Create the Tapas
        TapasDTO tapasDTO = tapasMapper.toDto(tapas);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTapasMockMvc.perform(put("/api/tapas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tapasDTO)))
            .andExpect(status().isCreated());

        // Validate the Tapas in the database
        List<Tapas> tapasList = tapasRepository.findAll();
        assertThat(tapasList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteTapas() throws Exception {
        // Initialize the database
        tapasRepository.save(tapas);
        int databaseSizeBeforeDelete = tapasRepository.findAll().size();

        // Get the tapas
        restTapasMockMvc.perform(delete("/api/tapas/{id}", tapas.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Tapas> tapasList = tapasRepository.findAll();
        assertThat(tapasList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tapas.class);
        Tapas tapas1 = new Tapas();
        tapas1.setId("id1");
        Tapas tapas2 = new Tapas();
        tapas2.setId(tapas1.getId());
        assertThat(tapas1).isEqualTo(tapas2);
        tapas2.setId("id2");
        assertThat(tapas1).isNotEqualTo(tapas2);
        tapas1.setId(null);
        assertThat(tapas1).isNotEqualTo(tapas2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TapasDTO.class);
        TapasDTO tapasDTO1 = new TapasDTO();
        tapasDTO1.setId("id1");
        TapasDTO tapasDTO2 = new TapasDTO();
        assertThat(tapasDTO1).isNotEqualTo(tapasDTO2);
        tapasDTO2.setId(tapasDTO1.getId());
        assertThat(tapasDTO1).isEqualTo(tapasDTO2);
        tapasDTO2.setId("id2");
        assertThat(tapasDTO1).isNotEqualTo(tapasDTO2);
        tapasDTO1.setId(null);
        assertThat(tapasDTO1).isNotEqualTo(tapasDTO2);
    }
}
