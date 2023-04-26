package com.udea.service.impl;

import com.udea.domain.Calendario;
import com.udea.repository.CalendarioRepository;
import com.udea.service.CalendarioService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Calendario}.
 */
@Service
@Transactional
public class CalendarioServiceImpl implements CalendarioService {

    private final Logger log = LoggerFactory.getLogger(CalendarioServiceImpl.class);

    private final CalendarioRepository calendarioRepository;

    public CalendarioServiceImpl(CalendarioRepository calendarioRepository) {
        this.calendarioRepository = calendarioRepository;
    }

    @Override
    public Calendario save(Calendario calendario) {
        log.debug("Request to save Calendario : {}", calendario);
        return calendarioRepository.save(calendario);
    }

    @Override
    public Calendario update(Calendario calendario) {
        log.debug("Request to update Calendario : {}", calendario);
        return calendarioRepository.save(calendario);
    }

    @Override
    public Optional<Calendario> partialUpdate(Calendario calendario) {
        log.debug("Request to partially update Calendario : {}", calendario);

        return calendarioRepository
            .findById(calendario.getId())
            .map(existingCalendario -> {
                if (calendario.getSemestre() != null) {
                    existingCalendario.setSemestre(calendario.getSemestre());
                }
                if (calendario.getPublicacionOferta() != null) {
                    existingCalendario.setPublicacionOferta(calendario.getPublicacionOferta());
                }
                if (calendario.getInicioMatriculas() != null) {
                    existingCalendario.setInicioMatriculas(calendario.getInicioMatriculas());
                }
                if (calendario.getFinMatriculas() != null) {
                    existingCalendario.setFinMatriculas(calendario.getFinMatriculas());
                }
                if (calendario.getInicioAjustes() != null) {
                    existingCalendario.setInicioAjustes(calendario.getInicioAjustes());
                }
                if (calendario.getFinAjustes() != null) {
                    existingCalendario.setFinAjustes(calendario.getFinAjustes());
                }
                if (calendario.getInicioClases() != null) {
                    existingCalendario.setInicioClases(calendario.getInicioClases());
                }
                if (calendario.getFinClases() != null) {
                    existingCalendario.setFinClases(calendario.getFinClases());
                }
                if (calendario.getInicioExamenesFinales() != null) {
                    existingCalendario.setInicioExamenesFinales(calendario.getInicioExamenesFinales());
                }
                if (calendario.getFinExamenesFinales() != null) {
                    existingCalendario.setFinExamenesFinales(calendario.getFinExamenesFinales());
                }
                if (calendario.getInicioValidaciones() != null) {
                    existingCalendario.setInicioValidaciones(calendario.getInicioValidaciones());
                }
                if (calendario.getFinValidaciones() != null) {
                    existingCalendario.setFinValidaciones(calendario.getFinValidaciones());
                }
                if (calendario.getInicioHabilitaciones() != null) {
                    existingCalendario.setInicioHabilitaciones(calendario.getInicioHabilitaciones());
                }
                if (calendario.getFinHabilitaciones() != null) {
                    existingCalendario.setFinHabilitaciones(calendario.getFinHabilitaciones());
                }
                if (calendario.getTerminacionOficinal() != null) {
                    existingCalendario.setTerminacionOficinal(calendario.getTerminacionOficinal());
                }

                return existingCalendario;
            })
            .map(calendarioRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Calendario> findAll(Pageable pageable) {
        log.debug("Request to get all Calendarios");
        return calendarioRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Calendario> findOne(Long id) {
        log.debug("Request to get Calendario : {}", id);
        return calendarioRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Calendario : {}", id);
        calendarioRepository.deleteById(id);
    }
}
