package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;
import io.github.jhipster.application.domain.DetalleFactura;
import io.github.jhipster.application.repository.DetalleFacturaRepository;
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
 * Integration tests for the {@Link DetalleFacturaResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class DetalleFacturaResourceIT {

    private static final Integer DEFAULT_DETALLE_FACT_ID = 1;
    private static final Integer UPDATED_DETALLE_FACT_ID = 2;

    private static final Integer DEFAULT_FACTURA_NRO = 1;
    private static final Integer UPDATED_FACTURA_NRO = 2;

    private static final String DEFAULT_DETALLE_FAC_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DETALLE_FAC_DESCRIPCION = "BBBBBBBBBB";

    private static final Integer DEFAULT_DETALLE_FAC_PRECIO_UNITARIO = 1;
    private static final Integer UPDATED_DETALLE_FAC_PRECIO_UNITARIO = 2;

    private static final Integer DEFAULT_DETALLE_FAC_CANTIDAD = 1;
    private static final Integer UPDATED_DETALLE_FAC_CANTIDAD = 2;

    private static final Integer DEFAULT_DETALLE_FAC_TOTAL = 1;
    private static final Integer UPDATED_DETALLE_FAC_TOTAL = 2;

    @Autowired
    private DetalleFacturaRepository detalleFacturaRepository;

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

    private MockMvc restDetalleFacturaMockMvc;

    private DetalleFactura detalleFactura;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DetalleFacturaResource detalleFacturaResource = new DetalleFacturaResource(detalleFacturaRepository);
        this.restDetalleFacturaMockMvc = MockMvcBuilders.standaloneSetup(detalleFacturaResource)
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
    public static DetalleFactura createEntity(EntityManager em) {
        DetalleFactura detalleFactura = new DetalleFactura()
            .detalleFactID(DEFAULT_DETALLE_FACT_ID)
            .facturaNro(DEFAULT_FACTURA_NRO)
            .detalleFacDescripcion(DEFAULT_DETALLE_FAC_DESCRIPCION)
            .detalleFacPrecioUnitario(DEFAULT_DETALLE_FAC_PRECIO_UNITARIO)
            .detalleFacCantidad(DEFAULT_DETALLE_FAC_CANTIDAD)
            .detalleFacTotal(DEFAULT_DETALLE_FAC_TOTAL);
        return detalleFactura;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DetalleFactura createUpdatedEntity(EntityManager em) {
        DetalleFactura detalleFactura = new DetalleFactura()
            .detalleFactID(UPDATED_DETALLE_FACT_ID)
            .facturaNro(UPDATED_FACTURA_NRO)
            .detalleFacDescripcion(UPDATED_DETALLE_FAC_DESCRIPCION)
            .detalleFacPrecioUnitario(UPDATED_DETALLE_FAC_PRECIO_UNITARIO)
            .detalleFacCantidad(UPDATED_DETALLE_FAC_CANTIDAD)
            .detalleFacTotal(UPDATED_DETALLE_FAC_TOTAL);
        return detalleFactura;
    }

    @BeforeEach
    public void initTest() {
        detalleFactura = createEntity(em);
    }

    @Test
    @Transactional
    public void createDetalleFactura() throws Exception {
        int databaseSizeBeforeCreate = detalleFacturaRepository.findAll().size();

        // Create the DetalleFactura
        restDetalleFacturaMockMvc.perform(post("/api/detalle-facturas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalleFactura)))
            .andExpect(status().isCreated());

        // Validate the DetalleFactura in the database
        List<DetalleFactura> detalleFacturaList = detalleFacturaRepository.findAll();
        assertThat(detalleFacturaList).hasSize(databaseSizeBeforeCreate + 1);
        DetalleFactura testDetalleFactura = detalleFacturaList.get(detalleFacturaList.size() - 1);
        assertThat(testDetalleFactura.getDetalleFactID()).isEqualTo(DEFAULT_DETALLE_FACT_ID);
        assertThat(testDetalleFactura.getFacturaNro()).isEqualTo(DEFAULT_FACTURA_NRO);
        assertThat(testDetalleFactura.getDetalleFacDescripcion()).isEqualTo(DEFAULT_DETALLE_FAC_DESCRIPCION);
        assertThat(testDetalleFactura.getDetalleFacPrecioUnitario()).isEqualTo(DEFAULT_DETALLE_FAC_PRECIO_UNITARIO);
        assertThat(testDetalleFactura.getDetalleFacCantidad()).isEqualTo(DEFAULT_DETALLE_FAC_CANTIDAD);
        assertThat(testDetalleFactura.getDetalleFacTotal()).isEqualTo(DEFAULT_DETALLE_FAC_TOTAL);
    }

    @Test
    @Transactional
    public void createDetalleFacturaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = detalleFacturaRepository.findAll().size();

        // Create the DetalleFactura with an existing ID
        detalleFactura.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDetalleFacturaMockMvc.perform(post("/api/detalle-facturas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalleFactura)))
            .andExpect(status().isBadRequest());

        // Validate the DetalleFactura in the database
        List<DetalleFactura> detalleFacturaList = detalleFacturaRepository.findAll();
        assertThat(detalleFacturaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFacturaNroIsRequired() throws Exception {
        int databaseSizeBeforeTest = detalleFacturaRepository.findAll().size();
        // set the field null
        detalleFactura.setFacturaNro(null);

        // Create the DetalleFactura, which fails.

        restDetalleFacturaMockMvc.perform(post("/api/detalle-facturas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalleFactura)))
            .andExpect(status().isBadRequest());

        List<DetalleFactura> detalleFacturaList = detalleFacturaRepository.findAll();
        assertThat(detalleFacturaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDetalleFacturas() throws Exception {
        // Initialize the database
        detalleFacturaRepository.saveAndFlush(detalleFactura);

        // Get all the detalleFacturaList
        restDetalleFacturaMockMvc.perform(get("/api/detalle-facturas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detalleFactura.getId().intValue())))
            .andExpect(jsonPath("$.[*].detalleFactID").value(hasItem(DEFAULT_DETALLE_FACT_ID)))
            .andExpect(jsonPath("$.[*].facturaNro").value(hasItem(DEFAULT_FACTURA_NRO)))
            .andExpect(jsonPath("$.[*].detalleFacDescripcion").value(hasItem(DEFAULT_DETALLE_FAC_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].detalleFacPrecioUnitario").value(hasItem(DEFAULT_DETALLE_FAC_PRECIO_UNITARIO)))
            .andExpect(jsonPath("$.[*].detalleFacCantidad").value(hasItem(DEFAULT_DETALLE_FAC_CANTIDAD)))
            .andExpect(jsonPath("$.[*].detalleFacTotal").value(hasItem(DEFAULT_DETALLE_FAC_TOTAL)));
    }
    
    @Test
    @Transactional
    public void getDetalleFactura() throws Exception {
        // Initialize the database
        detalleFacturaRepository.saveAndFlush(detalleFactura);

        // Get the detalleFactura
        restDetalleFacturaMockMvc.perform(get("/api/detalle-facturas/{id}", detalleFactura.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(detalleFactura.getId().intValue()))
            .andExpect(jsonPath("$.detalleFactID").value(DEFAULT_DETALLE_FACT_ID))
            .andExpect(jsonPath("$.facturaNro").value(DEFAULT_FACTURA_NRO))
            .andExpect(jsonPath("$.detalleFacDescripcion").value(DEFAULT_DETALLE_FAC_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.detalleFacPrecioUnitario").value(DEFAULT_DETALLE_FAC_PRECIO_UNITARIO))
            .andExpect(jsonPath("$.detalleFacCantidad").value(DEFAULT_DETALLE_FAC_CANTIDAD))
            .andExpect(jsonPath("$.detalleFacTotal").value(DEFAULT_DETALLE_FAC_TOTAL));
    }

    @Test
    @Transactional
    public void getNonExistingDetalleFactura() throws Exception {
        // Get the detalleFactura
        restDetalleFacturaMockMvc.perform(get("/api/detalle-facturas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDetalleFactura() throws Exception {
        // Initialize the database
        detalleFacturaRepository.saveAndFlush(detalleFactura);

        int databaseSizeBeforeUpdate = detalleFacturaRepository.findAll().size();

        // Update the detalleFactura
        DetalleFactura updatedDetalleFactura = detalleFacturaRepository.findById(detalleFactura.getId()).get();
        // Disconnect from session so that the updates on updatedDetalleFactura are not directly saved in db
        em.detach(updatedDetalleFactura);
        updatedDetalleFactura
            .detalleFactID(UPDATED_DETALLE_FACT_ID)
            .facturaNro(UPDATED_FACTURA_NRO)
            .detalleFacDescripcion(UPDATED_DETALLE_FAC_DESCRIPCION)
            .detalleFacPrecioUnitario(UPDATED_DETALLE_FAC_PRECIO_UNITARIO)
            .detalleFacCantidad(UPDATED_DETALLE_FAC_CANTIDAD)
            .detalleFacTotal(UPDATED_DETALLE_FAC_TOTAL);

        restDetalleFacturaMockMvc.perform(put("/api/detalle-facturas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDetalleFactura)))
            .andExpect(status().isOk());

        // Validate the DetalleFactura in the database
        List<DetalleFactura> detalleFacturaList = detalleFacturaRepository.findAll();
        assertThat(detalleFacturaList).hasSize(databaseSizeBeforeUpdate);
        DetalleFactura testDetalleFactura = detalleFacturaList.get(detalleFacturaList.size() - 1);
        assertThat(testDetalleFactura.getDetalleFactID()).isEqualTo(UPDATED_DETALLE_FACT_ID);
        assertThat(testDetalleFactura.getFacturaNro()).isEqualTo(UPDATED_FACTURA_NRO);
        assertThat(testDetalleFactura.getDetalleFacDescripcion()).isEqualTo(UPDATED_DETALLE_FAC_DESCRIPCION);
        assertThat(testDetalleFactura.getDetalleFacPrecioUnitario()).isEqualTo(UPDATED_DETALLE_FAC_PRECIO_UNITARIO);
        assertThat(testDetalleFactura.getDetalleFacCantidad()).isEqualTo(UPDATED_DETALLE_FAC_CANTIDAD);
        assertThat(testDetalleFactura.getDetalleFacTotal()).isEqualTo(UPDATED_DETALLE_FAC_TOTAL);
    }

    @Test
    @Transactional
    public void updateNonExistingDetalleFactura() throws Exception {
        int databaseSizeBeforeUpdate = detalleFacturaRepository.findAll().size();

        // Create the DetalleFactura

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDetalleFacturaMockMvc.perform(put("/api/detalle-facturas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detalleFactura)))
            .andExpect(status().isBadRequest());

        // Validate the DetalleFactura in the database
        List<DetalleFactura> detalleFacturaList = detalleFacturaRepository.findAll();
        assertThat(detalleFacturaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDetalleFactura() throws Exception {
        // Initialize the database
        detalleFacturaRepository.saveAndFlush(detalleFactura);

        int databaseSizeBeforeDelete = detalleFacturaRepository.findAll().size();

        // Delete the detalleFactura
        restDetalleFacturaMockMvc.perform(delete("/api/detalle-facturas/{id}", detalleFactura.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<DetalleFactura> detalleFacturaList = detalleFacturaRepository.findAll();
        assertThat(detalleFacturaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetalleFactura.class);
        DetalleFactura detalleFactura1 = new DetalleFactura();
        detalleFactura1.setId(1L);
        DetalleFactura detalleFactura2 = new DetalleFactura();
        detalleFactura2.setId(detalleFactura1.getId());
        assertThat(detalleFactura1).isEqualTo(detalleFactura2);
        detalleFactura2.setId(2L);
        assertThat(detalleFactura1).isNotEqualTo(detalleFactura2);
        detalleFactura1.setId(null);
        assertThat(detalleFactura1).isNotEqualTo(detalleFactura2);
    }
}
