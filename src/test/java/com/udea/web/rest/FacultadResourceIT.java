package com.udea.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.udea.IntegrationTest;
import com.udea.domain.Facultad;
import com.udea.repository.FacultadRepository;
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
 * Integration tests for the {@link FacultadResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FacultadResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/facultads";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FacultadRepository facultadRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFacultadMockMvc;

    private Facultad facultad;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Facultad createEntity(EntityManager em) {
        Facultad facultad = new Facultad().nombre(DEFAULT_NOMBRE);
        return facultad;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Facultad createUpdatedEntity(EntityManager em) {
        Facultad facultad = new Facultad().nombre(UPDATED_NOMBRE);
        return facultad;
    }

    @BeforeEach
    public void initTest() {
        facultad = createEntity(em);
    }

    @Test
    @Transactional
    void createFacultad() throws Exception {
        int databaseSizeBeforeCreate = facultadRepository.findAll().size();
        // Create the Facultad
        restFacultadMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(facultad)))
            .andExpect(status().isCreated());

        // Validate the Facultad in the database
        List<Facultad> facultadList = facultadRepository.findAll();
        assertThat(facultadList).hasSize(databaseSizeBeforeCreate + 1);
        Facultad testFacultad = facultadList.get(facultadList.size() - 1);
        assertThat(testFacultad.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    void createFacultadWithExistingId() throws Exception {
        // Create the Facultad with an existing ID
        facultad.setId(1L);

        int databaseSizeBeforeCreate = facultadRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFacultadMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(facultad)))
            .andExpect(status().isBadRequest());

        // Validate the Facultad in the database
        List<Facultad> facultadList = facultadRepository.findAll();
        assertThat(facultadList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFacultads() throws Exception {
        // Initialize the database
        facultadRepository.saveAndFlush(facultad);

        // Get all the facultadList
        restFacultadMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(facultad.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)));
    }

    @Test
    @Transactional
    void getFacultad() throws Exception {
        // Initialize the database
        facultadRepository.saveAndFlush(facultad);

        // Get the facultad
        restFacultadMockMvc
            .perform(get(ENTITY_API_URL_ID, facultad.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(facultad.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE));
    }

    @Test
    @Transactional
    void getNonExistingFacultad() throws Exception {
        // Get the facultad
        restFacultadMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFacultad() throws Exception {
        // Initialize the database
        facultadRepository.saveAndFlush(facultad);

        int databaseSizeBeforeUpdate = facultadRepository.findAll().size();

        // Update the facultad
        Facultad updatedFacultad = facultadRepository.findById(facultad.getId()).get();
        // Disconnect from session so that the updates on updatedFacultad are not directly saved in db
        em.detach(updatedFacultad);
        updatedFacultad.nombre(UPDATED_NOMBRE);

        restFacultadMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFacultad.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFacultad))
            )
            .andExpect(status().isOk());

        // Validate the Facultad in the database
        List<Facultad> facultadList = facultadRepository.findAll();
        assertThat(facultadList).hasSize(databaseSizeBeforeUpdate);
        Facultad testFacultad = facultadList.get(facultadList.size() - 1);
        assertThat(testFacultad.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void putNonExistingFacultad() throws Exception {
        int databaseSizeBeforeUpdate = facultadRepository.findAll().size();
        facultad.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFacultadMockMvc
            .perform(
                put(ENTITY_API_URL_ID, facultad.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(facultad))
            )
            .andExpect(status().isBadRequest());

        // Validate the Facultad in the database
        List<Facultad> facultadList = facultadRepository.findAll();
        assertThat(facultadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFacultad() throws Exception {
        int databaseSizeBeforeUpdate = facultadRepository.findAll().size();
        facultad.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFacultadMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(facultad))
            )
            .andExpect(status().isBadRequest());

        // Validate the Facultad in the database
        List<Facultad> facultadList = facultadRepository.findAll();
        assertThat(facultadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFacultad() throws Exception {
        int databaseSizeBeforeUpdate = facultadRepository.findAll().size();
        facultad.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFacultadMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(facultad)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Facultad in the database
        List<Facultad> facultadList = facultadRepository.findAll();
        assertThat(facultadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFacultadWithPatch() throws Exception {
        // Initialize the database
        facultadRepository.saveAndFlush(facultad);

        int databaseSizeBeforeUpdate = facultadRepository.findAll().size();

        // Update the facultad using partial update
        Facultad partialUpdatedFacultad = new Facultad();
        partialUpdatedFacultad.setId(facultad.getId());

        partialUpdatedFacultad.nombre(UPDATED_NOMBRE);

        restFacultadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFacultad.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFacultad))
            )
            .andExpect(status().isOk());

        // Validate the Facultad in the database
        List<Facultad> facultadList = facultadRepository.findAll();
        assertThat(facultadList).hasSize(databaseSizeBeforeUpdate);
        Facultad testFacultad = facultadList.get(facultadList.size() - 1);
        assertThat(testFacultad.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void fullUpdateFacultadWithPatch() throws Exception {
        // Initialize the database
        facultadRepository.saveAndFlush(facultad);

        int databaseSizeBeforeUpdate = facultadRepository.findAll().size();

        // Update the facultad using partial update
        Facultad partialUpdatedFacultad = new Facultad();
        partialUpdatedFacultad.setId(facultad.getId());

        partialUpdatedFacultad.nombre(UPDATED_NOMBRE);

        restFacultadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFacultad.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFacultad))
            )
            .andExpect(status().isOk());

        // Validate the Facultad in the database
        List<Facultad> facultadList = facultadRepository.findAll();
        assertThat(facultadList).hasSize(databaseSizeBeforeUpdate);
        Facultad testFacultad = facultadList.get(facultadList.size() - 1);
        assertThat(testFacultad.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void patchNonExistingFacultad() throws Exception {
        int databaseSizeBeforeUpdate = facultadRepository.findAll().size();
        facultad.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFacultadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, facultad.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(facultad))
            )
            .andExpect(status().isBadRequest());

        // Validate the Facultad in the database
        List<Facultad> facultadList = facultadRepository.findAll();
        assertThat(facultadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFacultad() throws Exception {
        int databaseSizeBeforeUpdate = facultadRepository.findAll().size();
        facultad.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFacultadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(facultad))
            )
            .andExpect(status().isBadRequest());

        // Validate the Facultad in the database
        List<Facultad> facultadList = facultadRepository.findAll();
        assertThat(facultadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFacultad() throws Exception {
        int databaseSizeBeforeUpdate = facultadRepository.findAll().size();
        facultad.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFacultadMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(facultad)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Facultad in the database
        List<Facultad> facultadList = facultadRepository.findAll();
        assertThat(facultadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFacultad() throws Exception {
        // Initialize the database
        facultadRepository.saveAndFlush(facultad);

        int databaseSizeBeforeDelete = facultadRepository.findAll().size();

        // Delete the facultad
        restFacultadMockMvc
            .perform(delete(ENTITY_API_URL_ID, facultad.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Facultad> facultadList = facultadRepository.findAll();
        assertThat(facultadList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
