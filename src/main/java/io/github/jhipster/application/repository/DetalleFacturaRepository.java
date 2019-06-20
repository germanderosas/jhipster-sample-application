package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.DetalleFactura;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DetalleFactura entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DetalleFacturaRepository extends JpaRepository<DetalleFactura, Long> {

}
