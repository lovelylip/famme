package com.famme.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.famme.web.rest.TestUtil;
import java.util.UUID;

public class UploadTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Upload.class);
        Upload upload1 = new Upload();
        upload1.setId(UUID.randomUUID());
        Upload upload2 = new Upload();
        upload2.setId(upload1.getId());
        assertThat(upload1).isEqualTo(upload2);
        upload2.setId(UUID.randomUUID());
        assertThat(upload1).isNotEqualTo(upload2);
        upload1.setId(null);
        assertThat(upload1).isNotEqualTo(upload2);
    }
}
