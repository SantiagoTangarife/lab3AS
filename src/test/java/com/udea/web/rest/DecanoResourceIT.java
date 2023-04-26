package com.udea.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.udea.IntegrationTest;
import com.udea.domain.Decano;
import com.udea.repository.DecanoRepository;
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
 * Integration tests for the {@link DecanoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DecanoResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_OFICINA = "AAAAAAAAAA";
    private static final String UPDATED_OFICINA = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_FACULTAD = "AAAAAAAAAA";
    private static final String UPDATED_NAME_FACULTAD = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/decanos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DecanoRepository decanoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDecanoMockMvc;

    private Decano decano;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Decano createEntity(EntityManager em) {
        Decano decano = new Decano()
            .nombre(DEFAULT_NOMBRE)
            .email(DEFAULT_EMAIL)
            .oficina(DEFAULT_OFICINA)
            .nameFacultad(DEFAULT_NAME_FACULTAD);
        return decano;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Decano createUpdatedEntity(EntityManager em) {
        Decano decano = new Decano()
            .nombre(UPDATED_NOMBRE)
            .email(UPDATED_EMAIL)
            .oficina(UPDATED_OFICINA)
            .nameFacultad(UPDATED_NAME_FACULTAD);
        return decano;
    }

    @BeforeEach
    public void initTest() {
        decano = createEntity(em);
    }

    @Test
    @Transactional
    void createDecano() throws Exception {
        int databaseSizeBeforeCreate = decanoRepository.findAll().size();
        // Create the Decano
        restDecanoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(decano)))
            .andExpect(status().isCreated());

        // Validate the Decano in the database
        List<Decano> decanoList = decanoRepository.findAll();
        assertThat(decanoList).hasSize(databaseSizeBeforeCreate + 1);
        Decano testDecano = decanoList.get(decanoList.size() - 1);
        assertThat(testDecano.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testDecano.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testDecano.getOficina()).isEqualTo(DEFAULT_OFICINA);
        assertThat(testDecano.getNameFacultad()).isEqualTo(DEFAULT_NAME_FACULTAD);
    }

    @Test
    @Transactional
    void createDecanoWithExistingId() throws Exception {
        // Create the Decano with an existing ID
        decano.setId(1L);

        int databaseSizeBeforeCreate = decanoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDecanoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(decano)))
            .andExpect(status().isBadRequest());

        // Validate the Decano in the database
        List<Decano> decanoList = decanoRepository.findAll();
        assertThat(decanoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDecanos() throws Exception {
        // Initialize the database
        decanoRepository.saveAndFlush(decano);

        // Get all the decanoList
        restDecanoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(decano.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].oficina").value(hasItem(DEFAULT_OFICINA)))
            .andExpect(jsonPath("$.[*].nameFacultad").value(hasItem(DEFAULT_NAME_FACULTAD)));
    }

    @Test
    @Transactional
    void getDecano() throws Exception {
        // Initialize the database
        decanoRepository.saveAndFlush(decano);

        // Get the decano
        restDecanoMockMvc
            .perform(get(ENTITY_API_URL_ID, decano.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(decano.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.oficina").value(DEFAULT_OFICINA))
            .andExpect(jsonPath("$.nameFacultad").value(DEFAULT_NAME_FACULTAD));
    }

    @Test
    @Transactional
    void getNonExistingDecano() throws Exception {
        // Get the decano
        restDecanoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDecano() throws Exception {
        // Initialize the database
        decanoRepository.saveAndFlush(decano);

        int databaseSizeBeforeUpdate = decanoRepository.findAll().size();

        // Update the decano
        Decano updatedDecano = decanoRepository.findById(decano.getId()).get();
        // Disconnect from session so that the updates on updatedDecano are not directly saved in db
        em.detach(updatedDecano);
        updatedDecano.nombre(UPDATED_NOMBRE).email(UPDATED_EMAIL).oficina(UPDATED_OFICINA).nameFacultad(UPDATED_NAME_FACULTAD);

        restDecanoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDecano.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDecano))
            )
            .andExpect(status().isOk());

        // Validate the Decano in the database
        List<Decano> decanoList = decanoRepository.findAll();
        assertThat(decanoList).hasSize(databaseSizeBeforeUpdate);
        Decano testDecano = decanoList.get(decanoList.size() - 1);
        assertThat(testDecano.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testDecano.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDecano.getOficina()).isEqualTo(UPDATED_OFICINA);
        assertThat(testDecano.getNameFacultad()).isEqualTo(UPDATED_NAME_FACULTAD);
    }

    @Test
    @Transactional
    void putNonExistingDecano() throws Exception {
        int databaseSizeBeforeUpdate = decanoRepository.findAll().size();
        decano.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDecanoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, decano.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(decano))
            )
            .andExpect(status().isBadRequest());

        // Validate the Decano in the database
        List<Decano> decanoList = decanoRepository.findAll();
        assertThat(decanoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDecano() throws Exception {
        int databaseSizeBeforeUpdate = decanoRepository.findAll().size();
        decano.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDecanoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(decano))
            )
            .andExpect(status().isBadRequest());

        // Validate the Decano in the database
        List<Decano> decanoList = decanoRepository.findAll();
        assertThat(decanoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDecano() throws Exception {
        int databaseSizeBeforeUpdate = decanoRepository.findAll().size();
        decano.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDecanoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(decano)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Decano in the database
        List<Decano> decanoList = decanoRepository.findAll();
        assertThat(decanoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDecanoWithPatch() throws Exception {
        // Initialize the database
        decanoRepository.saveAndFlush(decano);

        int databaseSizeBeforeUpdate = decanoRepository.findAll().size();

        // Update the decano using partial update
        Decano partialUpdatedDecano = new Decano();
        partialUpdatedDecano.setId(decano.getId());

        partialUpdatedDecano.nombre(UPDATED_NOMBRE).email(UPDATED_EMAIL).oficina(UPDATED_OFICINA);

        restDecanoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDecano.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDecano))
            )
            .andExpect(status().isOk());

        // Validate the Decano in the database
        List<Decano> decanoList = decanoRepository.findAll();
        assertThat(decanoList).hasSize(databaseSizeBeforeUpdate);
        Decano testDecano = decanoList.get(decanoList.size() - 1);
        assertThat(testDecano.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testDecano.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDecano.getOficina()).isEqualTo(UPDATED_OFICINA);
        assertThat(testDecano.getNameFacultad()).isEqualTo(DEFAULT_NAME_FACULTAD);
    }

    @Test
    @Transactional
    void fullUpdateDecanoWithPatch() throws Exception {
        // Initialize the database
        decanoRepository.saveAndFlush(decano);

        int databaseSizeBeforeUpdate = decanoRepository.findAll().size();

        // Update the decano using partial update
        Decano partialUpdatedDecano = new Decano();
        partialUpdatedDecano.setId(decano.getId());

        partialUpdatedDecano.nombre(UPDATED_NOMBRE).email(UPDATED_EMAIL).oficina(UPDATED_OFICINA).nameFacultad(UPDATED_NAME_FACULTAD);

        restDecanoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDecano.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDecano))
            )
            .andExpect(status().isOk());

        // Validate the Decano in the database
        List<Decano> decanoList = decanoRepository.findAll();
        assertThat(decanoList).hasSize(databaseSizeBeforeUpdate);
        Decano testDecano = decanoList.get(decanoList.size() - 1);
        assertThat(testDecano.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testDecano.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDecano.getOficina()).isEqualTo(UPDATED_OFICINA);
        assertThat(testDecano.getNameFacultad()).isEqualTo(UPDATED_NAME_FACULTAD);
    }

    @Test
    @Transactional
    void patchNonExistingDecano() throws Exception {
        int databaseSizeBeforeUpdate = decanoRepository.findAll().size();
        decano.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDecanoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, decano.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(decano))
            )
            .andExpect(status().isBadRequest());

        // Validate the Decano in the database
        List<Decano> decanoList = decanoRepository.findAll();
        assertThat(decanoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDecano() throws Exception {
        int databaseSizeBeforeUpdate = decanoRepository.findAll().size();
        decano.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDecanoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(decano))
            )
            .andExpect(status().isBadRequest());

        // Validate the Decano in the database
        List<Decano> decanoList = decanoRepository.findAll();
        assertThat(decanoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDecano() throws Exception {
        int databaseSizeBeforeUpdate = decanoRepository.findAll().size();
        decano.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDecanoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(decano)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Decano in the database
        List<Decano> decanoList = decanoRepository.findAll();
        assertThat(decanoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDecano() throws Exception {
        // Initialize the database
        decanoRepository.saveAndFlush(decano);

        int databaseSizeBeforeDelete = decanoRepository.findAll().size();

        // Delete the decano
        restDecanoMockMvc
            .perform(delete(ENTITY_API_URL_ID, decano.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Decano> decanoList = decanoRepository.findAll();
        assertThat(decanoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
