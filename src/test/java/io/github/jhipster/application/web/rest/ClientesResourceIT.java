package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;
import io.github.jhipster.application.domain.Clientes;
import io.github.jhipster.application.repository.ClientesRepository;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link ClientesResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class ClientesResourceIT {

    private static final String DEFAULT_CLIENTE_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_CLIENTE_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENTE_DOMICILIO = "AAAAAAAAAA";
    private static final String UPDATED_CLIENTE_DOMICILIO = "BBBBBBBBBB";

    private static final Integer DEFAULT_CLIENTE_CUIT = 1;
    private static final Integer UPDATED_CLIENTE_CUIT = 2;

    private static final Integer DEFAULT_CLIENTE_ID = 1;
    private static final Integer UPDATED_CLIENTE_ID = 2;

    @Autowired
    private ClientesRepository clientesRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restClientesMockMvc;

    private Clientes clientes;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClientesResource clientesResource = new ClientesResource(clientesRepository);
        this.restClientesMockMvc = MockMvcBuilders.standaloneSetup(clientesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Clientes createEntity(EntityManager em) {
        Clientes clientes = new Clientes()
            .clienteNombre(DEFAULT_CLIENTE_NOMBRE)
            .clienteDomicilio(DEFAULT_CLIENTE_DOMICILIO)
            .clienteCuit(DEFAULT_CLIENTE_CUIT)
            .clienteID(DEFAULT_CLIENTE_ID);
        return clientes;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Clientes createUpdatedEntity(EntityManager em) {
        Clientes clientes = new Clientes()
            .clienteNombre(UPDATED_CLIENTE_NOMBRE)
            .clienteDomicilio(UPDATED_CLIENTE_DOMICILIO)
            .clienteCuit(UPDATED_CLIENTE_CUIT)
            .clienteID(UPDATED_CLIENTE_ID);
        return clientes;
    }

    @BeforeEach
    public void initTest() {
        clientes = createEntity(em);
    }

    @Test
    @Transactional
    public void createClientes() throws Exception {
        int databaseSizeBeforeCreate = clientesRepository.findAll().size();

        // Create the Clientes
        restClientesMockMvc.perform(post("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientes)))
            .andExpect(status().isCreated());

        // Validate the Clientes in the database
        List<Clientes> clientesList = clientesRepository.findAll();
        assertThat(clientesList).hasSize(databaseSizeBeforeCreate + 1);
        Clientes testClientes = clientesList.get(clientesList.size() - 1);
        assertThat(testClientes.getClienteNombre()).isEqualTo(DEFAULT_CLIENTE_NOMBRE);
        assertThat(testClientes.getClienteDomicilio()).isEqualTo(DEFAULT_CLIENTE_DOMICILIO);
        assertThat(testClientes.getClienteCuit()).isEqualTo(DEFAULT_CLIENTE_CUIT);
        assertThat(testClientes.getClienteID()).isEqualTo(DEFAULT_CLIENTE_ID);
    }

    @Test
    @Transactional
    public void createClientesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clientesRepository.findAll().size();

        // Create the Clientes with an existing ID
        clientes.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientesMockMvc.perform(post("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientes)))
            .andExpect(status().isBadRequest());

        // Validate the Clientes in the database
        List<Clientes> clientesList = clientesRepository.findAll();
        assertThat(clientesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkClienteNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientesRepository.findAll().size();
        // set the field null
        clientes.setClienteNombre(null);

        // Create the Clientes, which fails.

        restClientesMockMvc.perform(post("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientes)))
            .andExpect(status().isBadRequest());

        List<Clientes> clientesList = clientesRepository.findAll();
        assertThat(clientesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClientes() throws Exception {
        // Initialize the database
        clientesRepository.saveAndFlush(clientes);

        // Get all the clientesList
        restClientesMockMvc.perform(get("/api/clientes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clientes.getId().intValue())))
            .andExpect(jsonPath("$.[*].clienteNombre").value(hasItem(DEFAULT_CLIENTE_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].clienteDomicilio").value(hasItem(DEFAULT_CLIENTE_DOMICILIO.toString())))
            .andExpect(jsonPath("$.[*].clienteCuit").value(hasItem(DEFAULT_CLIENTE_CUIT)))
            .andExpect(jsonPath("$.[*].clienteID").value(hasItem(DEFAULT_CLIENTE_ID)));
    }
    
    @Test
    @Transactional
    public void getClientes() throws Exception {
        // Initialize the database
        clientesRepository.saveAndFlush(clientes);

        // Get the clientes
        restClientesMockMvc.perform(get("/api/clientes/{id}", clientes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clientes.getId().intValue()))
            .andExpect(jsonPath("$.clienteNombre").value(DEFAULT_CLIENTE_NOMBRE.toString()))
            .andExpect(jsonPath("$.clienteDomicilio").value(DEFAULT_CLIENTE_DOMICILIO.toString()))
            .andExpect(jsonPath("$.clienteCuit").value(DEFAULT_CLIENTE_CUIT))
            .andExpect(jsonPath("$.clienteID").value(DEFAULT_CLIENTE_ID));
    }

    @Test
    @Transactional
    public void getNonExistingClientes() throws Exception {
        // Get the clientes
        restClientesMockMvc.perform(get("/api/clientes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClientes() throws Exception {
        // Initialize the database
        clientesRepository.saveAndFlush(clientes);

        int databaseSizeBeforeUpdate = clientesRepository.findAll().size();

        // Update the clientes
        Clientes updatedClientes = clientesRepository.findById(clientes.getId()).get();
        // Disconnect from session so that the updates on updatedClientes are not directly saved in db
        em.detach(updatedClientes);
        updatedClientes
            .clienteNombre(UPDATED_CLIENTE_NOMBRE)
            .clienteDomicilio(UPDATED_CLIENTE_DOMICILIO)
            .clienteCuit(UPDATED_CLIENTE_CUIT)
            .clienteID(UPDATED_CLIENTE_ID);

        restClientesMockMvc.perform(put("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedClientes)))
            .andExpect(status().isOk());

        // Validate the Clientes in the database
        List<Clientes> clientesList = clientesRepository.findAll();
        assertThat(clientesList).hasSize(databaseSizeBeforeUpdate);
        Clientes testClientes = clientesList.get(clientesList.size() - 1);
        assertThat(testClientes.getClienteNombre()).isEqualTo(UPDATED_CLIENTE_NOMBRE);
        assertThat(testClientes.getClienteDomicilio()).isEqualTo(UPDATED_CLIENTE_DOMICILIO);
        assertThat(testClientes.getClienteCuit()).isEqualTo(UPDATED_CLIENTE_CUIT);
        assertThat(testClientes.getClienteID()).isEqualTo(UPDATED_CLIENTE_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingClientes() throws Exception {
        int databaseSizeBeforeUpdate = clientesRepository.findAll().size();

        // Create the Clientes

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientesMockMvc.perform(put("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientes)))
            .andExpect(status().isBadRequest());

        // Validate the Clientes in the database
        List<Clientes> clientesList = clientesRepository.findAll();
        assertThat(clientesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClientes() throws Exception {
        // Initialize the database
        clientesRepository.saveAndFlush(clientes);

        int databaseSizeBeforeDelete = clientesRepository.findAll().size();

        // Delete the clientes
        restClientesMockMvc.perform(delete("/api/clientes/{id}", clientes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Clientes> clientesList = clientesRepository.findAll();
        assertThat(clientesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Clientes.class);
        Clientes clientes1 = new Clientes();
        clientes1.setId(1L);
        Clientes clientes2 = new Clientes();
        clientes2.setId(clientes1.getId());
        assertThat(clientes1).isEqualTo(clientes2);
        clientes2.setId(2L);
        assertThat(clientes1).isNotEqualTo(clientes2);
        clientes1.setId(null);
        assertThat(clientes1).isNotEqualTo(clientes2);
    }
}
