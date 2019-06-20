package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;
import io.github.jhipster.application.domain.Facturas;
import io.github.jhipster.application.repository.FacturasRepository;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link FacturasResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class FacturasResourceIT {

    private static final Integer DEFAULT_FACTURA_NRO = 1;
    private static final Integer UPDATED_FACTURA_NRO = 2;

    private static final Instant DEFAULT_FACTURA_FECHA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FACTURA_FECHA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_FACTURA_CLIENTE_ID = 1;
    private static final Integer UPDATED_FACTURA_CLIENTE_ID = 2;

    @Autowired
    private FacturasRepository facturasRepository;

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

    private MockMvc restFacturasMockMvc;

    private Facturas facturas;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FacturasResource facturasResource = new FacturasResource(facturasRepository);
        this.restFacturasMockMvc = MockMvcBuilders.standaloneSetup(facturasResource)
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
    public static Facturas createEntity(EntityManager em) {
        Facturas facturas = new Facturas()
            .facturaNro(DEFAULT_FACTURA_NRO)
            .facturaFecha(DEFAULT_FACTURA_FECHA)
            .facturaClienteID(DEFAULT_FACTURA_CLIENTE_ID);
        return facturas;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Facturas createUpdatedEntity(EntityManager em) {
        Facturas facturas = new Facturas()
            .facturaNro(UPDATED_FACTURA_NRO)
            .facturaFecha(UPDATED_FACTURA_FECHA)
            .facturaClienteID(UPDATED_FACTURA_CLIENTE_ID);
        return facturas;
    }

    @BeforeEach
    public void initTest() {
        facturas = createEntity(em);
    }

    @Test
    @Transactional
    public void createFacturas() throws Exception {
        int databaseSizeBeforeCreate = facturasRepository.findAll().size();

        // Create the Facturas
        restFacturasMockMvc.perform(post("/api/facturas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facturas)))
            .andExpect(status().isCreated());

        // Validate the Facturas in the database
        List<Facturas> facturasList = facturasRepository.findAll();
        assertThat(facturasList).hasSize(databaseSizeBeforeCreate + 1);
        Facturas testFacturas = facturasList.get(facturasList.size() - 1);
        assertThat(testFacturas.getFacturaNro()).isEqualTo(DEFAULT_FACTURA_NRO);
        assertThat(testFacturas.getFacturaFecha()).isEqualTo(DEFAULT_FACTURA_FECHA);
        assertThat(testFacturas.getFacturaClienteID()).isEqualTo(DEFAULT_FACTURA_CLIENTE_ID);
    }

    @Test
    @Transactional
    public void createFacturasWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = facturasRepository.findAll().size();

        // Create the Facturas with an existing ID
        facturas.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFacturasMockMvc.perform(post("/api/facturas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facturas)))
            .andExpect(status().isBadRequest());

        // Validate the Facturas in the database
        List<Facturas> facturasList = facturasRepository.findAll();
        assertThat(facturasList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFacturaNroIsRequired() throws Exception {
        int databaseSizeBeforeTest = facturasRepository.findAll().size();
        // set the field null
        facturas.setFacturaNro(null);

        // Create the Facturas, which fails.

        restFacturasMockMvc.perform(post("/api/facturas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facturas)))
            .andExpect(status().isBadRequest());

        List<Facturas> facturasList = facturasRepository.findAll();
        assertThat(facturasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFacturas() throws Exception {
        // Initialize the database
        facturasRepository.saveAndFlush(facturas);

        // Get all the facturasList
        restFacturasMockMvc.perform(get("/api/facturas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(facturas.getId().intValue())))
            .andExpect(jsonPath("$.[*].facturaNro").value(hasItem(DEFAULT_FACTURA_NRO)))
            .andExpect(jsonPath("$.[*].facturaFecha").value(hasItem(DEFAULT_FACTURA_FECHA.toString())))
            .andExpect(jsonPath("$.[*].facturaClienteID").value(hasItem(DEFAULT_FACTURA_CLIENTE_ID)));
    }
    
    @Test
    @Transactional
    public void getFacturas() throws Exception {
        // Initialize the database
        facturasRepository.saveAndFlush(facturas);

        // Get the facturas
        restFacturasMockMvc.perform(get("/api/facturas/{id}", facturas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(facturas.getId().intValue()))
            .andExpect(jsonPath("$.facturaNro").value(DEFAULT_FACTURA_NRO))
            .andExpect(jsonPath("$.facturaFecha").value(DEFAULT_FACTURA_FECHA.toString()))
            .andExpect(jsonPath("$.facturaClienteID").value(DEFAULT_FACTURA_CLIENTE_ID));
    }

    @Test
    @Transactional
    public void getNonExistingFacturas() throws Exception {
        // Get the facturas
        restFacturasMockMvc.perform(get("/api/facturas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFacturas() throws Exception {
        // Initialize the database
        facturasRepository.saveAndFlush(facturas);

        int databaseSizeBeforeUpdate = facturasRepository.findAll().size();

        // Update the facturas
        Facturas updatedFacturas = facturasRepository.findById(facturas.getId()).get();
        // Disconnect from session so that the updates on updatedFacturas are not directly saved in db
        em.detach(updatedFacturas);
        updatedFacturas
            .facturaNro(UPDATED_FACTURA_NRO)
            .facturaFecha(UPDATED_FACTURA_FECHA)
            .facturaClienteID(UPDATED_FACTURA_CLIENTE_ID);

        restFacturasMockMvc.perform(put("/api/facturas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFacturas)))
            .andExpect(status().isOk());

        // Validate the Facturas in the database
        List<Facturas> facturasList = facturasRepository.findAll();
        assertThat(facturasList).hasSize(databaseSizeBeforeUpdate);
        Facturas testFacturas = facturasList.get(facturasList.size() - 1);
        assertThat(testFacturas.getFacturaNro()).isEqualTo(UPDATED_FACTURA_NRO);
        assertThat(testFacturas.getFacturaFecha()).isEqualTo(UPDATED_FACTURA_FECHA);
        assertThat(testFacturas.getFacturaClienteID()).isEqualTo(UPDATED_FACTURA_CLIENTE_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingFacturas() throws Exception {
        int databaseSizeBeforeUpdate = facturasRepository.findAll().size();

        // Create the Facturas

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFacturasMockMvc.perform(put("/api/facturas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facturas)))
            .andExpect(status().isBadRequest());

        // Validate the Facturas in the database
        List<Facturas> facturasList = facturasRepository.findAll();
        assertThat(facturasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFacturas() throws Exception {
        // Initialize the database
        facturasRepository.saveAndFlush(facturas);

        int databaseSizeBeforeDelete = facturasRepository.findAll().size();

        // Delete the facturas
        restFacturasMockMvc.perform(delete("/api/facturas/{id}", facturas.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Facturas> facturasList = facturasRepository.findAll();
        assertThat(facturasList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Facturas.class);
        Facturas facturas1 = new Facturas();
        facturas1.setId(1L);
        Facturas facturas2 = new Facturas();
        facturas2.setId(facturas1.getId());
        assertThat(facturas1).isEqualTo(facturas2);
        facturas2.setId(2L);
        assertThat(facturas1).isNotEqualTo(facturas2);
        facturas1.setId(null);
        assertThat(facturas1).isNotEqualTo(facturas2);
    }
}
