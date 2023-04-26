package com.udea.service.impl;

import com.udea.domain.Facultad;
import com.udea.repository.FacultadRepository;
import com.udea.service.FacultadService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Facultad}.
 */
@Service
@Transactional
public class FacultadServiceImpl implements FacultadService {

    private final Logger log = LoggerFactory.getLogger(FacultadServiceImpl.class);

    private final FacultadRepository facultadRepository;

    public FacultadServiceImpl(FacultadRepository facultadRepository) {
        this.facultadRepository = facultadRepository;
    }

    @Override
    public Facultad save(Facultad facultad) {
        log.debug("Request to save Facultad : {}", facultad);
        return facultadRepository.save(facultad);
    }

    @Override
    public Facultad update(Facultad facultad) {
        log.debug("Request to update Facultad : {}", facultad);
        return facultadRepository.save(facultad);
    }

    @Override
    public Optional<Facultad> partialUpdate(Facultad facultad) {
        log.debug("Request to partially update Facultad : {}", facultad);

        return facultadRepository
            .findById(facultad.getId())
            .map(existingFacultad -> {
                if (facultad.getNombre() != null) {
                    existingFacultad.setNombre(facultad.getNombre());
                }

                return existingFacultad;
            })
            .map(facultadRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Facultad> findAll(Pageable pageable) {
        log.debug("Request to get all Facultads");
        return facultadRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Facultad> findOne(Long id) {
        log.debug("Request to get Facultad : {}", id);
        return facultadRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Facultad : {}", id);
        facultadRepository.deleteById(id);
    }
}
