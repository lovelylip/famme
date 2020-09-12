package com.famme.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * A Upload.
 */
@Table("upload")
public class Upload implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;

    private ByteBuffer file;

    @Column("file_content_type")
    private String fileContentType;

    private ByteBuffer image;

    @Column("image_content_type")
    private String imageContentType;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ByteBuffer getFile() {
        return file;
    }

    public Upload file(ByteBuffer file) {
        this.file = file;
        return this;
    }

    public void setFile(ByteBuffer file) {
        this.file = file;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public Upload fileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
        return this;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public ByteBuffer getImage() {
        return image;
    }

    public Upload image(ByteBuffer image) {
        this.image = image;
        return this;
    }

    public void setImage(ByteBuffer image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Upload imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Upload)) {
            return false;
        }
        return id != null && id.equals(((Upload) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Upload{" +
            "id=" + getId() +
            ", file='" + getFile() + "'" +
            ", fileContentType='" + getFileContentType() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            "}";
    }
}
