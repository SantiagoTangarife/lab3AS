package com.udea.web.rest;

import com.udea.domain.ViceDecano;
import com.udea.repository.ViceDecanoRepository;
import com.udea.service.ViceDecanoService;
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
 * REST controller for managing {@link com.udea.domain.ViceDecano}.
 */
@RestController
@RequestMapping("/api")
public class ViceDecanoResource {

    private final Logger log = LoggerFactory.getLogger(ViceDecanoResource.class);

    private static final String ENTITY_NAME = "viceDecano";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ViceDecanoService viceDecanoService;

    private final ViceDecanoRepository viceDecanoRepository;

    public ViceDecanoResource(ViceDecanoService viceDecanoService, ViceDecanoRepository viceDecanoRepository) {
        this.viceDecanoService = viceDecanoService;
        this.viceDecanoRepository = viceDecanoRepository;
    }

    /**
     * {@code POST  /vice-decanos} : Create a new viceDecano.
     *
     * @param viceDecano the viceDecano to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new viceDecano, or with status {@code 400 (Bad Request)} if the viceDecano has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vice-decanos")
    public ResponseEntity<ViceDecano> createViceDecano(@Valid @RequestBody ViceDecano viceDecano) throws URISyntaxException {
        log.debug("REST request to save ViceDecano : {}", viceDecano);
        if (viceDecano.getId() != null) {
            throw new BadRequestAlertException("A new viceDecano cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ViceDecano result = viceDecanoService.save(viceDecano);
        return ResponseEntity
            .created(new URI("/api/vice-decanos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vice-decanos/:id} : Updates an existing viceDecano.
     *
     * @param id the id of the viceDecano to save.
     * @param viceDecano the viceDecano to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated viceDecano,
     * or with status {@code 400 (Bad Request)} if the viceDecano is not valid,
     * or with status {@code 500 (Internal Server Error)} if the viceDecano couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vice-decanos/{id}")
    public ResponseEntity<ViceDecano> updateViceDecano(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ViceDecano viceDecano
    ) throws URISyntaxException {
        log.debug("REST request to update ViceDecano : {}, {}", id, viceDecano);
        if (viceDecano.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, viceDecano.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!viceDecanoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ViceDecano result = viceDecanoService.update(viceDecano);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, viceDecano.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /vice-decanos/:id} : Partial updates given fields of an existing viceDecano, field will ignore if it is null
     *
     * @param id the id of the viceDecano to save.
     * @param viceDecano the viceDecano to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated viceDecano,
     * or with status {@code 400 (Bad Request)} if the viceDecano is not valid,
     * or with status {@code 404 (Not Found)} if the viceDecano is not found,
     * or with status {@code 500 (Internal Server Error)} if the viceDecano couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/vice-decanos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ViceDecano> partialUpdateViceDecano(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ViceDecano viceDecano
    ) throws URISyntaxException {
        log.debug("REST request to partial update ViceDecano partially : {}, {}", id, viceDecano);
        if (viceDecano.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, viceDecano.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!viceDecanoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ViceDecano> result = viceDecanoService.partialUpdate(viceDecano);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, viceDecano.getId().toString())
        );
    }

    /**
     * {@code GET  /vice-decanos} : get all the viceDecanos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of viceDecanos in body.
     */
    @GetMapping("/vice-decanos")
    public ResponseEntity<List<ViceDecano>> getAllViceDecanos(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ViceDecanos");
        Page<ViceDecano> page = viceDecanoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /vice-decanos/:id} : get the "id" viceDecano.
     *
     * @param id the id of the viceDecano to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the viceDecano, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vice-decanos/{id}")
    public ResponseEntity<ViceDecano> getViceDecano(@PathVariable Long id) {
        log.debug("REST request to get ViceDecano : {}", id);
        Optional<ViceDecano> viceDecano = viceDecanoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(viceDecano);
    }

    /**
     * {@code DELETE  /vice-decanos/:id} : delete the "id" viceDecano.
     *
     * @param id the id of the viceDecano to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vice-decanos/{id}")
    public ResponseEntity<Void> deleteViceDecano(@PathVariable Long id) {
        log.debug("REST request to delete ViceDecano : {}", id);
        viceDecanoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
