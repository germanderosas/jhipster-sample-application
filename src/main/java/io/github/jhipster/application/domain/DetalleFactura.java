package io.github.jhipster.application.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A DetalleFactura.
 */
@Entity
@Table(name = "detalle_factura")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DetalleFactura implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "detalle_fact_id")
    private Integer detalleFactID;

    @NotNull
    @Column(name = "factura_nro", nullable = false)
    private Integer facturaNro;

    @Column(name = "detalle_fac_descripcion")
    private String detalleFacDescripcion;

    @Column(name = "detalle_fac_precio_unitario")
    private Integer detalleFacPrecioUnitario;

    @Column(name = "detalle_fac_cantidad")
    private Integer detalleFacCantidad;

    @Column(name = "detalle_fac_total")
    private Integer detalleFacTotal;

    @ManyToOne
    @JsonIgnoreProperties("detalleFacturas")
    private Facturas facturas;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDetalleFactID() {
        return detalleFactID;
    }

    public DetalleFactura detalleFactID(Integer detalleFactID) {
        this.detalleFactID = detalleFactID;
        return this;
    }

    public void setDetalleFactID(Integer detalleFactID) {
        this.detalleFactID = detalleFactID;
    }

    public Integer getFacturaNro() {
        return facturaNro;
    }

    public DetalleFactura facturaNro(Integer facturaNro) {
        this.facturaNro = facturaNro;
        return this;
    }

    public void setFacturaNro(Integer facturaNro) {
        this.facturaNro = facturaNro;
    }

    public String getDetalleFacDescripcion() {
        return detalleFacDescripcion;
    }

    public DetalleFactura detalleFacDescripcion(String detalleFacDescripcion) {
        this.detalleFacDescripcion = detalleFacDescripcion;
        return this;
    }

    public void setDetalleFacDescripcion(String detalleFacDescripcion) {
        this.detalleFacDescripcion = detalleFacDescripcion;
    }

    public Integer getDetalleFacPrecioUnitario() {
        return detalleFacPrecioUnitario;
    }

    public DetalleFactura detalleFacPrecioUnitario(Integer detalleFacPrecioUnitario) {
        this.detalleFacPrecioUnitario = detalleFacPrecioUnitario;
        return this;
    }

    public void setDetalleFacPrecioUnitario(Integer detalleFacPrecioUnitario) {
        this.detalleFacPrecioUnitario = detalleFacPrecioUnitario;
    }

    public Integer getDetalleFacCantidad() {
        return detalleFacCantidad;
    }

    public DetalleFactura detalleFacCantidad(Integer detalleFacCantidad) {
        this.detalleFacCantidad = detalleFacCantidad;
        return this;
    }

    public void setDetalleFacCantidad(Integer detalleFacCantidad) {
        this.detalleFacCantidad = detalleFacCantidad;
    }

    public Integer getDetalleFacTotal() {
        return detalleFacTotal;
    }

    public DetalleFactura detalleFacTotal(Integer detalleFacTotal) {
        this.detalleFacTotal = detalleFacTotal;
        return this;
    }

    public void setDetalleFacTotal(Integer detalleFacTotal) {
        this.detalleFacTotal = detalleFacTotal;
    }

    public Facturas getFacturas() {
        return facturas;
    }

    public DetalleFactura facturas(Facturas facturas) {
        this.facturas = facturas;
        return this;
    }

    public void setFacturas(Facturas facturas) {
        this.facturas = facturas;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DetalleFactura)) {
            return false;
        }
        return id != null && id.equals(((DetalleFactura) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DetalleFactura{" +
            "id=" + getId() +
            ", detalleFactID=" + getDetalleFactID() +
            ", facturaNro=" + getFacturaNro() +
            ", detalleFacDescripcion='" + getDetalleFacDescripcion() + "'" +
            ", detalleFacPrecioUnitario=" + getDetalleFacPrecioUnitario() +
            ", detalleFacCantidad=" + getDetalleFacCantidad() +
            ", detalleFacTotal=" + getDetalleFacTotal() +
            "}";
    }
}
