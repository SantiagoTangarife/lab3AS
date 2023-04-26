package com.udea.repository;

import com.udea.domain.Decano;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Decano entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DecanoRepository extends JpaRepository<Decano, Long> {}
