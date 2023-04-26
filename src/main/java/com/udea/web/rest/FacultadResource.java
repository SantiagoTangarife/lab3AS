package com.udea.web.rest;

import com.udea.domain.Facultad;
import com.udea.repository.FacultadRepository;
import com.udea.service.FacultadService;
import com.udea.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.udea.domain.Facultad}.
 */
@RestController
@RequestMapping("/api")
public class FacultadResource {

    private final Logger log = LoggerFactory.getLogger(FacultadResource.class);

    private static final String ENTITY_NAME = "facultad";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FacultadService facultadService;

    private final FacultadRepository facultadRepository;

    public FacultadResource(FacultadService facultadService, FacultadRepository facultadRepository) {
        this.facultadService = facultadService;
        this.facultadRepository = facultadRepository;
    }

    /**
     * {@code POST  /facultads} : Create a new facultad.
     *
     * @param facultad the facultad to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new facultad, or with status {@code 400 (Bad Request)} if the facultad has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/facultads")
    public ResponseEntity<Facultad> createFacultad(@RequestBody Facultad facultad) throws URISyntaxException {
        log.debug("REST request to save Facultad : {}", facultad);
        if (facultad.getId() != null) {
            throw new BadRequestAlertException("A new facultad cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Facultad result = facultadService.save(facultad);
        return ResponseEntity
            .created(new URI("/api/facultads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /facultads/:id} : Updates an existing facultad.
     *
     * @param id the id of the facultad to save.
     * @param facultad the facultad to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated facultad,
     * or with status {@code 400 (Bad Request)} if the facultad is not valid,
     * or with status {@code 500 (Internal Server Error)} if the facultad couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/facultads/{id}")
    public ResponseEntity<Facultad> updateFacultad(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Facultad facultad
    ) throws URISyntaxException {
        log.debug("REST request to update Facultad : {}, {}", id, facultad);
        if (facultad.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, facultad.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!facultadRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Facultad result = facultadService.update(facultad);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, facultad.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /facultads/:id} : Partial updates given fields of an existing facultad, field will ignore if it is null
     *
     * @param id the id of the facultad to save.
     * @param facultad the facultad to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated facultad,
     * or with status {@code 400 (Bad Request)} if the facultad is not valid,
     * or with status {@code 404 (Not Found)} if the facultad is not found,
     * or with status {@code 500 (Internal Server Error)} if the facultad couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/facultads/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Facultad> partialUpdateFacultad(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Facultad facultad
    ) throws URISyntaxException {
        log.debug("REST request to partial update Facultad partially : {}, {}", id, facultad);
        if (facultad.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, facultad.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!facultadRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Facultad> result = facultadService.partialUpdate(facultad);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, facultad.getId().toString())
        );
    }

    /**
     * {@code GET  /facultads} : get all the facultads.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of facultads in body.
     */
    @GetMapping("/facultads")
    public ResponseEntity<List<Facultad>> getAllFacultads(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Facultads");
        Page<Facultad> page = facultadService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /facultads/:id} : get the "id" facultad.
     *
     * @param id the id of the facultad to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the facultad, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/facultads/{id}")
    public ResponseEntity<Facultad> getFacultad(@PathVariable Long id) {
        log.debug("REST request to get Facultad : {}", id);
        Optional<Facultad> facultad = facultadService.findOne(id);
        return ResponseUtil.wrapOrNotFound(facultad);
    }

    /**
     * {@code DELETE  /facultads/:id} : delete the "id" facultad.
     *
     * @param id the id of the facultad to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/facultads/{id}")
    public ResponseEntity<Void> deleteFacultad(@PathVariable Long id) {
        log.debug("REST request to delete Facultad : {}", id);
        facultadService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
