package net.gentledot.springcodeproject.model.upload;

public class AttachFile {
    private String fileName;
    private String uploadPath;
    private String uuid;
    private boolean image;

    protected AttachFile() {
    }

    public AttachFile(String fileName, String uploadPath, String uuid, boolean image) {
        this.fileName = fileName;
        this.uploadPath = uploadPath;
        this.uuid = uuid;
        this.image = image;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public boolean isImage() {
        return image;
    }

    public void setImage(boolean image) {
        this.image = image;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AttachFile{");
        sb.append("fileName='").append(fileName).append('\'');
        sb.append(", uploadPath='").append(uploadPath).append('\'');
        sb.append(", uuid='").append(uuid).append('\'');
        sb.append(", image=").append(image);
        sb.append('}');
        return sb.toString();
    }


    public static final class Builder {
        private String fileName;
        private String uploadPath;
        private String uuid;
        private boolean image;

        public Builder() {
            this.image = false;
        }

        public Builder fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public Builder uploadPath(String uploadPath) {
            this.uploadPath = uploadPath;
            return this;
        }

        public Builder uuid(String uuid) {
            this.uuid = uuid;
            return this;
        }

        public AttachFile build() {
            return new AttachFile(fileName, uploadPath, uuid, image);
        }
    }
}
