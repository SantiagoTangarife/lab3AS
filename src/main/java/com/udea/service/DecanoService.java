package com.udea.service;

import com.udea.domain.Decano;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Decano}.
 */
public interface DecanoService {
    /**
     * Save a decano.
     *
     * @param decano the entity to save.
     * @return the persisted entity.
     */
    Decano save(Decano decano);

    /**
     * Updates a decano.
     *
     * @param decano the entity to update.
     * @return the persisted entity.
     */
    Decano update(Decano decano);

    /**
     * Partially updates a decano.
     *
     * @param decano the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Decano> partialUpdate(Decano decano);

    /**
     * Get all the decanos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Decano> findAll(Pageable pageable);

    /**
     * Get the "id" decano.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Decano> findOne(Long id);

    /**
     * Delete the "id" decano.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
