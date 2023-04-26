package com.udea.service;

import com.udea.domain.ViceDecano;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ViceDecano}.
 */
public interface ViceDecanoService {
    /**
     * Save a viceDecano.
     *
     * @param viceDecano the entity to save.
     * @return the persisted entity.
     */
    ViceDecano save(ViceDecano viceDecano);

    /**
     * Updates a viceDecano.
     *
     * @param viceDecano the entity to update.
     * @return the persisted entity.
     */
    ViceDecano update(ViceDecano viceDecano);

    /**
     * Partially updates a viceDecano.
     *
     * @param viceDecano the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ViceDecano> partialUpdate(ViceDecano viceDecano);

    /**
     * Get all the viceDecanos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ViceDecano> findAll(Pageable pageable);

    /**
     * Get the "id" viceDecano.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ViceDecano> findOne(Long id);

    /**
     * Delete the "id" viceDecano.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
