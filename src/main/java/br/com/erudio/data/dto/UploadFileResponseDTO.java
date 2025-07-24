package br.com.erudio.data.dto;

import java.io.Serializable;
import java.util.Objects;

public class UploadFileResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String fileName;
    private String filedowloadUrl;
    private String fileType;
    private long size;

    public UploadFileResponseDTO() {
    }

    public UploadFileResponseDTO(String fileName, String filedowloadUrl, String fileType, long size) {
        this.fileName = fileName;
        this.filedowloadUrl = filedowloadUrl;
        this.fileType = fileType;
        this.size = size;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFiledowloadUrl() {
        return filedowloadUrl;
    }

    public void setFiledowloadUrl(String filedowloadUrl) {
        this.filedowloadUrl = filedowloadUrl;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UploadFileResponseDTO that = (UploadFileResponseDTO) o;
        return Objects.equals(fileName, that.fileName) && Objects.equals(filedowloadUrl, that.filedowloadUrl) && Objects.equals(fileType, that.fileType) && Objects.equals(size, that.size);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileName, filedowloadUrl, fileType, size);
    }

    @Override
    public String toString() {
        return "UploadFileResponseDTO{" +
                "fileName='" + fileName + '\'' +
                ", filedowloadUrl='" + filedowloadUrl + '\'' +
                ", fileType='" + fileType + '\'' +
                ", size='" + size + '\'' +
                '}';
    }
}
