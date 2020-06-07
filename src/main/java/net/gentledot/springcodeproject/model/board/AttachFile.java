package net.gentledot.springcodeproject.model.board;

public class AttachFile {
    private String uuid;
    private long bno;
    private String uploadPath;
    private String fileName;
    private String fileType;

    public AttachFile(String uuid, Long bno, String uploadPath, String fileName, String fileType) {
        this.uuid = uuid;
        this.bno = bno;
        this.uploadPath = uploadPath;
        this.fileName = fileName;
        this.fileType = fileType;
    }

    public String getUuid() {
        return uuid;
    }

    public long getBno() {
        return bno;
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileType() {
        return fileType;
    }


    public static final class Builder {
        private String uuid;
        private long bno;
        private String uploadPath;
        private String fileName;
        private String fileType;

        public Builder(Long bno) {
            this.bno = bno;
        }

        public Builder uuid(String uuid) {
            this.uuid = uuid;
            return this;
        }

        public Builder uploadPath(String uploadPath) {
            this.uploadPath = uploadPath;
            return this;
        }

        public Builder fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public Builder fileType(String fileType) {
            this.fileType = fileType;
            return this;
        }

        public AttachFile build() {
            return new AttachFile(uuid, bno, uploadPath, fileName, fileType);
        }
    }
}
