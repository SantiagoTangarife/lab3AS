package com.udea.service.impl;

import com.udea.domain.ViceDecano;
import com.udea.repository.ViceDecanoRepository;
import com.udea.service.ViceDecanoService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ViceDecano}.
 */
@Service
@Transactional
public class ViceDecanoServiceImpl implements ViceDecanoService {

    private final Logger log = LoggerFactory.getLogger(ViceDecanoServiceImpl.class);

    private final ViceDecanoRepository viceDecanoRepository;

    public ViceDecanoServiceImpl(ViceDecanoRepository viceDecanoRepository) {
        this.viceDecanoRepository = viceDecanoRepository;
    }

    @Override
    public ViceDecano save(ViceDecano viceDecano) {
        log.debug("Request to save ViceDecano : {}", viceDecano);
        return viceDecanoRepository.save(viceDecano);
    }

    @Override
    public ViceDecano update(ViceDecano viceDecano) {
        log.debug("Request to update ViceDecano : {}", viceDecano);
        return viceDecanoRepository.save(viceDecano);
    }

    @Override
    public Optional<ViceDecano> partialUpdate(ViceDecano viceDecano) {
        log.debug("Request to partially update ViceDecano : {}", viceDecano);

        return viceDecanoRepository
            .findById(viceDecano.getId())
            .map(existingViceDecano -> {
                if (viceDecano.getNombre() != null) {
                    existingViceDecano.setNombre(viceDecano.getNombre());
                }
                if (viceDecano.getEmail() != null) {
                    existingViceDecano.setEmail(viceDecano.getEmail());
                }
                if (viceDecano.getOficina() != null) {
                    existingViceDecano.setOficina(viceDecano.getOficina());
                }
                if (viceDecano.getNameFacultad() != null) {
                    existingViceDecano.setNameFacultad(viceDecano.getNameFacultad());
                }

                return existingViceDecano;
            })
            .map(viceDecanoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ViceDecano> findAll(Pageable pageable) {
        log.debug("Request to get all ViceDecanos");
        return viceDecanoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ViceDecano> findOne(Long id) {
        log.debug("Request to get ViceDecano : {}", id);
        return viceDecanoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ViceDecano : {}", id);
        viceDecanoRepository.deleteById(id);
    }
}
