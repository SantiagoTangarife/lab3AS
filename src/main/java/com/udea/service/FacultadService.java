package com.udea.service;

import com.udea.domain.Facultad;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Facultad}.
 */
public interface FacultadService {
    /**
     * Save a facultad.
     *
     * @param facultad the entity to save.
     * @return the persisted entity.
     */
    Facultad save(Facultad facultad);

    /**
     * Updates a facultad.
     *
     * @param facultad the entity to update.
     * @return the persisted entity.
     */
    Facultad update(Facultad facultad);

    /**
     * Partially updates a facultad.
     *
     * @param facultad the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Facultad> partialUpdate(Facultad facultad);

    /**
     * Get all the facultads.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Facultad> findAll(Pageable pageable);

    /**
     * Get the "id" facultad.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Facultad> findOne(Long id);

    /**
     * Delete the "id" facultad.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
