package net.gentledot.springcodeproject.model.request;

public class FileRequest {
    private String fileName;
    private String type;

    protected FileRequest() {
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
