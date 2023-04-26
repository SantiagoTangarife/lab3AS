package com.udea.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Calendario.
 */
@Entity
@Table(name = "calendario")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Calendario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "semestre")
    private String semestre;

    @Column(name = "publicacion_oferta")
    private Instant publicacionOferta;

    @Column(name = "inicio_matriculas")
    private Instant inicioMatriculas;

    @Column(name = "fin_matriculas")
    private Instant finMatriculas;

    @Column(name = "inicio_ajustes")
    private Instant inicioAjustes;

    @Column(name = "fin_ajustes")
    private Instant finAjustes;

    @Column(name = "inicio_clases")
    private Instant inicioClases;

    @Column(name = "fin_clases")
    private Instant finClases;

    @Column(name = "inicio_examenes_finales")
    private Instant inicioExamenesFinales;

    @Column(name = "fin_examenes_finales")
    private Instant finExamenesFinales;

    @Column(name = "inicio_validaciones")
    private Instant inicioValidaciones;

    @Column(name = "fin_validaciones")
    private Instant finValidaciones;

    @Column(name = "inicio_habilitaciones")
    private Instant inicioHabilitaciones;

    @Column(name = "fin_habilitaciones")
    private Instant finHabilitaciones;

    @Column(name = "terminacion_oficinal")
    private Instant terminacionOficinal;

    @ManyToOne
    @JsonIgnoreProperties(value = { "decano", "viceDecano" }, allowSetters = true)
    private Facultad facultad;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Calendario id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSemestre() {
        return this.semestre;
    }

    public Calendario semestre(String semestre) {
        this.setSemestre(semestre);
        return this;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public Instant getPublicacionOferta() {
        return this.publicacionOferta;
    }

    public Calendario publicacionOferta(Instant publicacionOferta) {
        this.setPublicacionOferta(publicacionOferta);
        return this;
    }

    public void setPublicacionOferta(Instant publicacionOferta) {
        this.publicacionOferta = publicacionOferta;
    }

    public Instant getInicioMatriculas() {
        return this.inicioMatriculas;
    }

    public Calendario inicioMatriculas(Instant inicioMatriculas) {
        this.setInicioMatriculas(inicioMatriculas);
        return this;
    }

    public void setInicioMatriculas(Instant inicioMatriculas) {
        this.inicioMatriculas = inicioMatriculas;
    }

    public Instant getFinMatriculas() {
        return this.finMatriculas;
    }

    public Calendario finMatriculas(Instant finMatriculas) {
        this.setFinMatriculas(finMatriculas);
        return this;
    }

    public void setFinMatriculas(Instant finMatriculas) {
        this.finMatriculas = finMatriculas;
    }

    public Instant getInicioAjustes() {
        return this.inicioAjustes;
    }

    public Calendario inicioAjustes(Instant inicioAjustes) {
        this.setInicioAjustes(inicioAjustes);
        return this;
    }

    public void setInicioAjustes(Instant inicioAjustes) {
        this.inicioAjustes = inicioAjustes;
    }

    public Instant getFinAjustes() {
        return this.finAjustes;
    }

    public Calendario finAjustes(Instant finAjustes) {
        this.setFinAjustes(finAjustes);
        return this;
    }

    public void setFinAjustes(Instant finAjustes) {
        this.finAjustes = finAjustes;
    }

    public Instant getInicioClases() {
        return this.inicioClases;
    }

    public Calendario inicioClases(Instant inicioClases) {
        this.setInicioClases(inicioClases);
        return this;
    }

    public void setInicioClases(Instant inicioClases) {
        this.inicioClases = inicioClases;
    }

    public Instant getFinClases() {
        return this.finClases;
    }

    public Calendario finClases(Instant finClases) {
        this.setFinClases(finClases);
        return this;
    }

    public void setFinClases(Instant finClases) {
        this.finClases = finClases;
    }

    public Instant getInicioExamenesFinales() {
        return this.inicioExamenesFinales;
    }

    public Calendario inicioExamenesFinales(Instant inicioExamenesFinales) {
        this.setInicioExamenesFinales(inicioExamenesFinales);
        return this;
    }

    public void setInicioExamenesFinales(Instant inicioExamenesFinales) {
        this.inicioExamenesFinales = inicioExamenesFinales;
    }

    public Instant getFinExamenesFinales() {
        return this.finExamenesFinales;
    }

    public Calendario finExamenesFinales(Instant finExamenesFinales) {
        this.setFinExamenesFinales(finExamenesFinales);
        return this;
    }

    public void setFinExamenesFinales(Instant finExamenesFinales) {
        this.finExamenesFinales = finExamenesFinales;
    }

    public Instant getInicioValidaciones() {
        return this.inicioValidaciones;
    }

    public Calendario inicioValidaciones(Instant inicioValidaciones) {
        this.setInicioValidaciones(inicioValidaciones);
        return this;
    }

    public void setInicioValidaciones(Instant inicioValidaciones) {
        this.inicioValidaciones = inicioValidaciones;
    }

    public Instant getFinValidaciones() {
        return this.finValidaciones;
    }

    public Calendario finValidaciones(Instant finValidaciones) {
        this.setFinValidaciones(finValidaciones);
        return this;
    }

    public void setFinValidaciones(Instant finValidaciones) {
        this.finValidaciones = finValidaciones;
    }

    public Instant getInicioHabilitaciones() {
        return this.inicioHabilitaciones;
    }

    public Calendario inicioHabilitaciones(Instant inicioHabilitaciones) {
        this.setInicioHabilitaciones(inicioHabilitaciones);
        return this;
    }

    public void setInicioHabilitaciones(Instant inicioHabilitaciones) {
        this.inicioHabilitaciones = inicioHabilitaciones;
    }

    public Instant getFinHabilitaciones() {
        return this.finHabilitaciones;
    }

    public Calendario finHabilitaciones(Instant finHabilitaciones) {
        this.setFinHabilitaciones(finHabilitaciones);
        return this;
    }

    public void setFinHabilitaciones(Instant finHabilitaciones) {
        this.finHabilitaciones = finHabilitaciones;
    }

    public Instant getTerminacionOficinal() {
        return this.terminacionOficinal;
    }

    public Calendario terminacionOficinal(Instant terminacionOficinal) {
        this.setTerminacionOficinal(terminacionOficinal);
        return this;
    }

    public void setTerminacionOficinal(Instant terminacionOficinal) {
        this.terminacionOficinal = terminacionOficinal;
    }

    public Facultad getFacultad() {
        return this.facultad;
    }

    public void setFacultad(Facultad facultad) {
        this.facultad = facultad;
    }

    public Calendario facultad(Facultad facultad) {
        this.setFacultad(facultad);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Calendario)) {
            return false;
        }
        return id != null && id.equals(((Calendario) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Calendario{" +
            "id=" + getId() +
            ", semestre='" + getSemestre() + "'" +
            ", publicacionOferta='" + getPublicacionOferta() + "'" +
            ", inicioMatriculas='" + getInicioMatriculas() + "'" +
            ", finMatriculas='" + getFinMatriculas() + "'" +
            ", inicioAjustes='" + getInicioAjustes() + "'" +
            ", finAjustes='" + getFinAjustes() + "'" +
            ", inicioClases='" + getInicioClases() + "'" +
            ", finClases='" + getFinClases() + "'" +
            ", inicioExamenesFinales='" + getInicioExamenesFinales() + "'" +
            ", finExamenesFinales='" + getFinExamenesFinales() + "'" +
            ", inicioValidaciones='" + getInicioValidaciones() + "'" +
            ", finValidaciones='" + getFinValidaciones() + "'" +
            ", inicioHabilitaciones='" + getInicioHabilitaciones() + "'" +
            ", finHabilitaciones='" + getFinHabilitaciones() + "'" +
            ", terminacionOficinal='" + getTerminacionOficinal() + "'" +
            "}";
    }
}
