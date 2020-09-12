package com.famme.web.rest;

import com.famme.AbstractCassandraTest;
import com.famme.FammeApp;
import com.famme.domain.Upload;
import com.famme.repository.UploadRepository;
import com.famme.service.UploadService;
import com.famme.service.dto.UploadDTO;
import com.famme.service.mapper.UploadMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Base64Utils;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link UploadResource} REST controller.
 */
@SpringBootTest(classes = FammeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class UploadResourceIT extends AbstractCassandraTest {

    private static final ByteBuffer DEFAULT_FILE = ByteBuffer.wrap(TestUtil.createByteArray(1, "0"));
    private static final ByteBuffer UPDATED_FILE = ByteBuffer.wrap(TestUtil.createByteArray(1, "1"));
    private static final String DEFAULT_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILE_CONTENT_TYPE = "image/png";

    private static final ByteBuffer DEFAULT_IMAGE = ByteBuffer.wrap(TestUtil.createByteArray(1, "0"));
    private static final ByteBuffer UPDATED_IMAGE = ByteBuffer.wrap(TestUtil.createByteArray(1, "1"));
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    @Autowired
    private UploadRepository uploadRepository;

    @Autowired
    private UploadMapper uploadMapper;

    @Autowired
    private UploadService uploadService;

    @Autowired
    private MockMvc restUploadMockMvc;

    private Upload upload;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Upload createEntity() {
        Upload upload = new Upload()
            .file(DEFAULT_FILE)
            .fileContentType(DEFAULT_FILE_CONTENT_TYPE)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
        return upload;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Upload createUpdatedEntity() {
        Upload upload = new Upload()
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        return upload;
    }

    @BeforeEach
    public void initTest() {
        uploadRepository.deleteAll();
        upload = createEntity();
    }

    @Test
    public void createUpload() throws Exception {
        int databaseSizeBeforeCreate = uploadRepository.findAll().size();
        // Create the Upload
        UploadDTO uploadDTO = uploadMapper.toDto(upload);
        restUploadMockMvc.perform(post("/api/uploads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(uploadDTO)))
            .andExpect(status().isCreated());

        // Validate the Upload in the database
        List<Upload> uploadList = uploadRepository.findAll();
        assertThat(uploadList).hasSize(databaseSizeBeforeCreate + 1);
        Upload testUpload = uploadList.get(uploadList.size() - 1);
        assertThat(testUpload.getFile()).isEqualTo(DEFAULT_FILE);
        assertThat(testUpload.getFileContentType()).isEqualTo(DEFAULT_FILE_CONTENT_TYPE);
        assertThat(testUpload.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testUpload.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
    }

    @Test
    public void createUploadWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = uploadRepository.findAll().size();

        // Create the Upload with an existing ID
        upload.setId(UUID.randomUUID());
        UploadDTO uploadDTO = uploadMapper.toDto(upload);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUploadMockMvc.perform(post("/api/uploads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(uploadDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Upload in the database
        List<Upload> uploadList = uploadRepository.findAll();
        assertThat(uploadList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllUploads() throws Exception {
        // Initialize the database
        upload.setId(UUID.randomUUID());
        uploadRepository.save(upload);

        // Get all the uploadList
        restUploadMockMvc.perform(get("/api/uploads"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(upload.getId().toString())))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE.array()))))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE.array()))));
    }
    
    @Test
    public void getUpload() throws Exception {
        // Initialize the database
        upload.setId(UUID.randomUUID());
        uploadRepository.save(upload);

        // Get the upload
        restUploadMockMvc.perform(get("/api/uploads/{id}", upload.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(upload.getId().toString()))
            .andExpect(jsonPath("$.fileContentType").value(DEFAULT_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.file").value(Base64Utils.encodeToString(DEFAULT_FILE.array())))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE.array())));
    }
    @Test
    public void getNonExistingUpload() throws Exception {
        // Get the upload
        restUploadMockMvc.perform(get("/api/uploads/{id}", UUID.randomUUID().toString()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateUpload() throws Exception {
        // Initialize the database
        upload.setId(UUID.randomUUID());
        uploadRepository.save(upload);

        int databaseSizeBeforeUpdate = uploadRepository.findAll().size();

        // Update the upload
        Upload updatedUpload = uploadRepository.findById(upload.getId()).get();
        updatedUpload
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        UploadDTO uploadDTO = uploadMapper.toDto(updatedUpload);

        restUploadMockMvc.perform(put("/api/uploads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(uploadDTO)))
            .andExpect(status().isOk());

        // Validate the Upload in the database
        List<Upload> uploadList = uploadRepository.findAll();
        assertThat(uploadList).hasSize(databaseSizeBeforeUpdate);
        Upload testUpload = uploadList.get(uploadList.size() - 1);
        assertThat(testUpload.getFile()).isEqualTo(UPDATED_FILE);
        assertThat(testUpload.getFileContentType()).isEqualTo(UPDATED_FILE_CONTENT_TYPE);
        assertThat(testUpload.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testUpload.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    public void updateNonExistingUpload() throws Exception {
        int databaseSizeBeforeUpdate = uploadRepository.findAll().size();

        // Create the Upload
        UploadDTO uploadDTO = uploadMapper.toDto(upload);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUploadMockMvc.perform(put("/api/uploads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(uploadDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Upload in the database
        List<Upload> uploadList = uploadRepository.findAll();
        assertThat(uploadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteUpload() throws Exception {
        // Initialize the database
        upload.setId(UUID.randomUUID());
        uploadRepository.save(upload);

        int databaseSizeBeforeDelete = uploadRepository.findAll().size();

        // Delete the upload
        restUploadMockMvc.perform(delete("/api/uploads/{id}", upload.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Upload> uploadList = uploadRepository.findAll();
        assertThat(uploadList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
