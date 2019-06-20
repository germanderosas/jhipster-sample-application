package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.domain.Facturas;
import io.github.jhipster.application.repository.FacturasRepository;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link io.github.jhipster.application.domain.Facturas}.
 */
@RestController
@RequestMapping("/api")
public class FacturasResource {

    private final Logger log = LoggerFactory.getLogger(FacturasResource.class);

    private static final String ENTITY_NAME = "facturas";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FacturasRepository facturasRepository;

    public FacturasResource(FacturasRepository facturasRepository) {
        this.facturasRepository = facturasRepository;
    }

    /**
     * {@code POST  /facturas} : Create a new facturas.
     *
     * @param facturas the facturas to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new facturas, or with status {@code 400 (Bad Request)} if the facturas has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/facturas")
    public ResponseEntity<Facturas> createFacturas(@Valid @RequestBody Facturas facturas) throws URISyntaxException {
        log.debug("REST request to save Facturas : {}", facturas);
        if (facturas.getId() != null) {
            throw new BadRequestAlertException("A new facturas cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Facturas result = facturasRepository.save(facturas);
        return ResponseEntity.created(new URI("/api/facturas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /facturas} : Updates an existing facturas.
     *
     * @param facturas the facturas to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated facturas,
     * or with status {@code 400 (Bad Request)} if the facturas is not valid,
     * or with status {@code 500 (Internal Server Error)} if the facturas couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/facturas")
    public ResponseEntity<Facturas> updateFacturas(@Valid @RequestBody Facturas facturas) throws URISyntaxException {
        log.debug("REST request to update Facturas : {}", facturas);
        if (facturas.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Facturas result = facturasRepository.save(facturas);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, facturas.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /facturas} : get all the facturas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of facturas in body.
     */
    @GetMapping("/facturas")
    public List<Facturas> getAllFacturas() {
        log.debug("REST request to get all Facturas");
        return facturasRepository.findAll();
    }

    /**
     * {@code GET  /facturas/:id} : get the "id" facturas.
     *
     * @param id the id of the facturas to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the facturas, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/facturas/{id}")
    public ResponseEntity<Facturas> getFacturas(@PathVariable Long id) {
        log.debug("REST request to get Facturas : {}", id);
        Optional<Facturas> facturas = facturasRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(facturas);
    }

    /**
     * {@code DELETE  /facturas/:id} : delete the "id" facturas.
     *
     * @param id the id of the facturas to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/facturas/{id}")
    public ResponseEntity<Void> deleteFacturas(@PathVariable Long id) {
        log.debug("REST request to delete Facturas : {}", id);
        facturasRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
