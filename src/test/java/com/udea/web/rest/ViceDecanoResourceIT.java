package com.udea.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.udea.IntegrationTest;
import com.udea.domain.ViceDecano;
import com.udea.repository.ViceDecanoRepository;
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
 * Integration tests for the {@link ViceDecanoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ViceDecanoResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_OFICINA = "AAAAAAAAAA";
    private static final String UPDATED_OFICINA = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_FACULTAD = "AAAAAAAAAA";
    private static final String UPDATED_NAME_FACULTAD = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/vice-decanos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ViceDecanoRepository viceDecanoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restViceDecanoMockMvc;

    private ViceDecano viceDecano;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ViceDecano createEntity(EntityManager em) {
        ViceDecano viceDecano = new ViceDecano()
            .nombre(DEFAULT_NOMBRE)
            .email(DEFAULT_EMAIL)
            .oficina(DEFAULT_OFICINA)
            .nameFacultad(DEFAULT_NAME_FACULTAD);
        return viceDecano;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ViceDecano createUpdatedEntity(EntityManager em) {
        ViceDecano viceDecano = new ViceDecano()
            .nombre(UPDATED_NOMBRE)
            .email(UPDATED_EMAIL)
            .oficina(UPDATED_OFICINA)
            .nameFacultad(UPDATED_NAME_FACULTAD);
        return viceDecano;
    }

    @BeforeEach
    public void initTest() {
        viceDecano = createEntity(em);
    }

    @Test
    @Transactional
    void createViceDecano() throws Exception {
        int databaseSizeBeforeCreate = viceDecanoRepository.findAll().size();
        // Create the ViceDecano
        restViceDecanoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(viceDecano)))
            .andExpect(status().isCreated());

        // Validate the ViceDecano in the database
        List<ViceDecano> viceDecanoList = viceDecanoRepository.findAll();
        assertThat(viceDecanoList).hasSize(databaseSizeBeforeCreate + 1);
        ViceDecano testViceDecano = viceDecanoList.get(viceDecanoList.size() - 1);
        assertThat(testViceDecano.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testViceDecano.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testViceDecano.getOficina()).isEqualTo(DEFAULT_OFICINA);
        assertThat(testViceDecano.getNameFacultad()).isEqualTo(DEFAULT_NAME_FACULTAD);
    }

    @Test
    @Transactional
    void createViceDecanoWithExistingId() throws Exception {
        // Create the ViceDecano with an existing ID
        viceDecano.setId(1L);

        int databaseSizeBeforeCreate = viceDecanoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restViceDecanoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(viceDecano)))
            .andExpect(status().isBadRequest());

        // Validate the ViceDecano in the database
        List<ViceDecano> viceDecanoList = viceDecanoRepository.findAll();
        assertThat(viceDecanoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllViceDecanos() throws Exception {
        // Initialize the database
        viceDecanoRepository.saveAndFlush(viceDecano);

        // Get all the viceDecanoList
        restViceDecanoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(viceDecano.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].oficina").value(hasItem(DEFAULT_OFICINA)))
            .andExpect(jsonPath("$.[*].nameFacultad").value(hasItem(DEFAULT_NAME_FACULTAD)));
    }

    @Test
    @Transactional
    void getViceDecano() throws Exception {
        // Initialize the database
        viceDecanoRepository.saveAndFlush(viceDecano);

        // Get the viceDecano
        restViceDecanoMockMvc
            .perform(get(ENTITY_API_URL_ID, viceDecano.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(viceDecano.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.oficina").value(DEFAULT_OFICINA))
            .andExpect(jsonPath("$.nameFacultad").value(DEFAULT_NAME_FACULTAD));
    }

    @Test
    @Transactional
    void getNonExistingViceDecano() throws Exception {
        // Get the viceDecano
        restViceDecanoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingViceDecano() throws Exception {
        // Initialize the database
        viceDecanoRepository.saveAndFlush(viceDecano);

        int databaseSizeBeforeUpdate = viceDecanoRepository.findAll().size();

        // Update the viceDecano
        ViceDecano updatedViceDecano = viceDecanoRepository.findById(viceDecano.getId()).get();
        // Disconnect from session so that the updates on updatedViceDecano are not directly saved in db
        em.detach(updatedViceDecano);
        updatedViceDecano.nombre(UPDATED_NOMBRE).email(UPDATED_EMAIL).oficina(UPDATED_OFICINA).nameFacultad(UPDATED_NAME_FACULTAD);

        restViceDecanoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedViceDecano.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedViceDecano))
            )
            .andExpect(status().isOk());

        // Validate the ViceDecano in the database
        List<ViceDecano> viceDecanoList = viceDecanoRepository.findAll();
        assertThat(viceDecanoList).hasSize(databaseSizeBeforeUpdate);
        ViceDecano testViceDecano = viceDecanoList.get(viceDecanoList.size() - 1);
        assertThat(testViceDecano.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testViceDecano.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testViceDecano.getOficina()).isEqualTo(UPDATED_OFICINA);
        assertThat(testViceDecano.getNameFacultad()).isEqualTo(UPDATED_NAME_FACULTAD);
    }

    @Test
    @Transactional
    void putNonExistingViceDecano() throws Exception {
        int databaseSizeBeforeUpdate = viceDecanoRepository.findAll().size();
        viceDecano.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restViceDecanoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, viceDecano.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(viceDecano))
            )
            .andExpect(status().isBadRequest());

        // Validate the ViceDecano in the database
        List<ViceDecano> viceDecanoList = viceDecanoRepository.findAll();
        assertThat(viceDecanoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchViceDecano() throws Exception {
        int databaseSizeBeforeUpdate = viceDecanoRepository.findAll().size();
        viceDecano.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restViceDecanoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(viceDecano))
            )
            .andExpect(status().isBadRequest());

        // Validate the ViceDecano in the database
        List<ViceDecano> viceDecanoList = viceDecanoRepository.findAll();
        assertThat(viceDecanoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamViceDecano() throws Exception {
        int databaseSizeBeforeUpdate = viceDecanoRepository.findAll().size();
        viceDecano.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restViceDecanoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(viceDecano)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ViceDecano in the database
        List<ViceDecano> viceDecanoList = viceDecanoRepository.findAll();
        assertThat(viceDecanoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateViceDecanoWithPatch() throws Exception {
        // Initialize the database
        viceDecanoRepository.saveAndFlush(viceDecano);

        int databaseSizeBeforeUpdate = viceDecanoRepository.findAll().size();

        // Update the viceDecano using partial update
        ViceDecano partialUpdatedViceDecano = new ViceDecano();
        partialUpdatedViceDecano.setId(viceDecano.getId());

        restViceDecanoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedViceDecano.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedViceDecano))
            )
            .andExpect(status().isOk());

        // Validate the ViceDecano in the database
        List<ViceDecano> viceDecanoList = viceDecanoRepository.findAll();
        assertThat(viceDecanoList).hasSize(databaseSizeBeforeUpdate);
        ViceDecano testViceDecano = viceDecanoList.get(viceDecanoList.size() - 1);
        assertThat(testViceDecano.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testViceDecano.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testViceDecano.getOficina()).isEqualTo(DEFAULT_OFICINA);
        assertThat(testViceDecano.getNameFacultad()).isEqualTo(DEFAULT_NAME_FACULTAD);
    }

    @Test
    @Transactional
    void fullUpdateViceDecanoWithPatch() throws Exception {
        // Initialize the database
        viceDecanoRepository.saveAndFlush(viceDecano);

        int databaseSizeBeforeUpdate = viceDecanoRepository.findAll().size();

        // Update the viceDecano using partial update
        ViceDecano partialUpdatedViceDecano = new ViceDecano();
        partialUpdatedViceDecano.setId(viceDecano.getId());

        partialUpdatedViceDecano.nombre(UPDATED_NOMBRE).email(UPDATED_EMAIL).oficina(UPDATED_OFICINA).nameFacultad(UPDATED_NAME_FACULTAD);

        restViceDecanoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedViceDecano.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedViceDecano))
            )
            .andExpect(status().isOk());

        // Validate the ViceDecano in the database
        List<ViceDecano> viceDecanoList = viceDecanoRepository.findAll();
        assertThat(viceDecanoList).hasSize(databaseSizeBeforeUpdate);
        ViceDecano testViceDecano = viceDecanoList.get(viceDecanoList.size() - 1);
        assertThat(testViceDecano.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testViceDecano.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testViceDecano.getOficina()).isEqualTo(UPDATED_OFICINA);
        assertThat(testViceDecano.getNameFacultad()).isEqualTo(UPDATED_NAME_FACULTAD);
    }

    @Test
    @Transactional
    void patchNonExistingViceDecano() throws Exception {
        int databaseSizeBeforeUpdate = viceDecanoRepository.findAll().size();
        viceDecano.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restViceDecanoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, viceDecano.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(viceDecano))
            )
            .andExpect(status().isBadRequest());

        // Validate the ViceDecano in the database
        List<ViceDecano> viceDecanoList = viceDecanoRepository.findAll();
        assertThat(viceDecanoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchViceDecano() throws Exception {
        int databaseSizeBeforeUpdate = viceDecanoRepository.findAll().size();
        viceDecano.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restViceDecanoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(viceDecano))
            )
            .andExpect(status().isBadRequest());

        // Validate the ViceDecano in the database
        List<ViceDecano> viceDecanoList = viceDecanoRepository.findAll();
        assertThat(viceDecanoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamViceDecano() throws Exception {
        int databaseSizeBeforeUpdate = viceDecanoRepository.findAll().size();
        viceDecano.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restViceDecanoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(viceDecano))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ViceDecano in the database
        List<ViceDecano> viceDecanoList = viceDecanoRepository.findAll();
        assertThat(viceDecanoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteViceDecano() throws Exception {
        // Initialize the database
        viceDecanoRepository.saveAndFlush(viceDecano);

        int databaseSizeBeforeDelete = viceDecanoRepository.findAll().size();

        // Delete the viceDecano
        restViceDecanoMockMvc
            .perform(delete(ENTITY_API_URL_ID, viceDecano.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ViceDecano> viceDecanoList = viceDecanoRepository.findAll();
        assertThat(viceDecanoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
