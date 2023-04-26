package com.udea.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ViceDecano.
 */
@Entity
@Table(name = "vice_decano")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ViceDecano implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "oficina")
    private String oficina;

    @Column(name = "name_facultad")
    private String nameFacultad;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ViceDecano id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public ViceDecano nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return this.email;
    }

    public ViceDecano email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOficina() {
        return this.oficina;
    }

    public ViceDecano oficina(String oficina) {
        this.setOficina(oficina);
        return this;
    }

    public void setOficina(String oficina) {
        this.oficina = oficina;
    }

    public String getNameFacultad() {
        return this.nameFacultad;
    }

    public ViceDecano nameFacultad(String nameFacultad) {
        this.setNameFacultad(nameFacultad);
        return this;
    }

    public void setNameFacultad(String nameFacultad) {
        this.nameFacultad = nameFacultad;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ViceDecano)) {
            return false;
        }
        return id != null && id.equals(((ViceDecano) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ViceDecano{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", email='" + getEmail() + "'" +
            ", oficina='" + getOficina() + "'" +
            ", nameFacultad='" + getNameFacultad() + "'" +
            "}";
    }
}
