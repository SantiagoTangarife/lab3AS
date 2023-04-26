package com.udea.repository;

import com.udea.domain.Calendario;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Calendario entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CalendarioRepository extends JpaRepository<Calendario, Long> {}
