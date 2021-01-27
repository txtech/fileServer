package com.vc.group.file.entity;

public class FileInfo {
    
    private int id;
    private long fileId;
    private String name;
    private String type;
    private String fileFormat;
    private String downloadUrl;
    private int fileSource;
    private int fileSize;
    private String fileMd5; 
    private int nodeId;
    
    public FileInfo(){}
    
    public FileInfo(long fileId,String type){
        this.fileId = fileId;
        this.type = type;
    }
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public long getFileId() {
        return fileId;
    }
    public void setFileId(long fileId) {
        this.fileId = fileId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getFileFormat() {
        return fileFormat;
    }
    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }
    public String getDownloadUrl() {
        return downloadUrl;
    }
    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
    public int getFileSource() {
        return fileSource;
    }
    public void setFileSource(int fileSource) {
        this.fileSource = fileSource;
    }
    public int getFileSize() {
        return fileSize;
    }
    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }
    public String getFileMd5() {
        return fileMd5;
    }
    public void setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
    }
    public int getNodeId() {
        return nodeId;
    }
    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }
    
    
    
}