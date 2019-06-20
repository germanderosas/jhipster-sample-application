package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Clientes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Clientes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientesRepository extends JpaRepository<Clientes, Long> {

}
