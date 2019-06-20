package io.github.jhipster.application.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Facturas.
 */
@Entity
@Table(name = "facturas")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Facturas implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "factura_nro", nullable = false)
    private Integer facturaNro;

    @Column(name = "factura_fecha")
    private Instant facturaFecha;

    @Column(name = "factura_cliente_id")
    private Integer facturaClienteID;

    @ManyToOne
    @JsonIgnoreProperties("facturas")
    private Clientes clientes;

    @OneToMany(mappedBy = "facturas")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DetalleFactura> facturaNros = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getFacturaNro() {
        return facturaNro;
    }

    public Facturas facturaNro(Integer facturaNro) {
        this.facturaNro = facturaNro;
        return this;
    }

    public void setFacturaNro(Integer facturaNro) {
        this.facturaNro = facturaNro;
    }

    public Instant getFacturaFecha() {
        return facturaFecha;
    }

    public Facturas facturaFecha(Instant facturaFecha) {
        this.facturaFecha = facturaFecha;
        return this;
    }

    public void setFacturaFecha(Instant facturaFecha) {
        this.facturaFecha = facturaFecha;
    }

    public Integer getFacturaClienteID() {
        return facturaClienteID;
    }

    public Facturas facturaClienteID(Integer facturaClienteID) {
        this.facturaClienteID = facturaClienteID;
        return this;
    }

    public void setFacturaClienteID(Integer facturaClienteID) {
        this.facturaClienteID = facturaClienteID;
    }

    public Clientes getClientes() {
        return clientes;
    }

    public Facturas clientes(Clientes clientes) {
        this.clientes = clientes;
        return this;
    }

    public void setClientes(Clientes clientes) {
        this.clientes = clientes;
    }

    public Set<DetalleFactura> getFacturaNros() {
        return facturaNros;
    }

    public Facturas facturaNros(Set<DetalleFactura> detalleFacturas) {
        this.facturaNros = detalleFacturas;
        return this;
    }

    public Facturas addFacturaNro(DetalleFactura detalleFactura) {
        this.facturaNros.add(detalleFactura);
        detalleFactura.setFacturas(this);
        return this;
    }

    public Facturas removeFacturaNro(DetalleFactura detalleFactura) {
        this.facturaNros.remove(detalleFactura);
        detalleFactura.setFacturas(null);
        return this;
    }

    public void setFacturaNros(Set<DetalleFactura> detalleFacturas) {
        this.facturaNros = detalleFacturas;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Facturas)) {
            return false;
        }
        return id != null && id.equals(((Facturas) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Facturas{" +
            "id=" + getId() +
            ", facturaNro=" + getFacturaNro() +
            ", facturaFecha='" + getFacturaFecha() + "'" +
            ", facturaClienteID=" + getFacturaClienteID() +
            "}";
    }
}
