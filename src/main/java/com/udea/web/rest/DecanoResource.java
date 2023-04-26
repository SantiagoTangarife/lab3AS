package com.udea.web.rest;

import com.udea.domain.Decano;
import com.udea.repository.DecanoRepository;
import com.udea.service.DecanoService;
import com.udea.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
 * REST controller for managing {@link com.udea.domain.Decano}.
 */
@RestController
@RequestMapping("/api")
public class DecanoResource {

    private final Logger log = LoggerFactory.getLogger(DecanoResource.class);

    private static final String ENTITY_NAME = "decano";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DecanoService decanoService;

    private final DecanoRepository decanoRepository;

    public DecanoResource(DecanoService decanoService, DecanoRepository decanoRepository) {
        this.decanoService = decanoService;
        this.decanoRepository = decanoRepository;
    }

    /**
     * {@code POST  /decanos} : Create a new decano.
     *
     * @param decano the decano to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new decano, or with status {@code 400 (Bad Request)} if the decano has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/decanos")
    public ResponseEntity<Decano> createDecano(@Valid @RequestBody Decano decano) throws URISyntaxException {
        log.debug("REST request to save Decano : {}", decano);
        if (decano.getId() != null) {
            throw new BadRequestAlertException("A new decano cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Decano result = decanoService.save(decano);
        return ResponseEntity
            .created(new URI("/api/decanos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /decanos/:id} : Updates an existing decano.
     *
     * @param id the id of the decano to save.
     * @param decano the decano to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated decano,
     * or with status {@code 400 (Bad Request)} if the decano is not valid,
     * or with status {@code 500 (Internal Server Error)} if the decano couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/decanos/{id}")
    public ResponseEntity<Decano> updateDecano(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Decano decano
    ) throws URISyntaxException {
        log.debug("REST request to update Decano : {}, {}", id, decano);
        if (decano.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, decano.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!decanoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Decano result = decanoService.update(decano);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, decano.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /decanos/:id} : Partial updates given fields of an existing decano, field will ignore if it is null
     *
     * @param id the id of the decano to save.
     * @param decano the decano to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated decano,
     * or with status {@code 400 (Bad Request)} if the decano is not valid,
     * or with status {@code 404 (Not Found)} if the decano is not found,
     * or with status {@code 500 (Internal Server Error)} if the decano couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/decanos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Decano> partialUpdateDecano(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Decano decano
    ) throws URISyntaxException {
        log.debug("REST request to partial update Decano partially : {}, {}", id, decano);
        if (decano.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, decano.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!decanoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Decano> result = decanoService.partialUpdate(decano);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, decano.getId().toString())
        );
    }

    /**
     * {@code GET  /decanos} : get all the decanos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of decanos in body.
     */
    @GetMapping("/decanos")
    public ResponseEntity<List<Decano>> getAllDecanos(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Decanos");
        Page<Decano> page = decanoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /decanos/:id} : get the "id" decano.
     *
     * @param id the id of the decano to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the decano, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/decanos/{id}")
    public ResponseEntity<Decano> getDecano(@PathVariable Long id) {
        log.debug("REST request to get Decano : {}", id);
        Optional<Decano> decano = decanoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(decano);
    }

    /**
     * {@code DELETE  /decanos/:id} : delete the "id" decano.
     *
     * @param id the id of the decano to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/decanos/{id}")
    public ResponseEntity<Void> deleteDecano(@PathVariable Long id) {
        log.debug("REST request to delete Decano : {}", id);
        decanoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
