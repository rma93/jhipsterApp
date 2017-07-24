package com.ructor.rmenaa.web.rest;

import com.ructor.rmenaa.Prueba3App;

import com.ructor.rmenaa.domain.Usuario;
import com.ructor.rmenaa.repository.UsuarioRepository;
import com.ructor.rmenaa.service.UsuarioService;
import com.ructor.rmenaa.service.dto.UsuarioDTO;
import com.ructor.rmenaa.service.mapper.UsuarioMapper;
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
import org.springframework.util.Base64Utils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the UsuarioResource REST controller.
 *
 * @see UsuarioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Prueba3App.class)
public class UsuarioResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO_1 = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO_1 = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO_2 = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO_2 = "BBBBBBBBBB";

    private static final Long DEFAULT_EDAD = 1L;
    private static final Long UPDATED_EDAD = 2L;

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_CALLE = "AAAAAAAAAA";
    private static final String UPDATED_CALLE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_FOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FOTO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_FOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FOTO_CONTENT_TYPE = "image/png";

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restUsuarioMockMvc;

    private Usuario usuario;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UsuarioResource usuarioResource = new UsuarioResource(usuarioService);
        this.restUsuarioMockMvc = MockMvcBuilders.standaloneSetup(usuarioResource)
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
    public static Usuario createEntity() {
        Usuario usuario = new Usuario()
            .nombre(DEFAULT_NOMBRE)
            .apellido1(DEFAULT_APELLIDO_1)
            .apellido2(DEFAULT_APELLIDO_2)
            .edad(DEFAULT_EDAD)
            .email(DEFAULT_EMAIL)
            .calle(DEFAULT_CALLE)
            .foto(DEFAULT_FOTO)
            .fotoContentType(DEFAULT_FOTO_CONTENT_TYPE);
        return usuario;
    }

    @Before
    public void initTest() {
        usuarioRepository.deleteAll();
        usuario = createEntity();
    }

    @Test
    public void createUsuario() throws Exception {
        int databaseSizeBeforeCreate = usuarioRepository.findAll().size();

        // Create the Usuario
        UsuarioDTO usuarioDTO = usuarioMapper.toDto(usuario);
        restUsuarioMockMvc.perform(post("/api/usuarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usuarioDTO)))
            .andExpect(status().isCreated());

        // Validate the Usuario in the database
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeCreate + 1);
        Usuario testUsuario = usuarioList.get(usuarioList.size() - 1);
        assertThat(testUsuario.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testUsuario.getApellido1()).isEqualTo(DEFAULT_APELLIDO_1);
        assertThat(testUsuario.getApellido2()).isEqualTo(DEFAULT_APELLIDO_2);
        assertThat(testUsuario.getEdad()).isEqualTo(DEFAULT_EDAD);
        assertThat(testUsuario.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testUsuario.getCalle()).isEqualTo(DEFAULT_CALLE);
        assertThat(testUsuario.getFoto()).isEqualTo(DEFAULT_FOTO);
        assertThat(testUsuario.getFotoContentType()).isEqualTo(DEFAULT_FOTO_CONTENT_TYPE);
    }

    @Test
    public void createUsuarioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = usuarioRepository.findAll().size();

        // Create the Usuario with an existing ID
        usuario.setId("existing_id");
        UsuarioDTO usuarioDTO = usuarioMapper.toDto(usuario);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUsuarioMockMvc.perform(post("/api/usuarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usuarioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = usuarioRepository.findAll().size();
        // set the field null
        usuario.setNombre(null);

        // Create the Usuario, which fails.
        UsuarioDTO usuarioDTO = usuarioMapper.toDto(usuario);

        restUsuarioMockMvc.perform(post("/api/usuarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usuarioDTO)))
            .andExpect(status().isBadRequest());

        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkApellido1IsRequired() throws Exception {
        int databaseSizeBeforeTest = usuarioRepository.findAll().size();
        // set the field null
        usuario.setApellido1(null);

        // Create the Usuario, which fails.
        UsuarioDTO usuarioDTO = usuarioMapper.toDto(usuario);

        restUsuarioMockMvc.perform(post("/api/usuarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usuarioDTO)))
            .andExpect(status().isBadRequest());

        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = usuarioRepository.findAll().size();
        // set the field null
        usuario.setEmail(null);

        // Create the Usuario, which fails.
        UsuarioDTO usuarioDTO = usuarioMapper.toDto(usuario);

        restUsuarioMockMvc.perform(post("/api/usuarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usuarioDTO)))
            .andExpect(status().isBadRequest());

        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllUsuarios() throws Exception {
        // Initialize the database
        usuarioRepository.save(usuario);

        // Get all the usuarioList
        restUsuarioMockMvc.perform(get("/api/usuarios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(usuario.getId())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].apellido1").value(hasItem(DEFAULT_APELLIDO_1.toString())))
            .andExpect(jsonPath("$.[*].apellido2").value(hasItem(DEFAULT_APELLIDO_2.toString())))
            .andExpect(jsonPath("$.[*].edad").value(hasItem(DEFAULT_EDAD.intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].calle").value(hasItem(DEFAULT_CALLE.toString())))
            .andExpect(jsonPath("$.[*].fotoContentType").value(hasItem(DEFAULT_FOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].foto").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO))));
    }

    @Test
    public void getUsuario() throws Exception {
        // Initialize the database
        usuarioRepository.save(usuario);

        // Get the usuario
        restUsuarioMockMvc.perform(get("/api/usuarios/{id}", usuario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(usuario.getId()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.apellido1").value(DEFAULT_APELLIDO_1.toString()))
            .andExpect(jsonPath("$.apellido2").value(DEFAULT_APELLIDO_2.toString()))
            .andExpect(jsonPath("$.edad").value(DEFAULT_EDAD.intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.calle").value(DEFAULT_CALLE.toString()))
            .andExpect(jsonPath("$.fotoContentType").value(DEFAULT_FOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.foto").value(Base64Utils.encodeToString(DEFAULT_FOTO)));
    }

    @Test
    public void getNonExistingUsuario() throws Exception {
        // Get the usuario
        restUsuarioMockMvc.perform(get("/api/usuarios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateUsuario() throws Exception {
        // Initialize the database
        usuarioRepository.save(usuario);
        int databaseSizeBeforeUpdate = usuarioRepository.findAll().size();

        // Update the usuario
        Usuario updatedUsuario = usuarioRepository.findOne(usuario.getId());
        updatedUsuario
            .nombre(UPDATED_NOMBRE)
            .apellido1(UPDATED_APELLIDO_1)
            .apellido2(UPDATED_APELLIDO_2)
            .edad(UPDATED_EDAD)
            .email(UPDATED_EMAIL)
            .calle(UPDATED_CALLE)
            .foto(UPDATED_FOTO)
            .fotoContentType(UPDATED_FOTO_CONTENT_TYPE);
        UsuarioDTO usuarioDTO = usuarioMapper.toDto(updatedUsuario);

        restUsuarioMockMvc.perform(put("/api/usuarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usuarioDTO)))
            .andExpect(status().isOk());

        // Validate the Usuario in the database
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeUpdate);
        Usuario testUsuario = usuarioList.get(usuarioList.size() - 1);
        assertThat(testUsuario.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testUsuario.getApellido1()).isEqualTo(UPDATED_APELLIDO_1);
        assertThat(testUsuario.getApellido2()).isEqualTo(UPDATED_APELLIDO_2);
        assertThat(testUsuario.getEdad()).isEqualTo(UPDATED_EDAD);
        assertThat(testUsuario.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testUsuario.getCalle()).isEqualTo(UPDATED_CALLE);
        assertThat(testUsuario.getFoto()).isEqualTo(UPDATED_FOTO);
        assertThat(testUsuario.getFotoContentType()).isEqualTo(UPDATED_FOTO_CONTENT_TYPE);
    }

    @Test
    public void updateNonExistingUsuario() throws Exception {
        int databaseSizeBeforeUpdate = usuarioRepository.findAll().size();

        // Create the Usuario
        UsuarioDTO usuarioDTO = usuarioMapper.toDto(usuario);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUsuarioMockMvc.perform(put("/api/usuarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usuarioDTO)))
            .andExpect(status().isCreated());

        // Validate the Usuario in the database
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteUsuario() throws Exception {
        // Initialize the database
        usuarioRepository.save(usuario);
        int databaseSizeBeforeDelete = usuarioRepository.findAll().size();

        // Get the usuario
        restUsuarioMockMvc.perform(delete("/api/usuarios/{id}", usuario.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Usuario.class);
        Usuario usuario1 = new Usuario();
        usuario1.setId("id1");
        Usuario usuario2 = new Usuario();
        usuario2.setId(usuario1.getId());
        assertThat(usuario1).isEqualTo(usuario2);
        usuario2.setId("id2");
        assertThat(usuario1).isNotEqualTo(usuario2);
        usuario1.setId(null);
        assertThat(usuario1).isNotEqualTo(usuario2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UsuarioDTO.class);
        UsuarioDTO usuarioDTO1 = new UsuarioDTO();
        usuarioDTO1.setId("id1");
        UsuarioDTO usuarioDTO2 = new UsuarioDTO();
        assertThat(usuarioDTO1).isNotEqualTo(usuarioDTO2);
        usuarioDTO2.setId(usuarioDTO1.getId());
        assertThat(usuarioDTO1).isEqualTo(usuarioDTO2);
        usuarioDTO2.setId("id2");
        assertThat(usuarioDTO1).isNotEqualTo(usuarioDTO2);
        usuarioDTO1.setId(null);
        assertThat(usuarioDTO1).isNotEqualTo(usuarioDTO2);
    }
}
