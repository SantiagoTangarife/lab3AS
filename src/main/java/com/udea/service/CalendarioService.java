package com.udea.service;

import com.udea.domain.Calendario;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Calendario}.
 */
public interface CalendarioService {
    /**
     * Save a calendario.
     *
     * @param calendario the entity to save.
     * @return the persisted entity.
     */
    Calendario save(Calendario calendario);

    /**
     * Updates a calendario.
     *
     * @param calendario the entity to update.
     * @return the persisted entity.
     */
    Calendario update(Calendario calendario);

    /**
     * Partially updates a calendario.
     *
     * @param calendario the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Calendario> partialUpdate(Calendario calendario);

    /**
     * Get all the calendarios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Calendario> findAll(Pageable pageable);

    /**
     * Get the "id" calendario.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Calendario> findOne(Long id);

    /**
     * Delete the "id" calendario.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
