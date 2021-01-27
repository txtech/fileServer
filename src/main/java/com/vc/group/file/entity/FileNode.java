package com.vc.group.file.entity;

import java.util.Date;

public class FileNode {
    
    private int id;

    private int nodeId;

    private String host;

    private int port;

    private String path;

    private String mountPath;

    private String isPrimary;

    private String isBackup;

    private long freeSpace;

    private Date createTime;

    private Date modifyTime;

    private String fileType;
    
    public FileNode(){}
    
    public FileNode(int nodeId,long freeSpace){
        this.nodeId = nodeId;
        this.freeSpace = freeSpace;
    }
    
    public FileNode(int nodeId,String fileType){
        this.nodeId = nodeId;
        this.fileType = fileType;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host == null ? null : host.trim();
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public String getMountPath() {
        return mountPath;
    }

    public void setMountPath(String mountPath) {
        this.mountPath = mountPath == null ? null : mountPath.trim();
    }

    public String getIsPrimary() {
        return isPrimary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public String getIsBackup() {
        return isBackup;
    }

    public void setIsBackup(String isBackup) {
        this.isBackup = isBackup;
    }

    public long getFreeSpace() {
        return freeSpace;
    }

    public void setFreeSpace(long freeSpace) {
        this.freeSpace = freeSpace;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public void setIsPrimary(String isPrimary) {
        this.isPrimary = isPrimary;
    }
}