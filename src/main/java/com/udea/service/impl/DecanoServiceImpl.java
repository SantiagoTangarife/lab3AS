package com.udea.service.impl;

import com.udea.domain.Decano;
import com.udea.repository.DecanoRepository;
import com.udea.service.DecanoService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Decano}.
 */
@Service
@Transactional
public class DecanoServiceImpl implements DecanoService {

    private final Logger log = LoggerFactory.getLogger(DecanoServiceImpl.class);

    private final DecanoRepository decanoRepository;

    public DecanoServiceImpl(DecanoRepository decanoRepository) {
        this.decanoRepository = decanoRepository;
    }

    @Override
    public Decano save(Decano decano) {
        log.debug("Request to save Decano : {}", decano);
        return decanoRepository.save(decano);
    }

    @Override
    public Decano update(Decano decano) {
        log.debug("Request to update Decano : {}", decano);
        return decanoRepository.save(decano);
    }

    @Override
    public Optional<Decano> partialUpdate(Decano decano) {
        log.debug("Request to partially update Decano : {}", decano);

        return decanoRepository
            .findById(decano.getId())
            .map(existingDecano -> {
                if (decano.getNombre() != null) {
                    existingDecano.setNombre(decano.getNombre());
                }
                if (decano.getEmail() != null) {
                    existingDecano.setEmail(decano.getEmail());
                }
                if (decano.getOficina() != null) {
                    existingDecano.setOficina(decano.getOficina());
                }
                if (decano.getNameFacultad() != null) {
                    existingDecano.setNameFacultad(decano.getNameFacultad());
                }

                return existingDecano;
            })
            .map(decanoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Decano> findAll(Pageable pageable) {
        log.debug("Request to get all Decanos");
        return decanoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Decano> findOne(Long id) {
        log.debug("Request to get Decano : {}", id);
        return decanoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Decano : {}", id);
        decanoRepository.deleteById(id);
    }
}
