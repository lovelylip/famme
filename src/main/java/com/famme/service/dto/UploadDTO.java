package com.famme.service.dto;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * A DTO for the {@link com.famme.domain.Upload} entity.
 */
public class UploadDTO implements Serializable {
    
    private UUID id;

    private ByteBuffer file;

    private String fileContentType;
    private ByteBuffer image;

    private String imageContentType;
    
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ByteBuffer getFile() {
        return file;
    }

    public void setFile(ByteBuffer file) {
        this.file = file;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public ByteBuffer getImage() {
        return image;
    }

    public void setImage(ByteBuffer image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UploadDTO)) {
            return false;
        }

        return id != null && id.equals(((UploadDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UploadDTO{" +
            "id=" + getId() +
            ", file='" + getFile() + "'" +
            ", image='" + getImage() + "'" +
            "}";
    }
}
