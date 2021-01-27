package com.vc.group.file.dao;

import com.vc.group.file.entity.FileInfo;
import com.vc.group.file.entity.FileNode;

import java.util.List;

public interface FileServerDao {
    
    /**
     * 获取下一个文件id
     */
    long getNextFileId();
    
   /**
     * 获取用以写文件的存储节点
     */
    List<FileNode> findAllFileNodeList(String fileType);

    /**
     * 减少存储节点剩余空间
     */
    void decreaseNodeFreeSpace(FileNode fileNode);

    /**
     * 保存文件信息
     */
    void saveFileInfo(FileInfo record);

    /**
     * 获取文件信息
     */
    FileInfo findFileByFileId(FileInfo fileInfo);
    
    /**
     * 获取存储节点信息
     */
    FileNode findFileNode(FileNode fileNode);
    
    /**
     * @描述:删除文件
     */
    int deleteFile(Long fileId );
    
    /**
     * 查看URL
     */
    String  listReadUrls(FileInfo fileinfo,String fileType);

}