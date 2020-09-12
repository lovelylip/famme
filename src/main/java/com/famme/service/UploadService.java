package com.famme.service;

import com.famme.service.dto.UploadDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service Interface for managing {@link com.famme.domain.Upload}.
 */
public interface UploadService {

    /**
     * Save a upload.
     *
     * @param uploadDTO the entity to save.
     * @return the persisted entity.
     */
    UploadDTO save(UploadDTO uploadDTO);

    /**
     * Get all the uploads.
     *
     * @return the list of entities.
     */
    List<UploadDTO> findAll();


    /**
     * Get the "id" upload.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UploadDTO> findOne(UUID id);

    /**
     * Delete the "id" upload.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);
}
