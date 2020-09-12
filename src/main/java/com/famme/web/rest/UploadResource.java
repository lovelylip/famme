package com.famme.web.rest;

import com.famme.service.UploadService;
import com.famme.web.rest.errors.BadRequestAlertException;
import com.famme.service.dto.UploadDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * REST controller for managing {@link com.famme.domain.Upload}.
 */
@RestController
@RequestMapping("/api")
public class UploadResource {

    private final Logger log = LoggerFactory.getLogger(UploadResource.class);

    private static final String ENTITY_NAME = "upload";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UploadService uploadService;

    public UploadResource(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    /**
     * {@code POST  /uploads} : Create a new upload.
     *
     * @param uploadDTO the uploadDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new uploadDTO, or with status {@code 400 (Bad Request)} if the upload has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/uploads")
    public ResponseEntity<UploadDTO> createUpload(@RequestBody UploadDTO uploadDTO) throws URISyntaxException {
        log.debug("REST request to save Upload : {}", uploadDTO);
        if (uploadDTO.getId() != null) {
            throw new BadRequestAlertException("A new upload cannot already have an ID", ENTITY_NAME, "idexists");
        }
        uploadDTO.setId(UUID.randomUUID());
        UploadDTO result = uploadService.save(uploadDTO);
        return ResponseEntity.created(new URI("/api/uploads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /uploads} : Updates an existing upload.
     *
     * @param uploadDTO the uploadDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated uploadDTO,
     * or with status {@code 400 (Bad Request)} if the uploadDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the uploadDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/uploads")
    public ResponseEntity<UploadDTO> updateUpload(@RequestBody UploadDTO uploadDTO) throws URISyntaxException {
        log.debug("REST request to update Upload : {}", uploadDTO);
        if (uploadDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UploadDTO result = uploadService.save(uploadDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, uploadDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /uploads} : get all the uploads.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of uploads in body.
     */
    @GetMapping("/uploads")
    public List<UploadDTO> getAllUploads() {
        log.debug("REST request to get all Uploads");
        return uploadService.findAll();
    }

    /**
     * {@code GET  /uploads/:id} : get the "id" upload.
     *
     * @param id the id of the uploadDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the uploadDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/uploads/{id}")
    public ResponseEntity<UploadDTO> getUpload(@PathVariable UUID id) {
        log.debug("REST request to get Upload : {}", id);
        Optional<UploadDTO> uploadDTO = uploadService.findOne(id);
        return ResponseUtil.wrapOrNotFound(uploadDTO);
    }

    /**
     * {@code DELETE  /uploads/:id} : delete the "id" upload.
     *
     * @param id the id of the uploadDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/uploads/{id}")
    public ResponseEntity<Void> deleteUpload(@PathVariable UUID id) {
        log.debug("REST request to delete Upload : {}", id);
        uploadService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
