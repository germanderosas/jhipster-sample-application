package io.github.jhipster.application.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Clientes.
 */
@Entity
@Table(name = "clientes")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Clientes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "cliente_nombre", nullable = false)
    private String clienteNombre;

    @Column(name = "cliente_domicilio")
    private String clienteDomicilio;

    @Column(name = "cliente_cuit")
    private Integer clienteCuit;

    @Column(name = "cliente_id")
    private Integer clienteID;

    @OneToMany(mappedBy = "clientes")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Facturas> clienteIDS = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public Clientes clienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
        return this;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public String getClienteDomicilio() {
        return clienteDomicilio;
    }

    public Clientes clienteDomicilio(String clienteDomicilio) {
        this.clienteDomicilio = clienteDomicilio;
        return this;
    }

    public void setClienteDomicilio(String clienteDomicilio) {
        this.clienteDomicilio = clienteDomicilio;
    }

    public Integer getClienteCuit() {
        return clienteCuit;
    }

    public Clientes clienteCuit(Integer clienteCuit) {
        this.clienteCuit = clienteCuit;
        return this;
    }

    public void setClienteCuit(Integer clienteCuit) {
        this.clienteCuit = clienteCuit;
    }

    public Integer getClienteID() {
        return clienteID;
    }

    public Clientes clienteID(Integer clienteID) {
        this.clienteID = clienteID;
        return this;
    }

    public void setClienteID(Integer clienteID) {
        this.clienteID = clienteID;
    }

    public Set<Facturas> getClienteIDS() {
        return clienteIDS;
    }

    public Clientes clienteIDS(Set<Facturas> facturas) {
        this.clienteIDS = facturas;
        return this;
    }

    public Clientes addClienteID(Facturas facturas) {
        this.clienteIDS.add(facturas);
        facturas.setClientes(this);
        return this;
    }

    public Clientes removeClienteID(Facturas facturas) {
        this.clienteIDS.remove(facturas);
        facturas.setClientes(null);
        return this;
    }

    public void setClienteIDS(Set<Facturas> facturas) {
        this.clienteIDS = facturas;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Clientes)) {
            return false;
        }
        return id != null && id.equals(((Clientes) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Clientes{" +
            "id=" + getId() +
            ", clienteNombre='" + getClienteNombre() + "'" +
            ", clienteDomicilio='" + getClienteDomicilio() + "'" +
            ", clienteCuit=" + getClienteCuit() +
            ", clienteID=" + getClienteID() +
            "}";
    }
}
