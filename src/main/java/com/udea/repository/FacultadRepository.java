package com.udea.repository;

import com.udea.domain.Facultad;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Facultad entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FacultadRepository extends JpaRepository<Facultad, Long> {}
