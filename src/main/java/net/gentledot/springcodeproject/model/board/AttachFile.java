package net.gentledot.springcodeproject.model.board;

public class AttachFile {
    private String uuid;
    private long bno;
    private String uploadPath;
    private String fileName;
    private String fileType;

    protected AttachFile() {

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

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setBno(long bno) {
        this.bno = bno;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AttachFile{");
        sb.append("uuid='").append(uuid).append('\'');
        sb.append(", bno=").append(bno);
        sb.append(", uploadPath='").append(uploadPath).append('\'');
        sb.append(", fileName='").append(fileName).append('\'');
        sb.append(", fileType='").append(fileType).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
