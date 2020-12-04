package com.deepspc.stage.manager.system.model;

import java.io.Serializable;

/**
 * @author gzw
 * @date 2020/12/4 10:13
 */
public class UploadResult implements Serializable {
    private static final long serialVersionUID = -8696589491586011636L;

    private Long fileId;

    private String originalFileName;

    private String newFileName;

    private Integer fileSize;

    private String filePath;

    private String fileType;

    public UploadResult() {

    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getNewFileName() {
        return newFileName;
    }

    public void setNewFileName(String newFileName) {
        this.newFileName = newFileName;
    }

    public Integer getFileSize() {
        return fileSize;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
