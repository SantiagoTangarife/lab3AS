package com.udea.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Facultad.
 */
@Entity
@Table(name = "facultad")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Facultad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @OneToOne
    @JoinColumn(unique = true)
    private Decano decano;

    @OneToOne
    @JoinColumn(unique = true)
    private ViceDecano viceDecano;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Facultad id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Facultad nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Decano getDecano() {
        return this.decano;
    }

    public void setDecano(Decano decano) {
        this.decano = decano;
    }

    public Facultad decano(Decano decano) {
        this.setDecano(decano);
        return this;
    }

    public ViceDecano getViceDecano() {
        return this.viceDecano;
    }

    public void setViceDecano(ViceDecano viceDecano) {
        this.viceDecano = viceDecano;
    }

    public Facultad viceDecano(ViceDecano viceDecano) {
        this.setViceDecano(viceDecano);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Facultad)) {
            return false;
        }
        return id != null && id.equals(((Facultad) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Facultad{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
