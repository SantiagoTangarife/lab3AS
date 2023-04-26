package com.udea.repository;

import com.udea.domain.ViceDecano;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ViceDecano entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ViceDecanoRepository extends JpaRepository<ViceDecano, Long> {}
