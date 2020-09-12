package com.famme.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.famme.web.rest.TestUtil;
import java.util.UUID;

public class UploadDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UploadDTO.class);
        UploadDTO uploadDTO1 = new UploadDTO();
        uploadDTO1.setId(UUID.randomUUID());
        UploadDTO uploadDTO2 = new UploadDTO();
        assertThat(uploadDTO1).isNotEqualTo(uploadDTO2);
        uploadDTO2.setId(uploadDTO1.getId());
        assertThat(uploadDTO1).isEqualTo(uploadDTO2);
        uploadDTO2.setId(UUID.randomUUID());
        assertThat(uploadDTO1).isNotEqualTo(uploadDTO2);
        uploadDTO1.setId(null);
        assertThat(uploadDTO1).isNotEqualTo(uploadDTO2);
    }
}
