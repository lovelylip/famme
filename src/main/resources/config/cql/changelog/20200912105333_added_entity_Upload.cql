
CREATE TABLE IF NOT EXISTS upload (
    id uuid,
    file blob,
    file_content_type text,
    image blob,
    image_content_type text,
    PRIMARY KEY(id)
);
