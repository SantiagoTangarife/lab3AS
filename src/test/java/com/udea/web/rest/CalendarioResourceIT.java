package com.udea.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.udea.IntegrationTest;
import com.udea.domain.Calendario;
import com.udea.repository.CalendarioRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CalendarioResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CalendarioResourceIT {

    private static final String DEFAULT_SEMESTRE = "AAAAAAAAAA";
    private static final String UPDATED_SEMESTRE = "BBBBBBBBBB";

    private static final Instant DEFAULT_PUBLICACION_OFERTA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PUBLICACION_OFERTA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_INICIO_MATRICULAS = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INICIO_MATRICULAS = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FIN_MATRICULAS = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FIN_MATRICULAS = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_INICIO_AJUSTES = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INICIO_AJUSTES = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FIN_AJUSTES = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FIN_AJUSTES = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_INICIO_CLASES = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INICIO_CLASES = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FIN_CLASES = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FIN_CLASES = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_INICIO_EXAMENES_FINALES = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INICIO_EXAMENES_FINALES = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FIN_EXAMENES_FINALES = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FIN_EXAMENES_FINALES = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_INICIO_VALIDACIONES = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INICIO_VALIDACIONES = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FIN_VALIDACIONES = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FIN_VALIDACIONES = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_INICIO_HABILITACIONES = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INICIO_HABILITACIONES = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FIN_HABILITACIONES = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FIN_HABILITACIONES = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_TERMINACION_OFICINAL = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TERMINACION_OFICINAL = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/calendarios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CalendarioRepository calendarioRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCalendarioMockMvc;

    private Calendario calendario;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Calendario createEntity(EntityManager em) {
        Calendario calendario = new Calendario()
            .semestre(DEFAULT_SEMESTRE)
            .publicacionOferta(DEFAULT_PUBLICACION_OFERTA)
            .inicioMatriculas(DEFAULT_INICIO_MATRICULAS)
            .finMatriculas(DEFAULT_FIN_MATRICULAS)
            .inicioAjustes(DEFAULT_INICIO_AJUSTES)
            .finAjustes(DEFAULT_FIN_AJUSTES)
            .inicioClases(DEFAULT_INICIO_CLASES)
            .finClases(DEFAULT_FIN_CLASES)
            .inicioExamenesFinales(DEFAULT_INICIO_EXAMENES_FINALES)
            .finExamenesFinales(DEFAULT_FIN_EXAMENES_FINALES)
            .inicioValidaciones(DEFAULT_INICIO_VALIDACIONES)
            .finValidaciones(DEFAULT_FIN_VALIDACIONES)
            .inicioHabilitaciones(DEFAULT_INICIO_HABILITACIONES)
            .finHabilitaciones(DEFAULT_FIN_HABILITACIONES)
            .terminacionOficinal(DEFAULT_TERMINACION_OFICINAL);
        return calendario;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Calendario createUpdatedEntity(EntityManager em) {
        Calendario calendario = new Calendario()
            .semestre(UPDATED_SEMESTRE)
            .publicacionOferta(UPDATED_PUBLICACION_OFERTA)
            .inicioMatriculas(UPDATED_INICIO_MATRICULAS)
            .finMatriculas(UPDATED_FIN_MATRICULAS)
            .inicioAjustes(UPDATED_INICIO_AJUSTES)
            .finAjustes(UPDATED_FIN_AJUSTES)
            .inicioClases(UPDATED_INICIO_CLASES)
            .finClases(UPDATED_FIN_CLASES)
            .inicioExamenesFinales(UPDATED_INICIO_EXAMENES_FINALES)
            .finExamenesFinales(UPDATED_FIN_EXAMENES_FINALES)
            .inicioValidaciones(UPDATED_INICIO_VALIDACIONES)
            .finValidaciones(UPDATED_FIN_VALIDACIONES)
            .inicioHabilitaciones(UPDATED_INICIO_HABILITACIONES)
            .finHabilitaciones(UPDATED_FIN_HABILITACIONES)
            .terminacionOficinal(UPDATED_TERMINACION_OFICINAL);
        return calendario;
    }

    @BeforeEach
    public void initTest() {
        calendario = createEntity(em);
    }

    @Test
    @Transactional
    void createCalendario() throws Exception {
        int databaseSizeBeforeCreate = calendarioRepository.findAll().size();
        // Create the Calendario
        restCalendarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(calendario)))
            .andExpect(status().isCreated());

        // Validate the Calendario in the database
        List<Calendario> calendarioList = calendarioRepository.findAll();
        assertThat(calendarioList).hasSize(databaseSizeBeforeCreate + 1);
        Calendario testCalendario = calendarioList.get(calendarioList.size() - 1);
        assertThat(testCalendario.getSemestre()).isEqualTo(DEFAULT_SEMESTRE);
        assertThat(testCalendario.getPublicacionOferta()).isEqualTo(DEFAULT_PUBLICACION_OFERTA);
        assertThat(testCalendario.getInicioMatriculas()).isEqualTo(DEFAULT_INICIO_MATRICULAS);
        assertThat(testCalendario.getFinMatriculas()).isEqualTo(DEFAULT_FIN_MATRICULAS);
        assertThat(testCalendario.getInicioAjustes()).isEqualTo(DEFAULT_INICIO_AJUSTES);
        assertThat(testCalendario.getFinAjustes()).isEqualTo(DEFAULT_FIN_AJUSTES);
        assertThat(testCalendario.getInicioClases()).isEqualTo(DEFAULT_INICIO_CLASES);
        assertThat(testCalendario.getFinClases()).isEqualTo(DEFAULT_FIN_CLASES);
        assertThat(testCalendario.getInicioExamenesFinales()).isEqualTo(DEFAULT_INICIO_EXAMENES_FINALES);
        assertThat(testCalendario.getFinExamenesFinales()).isEqualTo(DEFAULT_FIN_EXAMENES_FINALES);
        assertThat(testCalendario.getInicioValidaciones()).isEqualTo(DEFAULT_INICIO_VALIDACIONES);
        assertThat(testCalendario.getFinValidaciones()).isEqualTo(DEFAULT_FIN_VALIDACIONES);
        assertThat(testCalendario.getInicioHabilitaciones()).isEqualTo(DEFAULT_INICIO_HABILITACIONES);
        assertThat(testCalendario.getFinHabilitaciones()).isEqualTo(DEFAULT_FIN_HABILITACIONES);
        assertThat(testCalendario.getTerminacionOficinal()).isEqualTo(DEFAULT_TERMINACION_OFICINAL);
    }

    @Test
    @Transactional
    void createCalendarioWithExistingId() throws Exception {
        // Create the Calendario with an existing ID
        calendario.setId(1L);

        int databaseSizeBeforeCreate = calendarioRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCalendarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(calendario)))
            .andExpect(status().isBadRequest());

        // Validate the Calendario in the database
        List<Calendario> calendarioList = calendarioRepository.findAll();
        assertThat(calendarioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCalendarios() throws Exception {
        // Initialize the database
        calendarioRepository.saveAndFlush(calendario);

        // Get all the calendarioList
        restCalendarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(calendario.getId().intValue())))
            .andExpect(jsonPath("$.[*].semestre").value(hasItem(DEFAULT_SEMESTRE)))
            .andExpect(jsonPath("$.[*].publicacionOferta").value(hasItem(DEFAULT_PUBLICACION_OFERTA.toString())))
            .andExpect(jsonPath("$.[*].inicioMatriculas").value(hasItem(DEFAULT_INICIO_MATRICULAS.toString())))
            .andExpect(jsonPath("$.[*].finMatriculas").value(hasItem(DEFAULT_FIN_MATRICULAS.toString())))
            .andExpect(jsonPath("$.[*].inicioAjustes").value(hasItem(DEFAULT_INICIO_AJUSTES.toString())))
            .andExpect(jsonPath("$.[*].finAjustes").value(hasItem(DEFAULT_FIN_AJUSTES.toString())))
            .andExpect(jsonPath("$.[*].inicioClases").value(hasItem(DEFAULT_INICIO_CLASES.toString())))
            .andExpect(jsonPath("$.[*].finClases").value(hasItem(DEFAULT_FIN_CLASES.toString())))
            .andExpect(jsonPath("$.[*].inicioExamenesFinales").value(hasItem(DEFAULT_INICIO_EXAMENES_FINALES.toString())))
            .andExpect(jsonPath("$.[*].finExamenesFinales").value(hasItem(DEFAULT_FIN_EXAMENES_FINALES.toString())))
            .andExpect(jsonPath("$.[*].inicioValidaciones").value(hasItem(DEFAULT_INICIO_VALIDACIONES.toString())))
            .andExpect(jsonPath("$.[*].finValidaciones").value(hasItem(DEFAULT_FIN_VALIDACIONES.toString())))
            .andExpect(jsonPath("$.[*].inicioHabilitaciones").value(hasItem(DEFAULT_INICIO_HABILITACIONES.toString())))
            .andExpect(jsonPath("$.[*].finHabilitaciones").value(hasItem(DEFAULT_FIN_HABILITACIONES.toString())))
            .andExpect(jsonPath("$.[*].terminacionOficinal").value(hasItem(DEFAULT_TERMINACION_OFICINAL.toString())));
    }

    @Test
    @Transactional
    void getCalendario() throws Exception {
        // Initialize the database
        calendarioRepository.saveAndFlush(calendario);

        // Get the calendario
        restCalendarioMockMvc
            .perform(get(ENTITY_API_URL_ID, calendario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(calendario.getId().intValue()))
            .andExpect(jsonPath("$.semestre").value(DEFAULT_SEMESTRE))
            .andExpect(jsonPath("$.publicacionOferta").value(DEFAULT_PUBLICACION_OFERTA.toString()))
            .andExpect(jsonPath("$.inicioMatriculas").value(DEFAULT_INICIO_MATRICULAS.toString()))
            .andExpect(jsonPath("$.finMatriculas").value(DEFAULT_FIN_MATRICULAS.toString()))
            .andExpect(jsonPath("$.inicioAjustes").value(DEFAULT_INICIO_AJUSTES.toString()))
            .andExpect(jsonPath("$.finAjustes").value(DEFAULT_FIN_AJUSTES.toString()))
            .andExpect(jsonPath("$.inicioClases").value(DEFAULT_INICIO_CLASES.toString()))
            .andExpect(jsonPath("$.finClases").value(DEFAULT_FIN_CLASES.toString()))
            .andExpect(jsonPath("$.inicioExamenesFinales").value(DEFAULT_INICIO_EXAMENES_FINALES.toString()))
            .andExpect(jsonPath("$.finExamenesFinales").value(DEFAULT_FIN_EXAMENES_FINALES.toString()))
            .andExpect(jsonPath("$.inicioValidaciones").value(DEFAULT_INICIO_VALIDACIONES.toString()))
            .andExpect(jsonPath("$.finValidaciones").value(DEFAULT_FIN_VALIDACIONES.toString()))
            .andExpect(jsonPath("$.inicioHabilitaciones").value(DEFAULT_INICIO_HABILITACIONES.toString()))
            .andExpect(jsonPath("$.finHabilitaciones").value(DEFAULT_FIN_HABILITACIONES.toString()))
            .andExpect(jsonPath("$.terminacionOficinal").value(DEFAULT_TERMINACION_OFICINAL.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCalendario() throws Exception {
        // Get the calendario
        restCalendarioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCalendario() throws Exception {
        // Initialize the database
        calendarioRepository.saveAndFlush(calendario);

        int databaseSizeBeforeUpdate = calendarioRepository.findAll().size();

        // Update the calendario
        Calendario updatedCalendario = calendarioRepository.findById(calendario.getId()).get();
        // Disconnect from session so that the updates on updatedCalendario are not directly saved in db
        em.detach(updatedCalendario);
        updatedCalendario
            .semestre(UPDATED_SEMESTRE)
            .publicacionOferta(UPDATED_PUBLICACION_OFERTA)
            .inicioMatriculas(UPDATED_INICIO_MATRICULAS)
            .finMatriculas(UPDATED_FIN_MATRICULAS)
            .inicioAjustes(UPDATED_INICIO_AJUSTES)
            .finAjustes(UPDATED_FIN_AJUSTES)
            .inicioClases(UPDATED_INICIO_CLASES)
            .finClases(UPDATED_FIN_CLASES)
            .inicioExamenesFinales(UPDATED_INICIO_EXAMENES_FINALES)
            .finExamenesFinales(UPDATED_FIN_EXAMENES_FINALES)
            .inicioValidaciones(UPDATED_INICIO_VALIDACIONES)
            .finValidaciones(UPDATED_FIN_VALIDACIONES)
            .inicioHabilitaciones(UPDATED_INICIO_HABILITACIONES)
            .finHabilitaciones(UPDATED_FIN_HABILITACIONES)
            .terminacionOficinal(UPDATED_TERMINACION_OFICINAL);

        restCalendarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCalendario.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCalendario))
            )
            .andExpect(status().isOk());

        // Validate the Calendario in the database
        List<Calendario> calendarioList = calendarioRepository.findAll();
        assertThat(calendarioList).hasSize(databaseSizeBeforeUpdate);
        Calendario testCalendario = calendarioList.get(calendarioList.size() - 1);
        assertThat(testCalendario.getSemestre()).isEqualTo(UPDATED_SEMESTRE);
        assertThat(testCalendario.getPublicacionOferta()).isEqualTo(UPDATED_PUBLICACION_OFERTA);
        assertThat(testCalendario.getInicioMatriculas()).isEqualTo(UPDATED_INICIO_MATRICULAS);
        assertThat(testCalendario.getFinMatriculas()).isEqualTo(UPDATED_FIN_MATRICULAS);
        assertThat(testCalendario.getInicioAjustes()).isEqualTo(UPDATED_INICIO_AJUSTES);
        assertThat(testCalendario.getFinAjustes()).isEqualTo(UPDATED_FIN_AJUSTES);
        assertThat(testCalendario.getInicioClases()).isEqualTo(UPDATED_INICIO_CLASES);
        assertThat(testCalendario.getFinClases()).isEqualTo(UPDATED_FIN_CLASES);
        assertThat(testCalendario.getInicioExamenesFinales()).isEqualTo(UPDATED_INICIO_EXAMENES_FINALES);
        assertThat(testCalendario.getFinExamenesFinales()).isEqualTo(UPDATED_FIN_EXAMENES_FINALES);
        assertThat(testCalendario.getInicioValidaciones()).isEqualTo(UPDATED_INICIO_VALIDACIONES);
        assertThat(testCalendario.getFinValidaciones()).isEqualTo(UPDATED_FIN_VALIDACIONES);
        assertThat(testCalendario.getInicioHabilitaciones()).isEqualTo(UPDATED_INICIO_HABILITACIONES);
        assertThat(testCalendario.getFinHabilitaciones()).isEqualTo(UPDATED_FIN_HABILITACIONES);
        assertThat(testCalendario.getTerminacionOficinal()).isEqualTo(UPDATED_TERMINACION_OFICINAL);
    }

    @Test
    @Transactional
    void putNonExistingCalendario() throws Exception {
        int databaseSizeBeforeUpdate = calendarioRepository.findAll().size();
        calendario.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCalendarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, calendario.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(calendario))
            )
            .andExpect(status().isBadRequest());

        // Validate the Calendario in the database
        List<Calendario> calendarioList = calendarioRepository.findAll();
        assertThat(calendarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCalendario() throws Exception {
        int databaseSizeBeforeUpdate = calendarioRepository.findAll().size();
        calendario.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCalendarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(calendario))
            )
            .andExpect(status().isBadRequest());

        // Validate the Calendario in the database
        List<Calendario> calendarioList = calendarioRepository.findAll();
        assertThat(calendarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCalendario() throws Exception {
        int databaseSizeBeforeUpdate = calendarioRepository.findAll().size();
        calendario.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCalendarioMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(calendario)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Calendario in the database
        List<Calendario> calendarioList = calendarioRepository.findAll();
        assertThat(calendarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCalendarioWithPatch() throws Exception {
        // Initialize the database
        calendarioRepository.saveAndFlush(calendario);

        int databaseSizeBeforeUpdate = calendarioRepository.findAll().size();

        // Update the calendario using partial update
        Calendario partialUpdatedCalendario = new Calendario();
        partialUpdatedCalendario.setId(calendario.getId());

        partialUpdatedCalendario
            .inicioMatriculas(UPDATED_INICIO_MATRICULAS)
            .finMatriculas(UPDATED_FIN_MATRICULAS)
            .inicioAjustes(UPDATED_INICIO_AJUSTES)
            .finValidaciones(UPDATED_FIN_VALIDACIONES)
            .inicioHabilitaciones(UPDATED_INICIO_HABILITACIONES);

        restCalendarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCalendario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCalendario))
            )
            .andExpect(status().isOk());

        // Validate the Calendario in the database
        List<Calendario> calendarioList = calendarioRepository.findAll();
        assertThat(calendarioList).hasSize(databaseSizeBeforeUpdate);
        Calendario testCalendario = calendarioList.get(calendarioList.size() - 1);
        assertThat(testCalendario.getSemestre()).isEqualTo(DEFAULT_SEMESTRE);
        assertThat(testCalendario.getPublicacionOferta()).isEqualTo(DEFAULT_PUBLICACION_OFERTA);
        assertThat(testCalendario.getInicioMatriculas()).isEqualTo(UPDATED_INICIO_MATRICULAS);
        assertThat(testCalendario.getFinMatriculas()).isEqualTo(UPDATED_FIN_MATRICULAS);
        assertThat(testCalendario.getInicioAjustes()).isEqualTo(UPDATED_INICIO_AJUSTES);
        assertThat(testCalendario.getFinAjustes()).isEqualTo(DEFAULT_FIN_AJUSTES);
        assertThat(testCalendario.getInicioClases()).isEqualTo(DEFAULT_INICIO_CLASES);
        assertThat(testCalendario.getFinClases()).isEqualTo(DEFAULT_FIN_CLASES);
        assertThat(testCalendario.getInicioExamenesFinales()).isEqualTo(DEFAULT_INICIO_EXAMENES_FINALES);
        assertThat(testCalendario.getFinExamenesFinales()).isEqualTo(DEFAULT_FIN_EXAMENES_FINALES);
        assertThat(testCalendario.getInicioValidaciones()).isEqualTo(DEFAULT_INICIO_VALIDACIONES);
        assertThat(testCalendario.getFinValidaciones()).isEqualTo(UPDATED_FIN_VALIDACIONES);
        assertThat(testCalendario.getInicioHabilitaciones()).isEqualTo(UPDATED_INICIO_HABILITACIONES);
        assertThat(testCalendario.getFinHabilitaciones()).isEqualTo(DEFAULT_FIN_HABILITACIONES);
        assertThat(testCalendario.getTerminacionOficinal()).isEqualTo(DEFAULT_TERMINACION_OFICINAL);
    }

    @Test
    @Transactional
    void fullUpdateCalendarioWithPatch() throws Exception {
        // Initialize the database
        calendarioRepository.saveAndFlush(calendario);

        int databaseSizeBeforeUpdate = calendarioRepository.findAll().size();

        // Update the calendario using partial update
        Calendario partialUpdatedCalendario = new Calendario();
        partialUpdatedCalendario.setId(calendario.getId());

        partialUpdatedCalendario
            .semestre(UPDATED_SEMESTRE)
            .publicacionOferta(UPDATED_PUBLICACION_OFERTA)
            .inicioMatriculas(UPDATED_INICIO_MATRICULAS)
            .finMatriculas(UPDATED_FIN_MATRICULAS)
            .inicioAjustes(UPDATED_INICIO_AJUSTES)
            .finAjustes(UPDATED_FIN_AJUSTES)
            .inicioClases(UPDATED_INICIO_CLASES)
            .finClases(UPDATED_FIN_CLASES)
            .inicioExamenesFinales(UPDATED_INICIO_EXAMENES_FINALES)
            .finExamenesFinales(UPDATED_FIN_EXAMENES_FINALES)
            .inicioValidaciones(UPDATED_INICIO_VALIDACIONES)
            .finValidaciones(UPDATED_FIN_VALIDACIONES)
            .inicioHabilitaciones(UPDATED_INICIO_HABILITACIONES)
            .finHabilitaciones(UPDATED_FIN_HABILITACIONES)
            .terminacionOficinal(UPDATED_TERMINACION_OFICINAL);

        restCalendarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCalendario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCalendario))
            )
            .andExpect(status().isOk());

        // Validate the Calendario in the database
        List<Calendario> calendarioList = calendarioRepository.findAll();
        assertThat(calendarioList).hasSize(databaseSizeBeforeUpdate);
        Calendario testCalendario = calendarioList.get(calendarioList.size() - 1);
        assertThat(testCalendario.getSemestre()).isEqualTo(UPDATED_SEMESTRE);
        assertThat(testCalendario.getPublicacionOferta()).isEqualTo(UPDATED_PUBLICACION_OFERTA);
        assertThat(testCalendario.getInicioMatriculas()).isEqualTo(UPDATED_INICIO_MATRICULAS);
        assertThat(testCalendario.getFinMatriculas()).isEqualTo(UPDATED_FIN_MATRICULAS);
        assertThat(testCalendario.getInicioAjustes()).isEqualTo(UPDATED_INICIO_AJUSTES);
        assertThat(testCalendario.getFinAjustes()).isEqualTo(UPDATED_FIN_AJUSTES);
        assertThat(testCalendario.getInicioClases()).isEqualTo(UPDATED_INICIO_CLASES);
        assertThat(testCalendario.getFinClases()).isEqualTo(UPDATED_FIN_CLASES);
        assertThat(testCalendario.getInicioExamenesFinales()).isEqualTo(UPDATED_INICIO_EXAMENES_FINALES);
        assertThat(testCalendario.getFinExamenesFinales()).isEqualTo(UPDATED_FIN_EXAMENES_FINALES);
        assertThat(testCalendario.getInicioValidaciones()).isEqualTo(UPDATED_INICIO_VALIDACIONES);
        assertThat(testCalendario.getFinValidaciones()).isEqualTo(UPDATED_FIN_VALIDACIONES);
        assertThat(testCalendario.getInicioHabilitaciones()).isEqualTo(UPDATED_INICIO_HABILITACIONES);
        assertThat(testCalendario.getFinHabilitaciones()).isEqualTo(UPDATED_FIN_HABILITACIONES);
        assertThat(testCalendario.getTerminacionOficinal()).isEqualTo(UPDATED_TERMINACION_OFICINAL);
    }

    @Test
    @Transactional
    void patchNonExistingCalendario() throws Exception {
        int databaseSizeBeforeUpdate = calendarioRepository.findAll().size();
        calendario.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCalendarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, calendario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(calendario))
            )
            .andExpect(status().isBadRequest());

        // Validate the Calendario in the database
        List<Calendario> calendarioList = calendarioRepository.findAll();
        assertThat(calendarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCalendario() throws Exception {
        int databaseSizeBeforeUpdate = calendarioRepository.findAll().size();
        calendario.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCalendarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(calendario))
            )
            .andExpect(status().isBadRequest());

        // Validate the Calendario in the database
        List<Calendario> calendarioList = calendarioRepository.findAll();
        assertThat(calendarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCalendario() throws Exception {
        int databaseSizeBeforeUpdate = calendarioRepository.findAll().size();
        calendario.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCalendarioMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(calendario))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Calendario in the database
        List<Calendario> calendarioList = calendarioRepository.findAll();
        assertThat(calendarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCalendario() throws Exception {
        // Initialize the database
        calendarioRepository.saveAndFlush(calendario);

        int databaseSizeBeforeDelete = calendarioRepository.findAll().size();

        // Delete the calendario
        restCalendarioMockMvc
            .perform(delete(ENTITY_API_URL_ID, calendario.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Calendario> calendarioList = calendarioRepository.findAll();
        assertThat(calendarioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
